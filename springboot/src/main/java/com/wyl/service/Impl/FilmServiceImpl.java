package com.wyl.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyl.analysis.SentimentAnalyzer;
import com.wyl.mapper.CategoryMapper;
import com.wyl.mapper.CommentMapper;
import com.wyl.mapper.FilmMapper;
import com.wyl.pojo.Category;
import com.wyl.pojo.Film;
import com.wyl.service.CategoryService;
import com.wyl.service.FilmService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/*
@作者：wyl
@创建时间：2025/1/7 18:48  
*/
@Service
public class FilmServiceImpl implements FilmService {
    @Autowired
    private FilmMapper filmMapper;
    @Autowired
    private CategoryService categoryService; // 需要注入分类服务
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Film> selectAll() {
        return filmMapper.selectAll();
    }

    @Override
    public PageInfo<Film> selectPage(Film film, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        // 直接调用自定义的联表查询方法
        List<Film> films = filmMapper.selectFilmPage(film);
        for (Film f : films) {
            this.setScore(f);
        }
        return new PageInfo<>(films);
    }

    @Override
    public Film selectById(Integer id) {
        Film f = filmMapper.selectById(id);
        this.setScore(f);
        return f;
    }

    // 推荐
//    @Override
//    public List<Film> selectRecommend(Integer filmId) {
//        // 获取当前电影的类别
//        Film currentFilm = filmMapper.selectById(filmId);
//        Integer currentCategoryId = currentFilm.getCategoryId();
//
//        // 获取所有电影
//        List<Film> films = this.selectAll();
//
//        // 过滤掉当前电影
//        films = films.stream().filter(film -> !film.getId().equals(filmId)).collect(Collectors.toList());
//
//        // 计算相似度并排序
//        films.sort((f1, f2) -> {
//            double similarity1 = calculateSimilarity(currentCategoryId, f1.getCategoryId());
//            double similarity2 = calculateSimilarity(currentCategoryId, f2.getCategoryId());
//            return Double.compare(similarity2, similarity1); // 降序排列
//        });
//
//        // 取前三个
//        List<Film> filmList = films.stream().limit(3).collect(Collectors.toList());
//
//        // 设置评分和总分
//        for (Film f : filmList) {
//            this.setScore(f);
//        }
//        return filmList;
//    }

    // 计算两个电影类别之间的相似度
    private double calculateSimilarity(Integer categoryId1, Integer categoryId2) {
        if (categoryId1.equals(categoryId2)) {
            return 1.0; // 相同类别，相似度为1
        } else {
            return 0.0; // 不同类别，相似度为0
        }
    }

    @Override
    public List<Film> selectRanking() {
        List<Film> films = filmMapper.selectAll();
        for (Film f : films) {
            this.setScore(f);
        }
        films = films.stream().sorted((o1, o2) -> o2.getScore().compareTo(o1.getScore())).limit(5)
                .collect(Collectors.toList());
        return films;
    }

    @Override
    public void add(Film film) {
        // 根据分类名称查询分类ID
        Integer categoryId = categoryService.getCategoryIdByName(film.getCategoryName());
        if (categoryId == null) {
            throw new RuntimeException("分类不存在");
        }
        film.setCategoryId(categoryId); // 设置正确的分类ID
        filmMapper.insert(film);
    }

    @Override
    public void update(Film film) {
        // 同样处理分类ID转换
        Integer categoryId = categoryService.getCategoryIdByName(film.getCategoryName());
        if (categoryId == null) {
            throw new RuntimeException("分类不存在");
        }
        film.setCategoryId(categoryId);
        filmMapper.updateById(film);
    }

    @Override
    public void deleteById(Integer id) {
        filmMapper.deleteById(id);
    }

    public void setScore(Film f) {
        // 获取该电影的评论总数
        int total = commentMapper.selectTotal(f.getId());

        // 设置评论数量
        f.setCommentNum(total);

        if (total == 0) {
            // 如果没有评论，设置默认评分0
            f.setScore("0.0");
            filmMapper.updateScore(f.getId(), f.getScore());
            return;
        }

        try {
            // 获取评分总和并计算平均评分
            double avgRating = commentMapper.selectSum(f.getId()) / total;

            // 获取所有评论内容进行情感分析
            List<String> comments = commentMapper.selectCommentContents(f.getId());

            // 计算情感得分
            double sentimentSum = 0.0;
            SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();

            for (String content : comments) {
                if (content != null && !content.trim().isEmpty()) {
                    sentimentSum += sentimentAnalyzer.analyzeSentiment(content);
                }
            }

            // 计算平均情感得分
            double avgSentiment = sentimentSum / total;

            // 对评论数量进行归一化处理（使用对数函数）
            double normalizedCount = Math.log1p(total) / Math.log1p(100); // 假设100条评论为基准
            if (normalizedCount > 1)
                normalizedCount = 1;

            // 设置三个维度的权重
            double weightRating = 0.5; // 评分权重50%
            double weightSentiment = 0.3; // 情感得分权重30%
            double weightCount = 0.2; // 评论数量权重20%

            // 综合计算最终得分
            double finalScore = (avgRating / 5.0 * weightRating) + // 评分部分
                    (avgSentiment * weightSentiment) + // 情感得分部分
                    (normalizedCount * weightCount); // 评论数量部分

            // 转换为5分制
            finalScore = finalScore * 5;

            // 格式化为一位小数
            String formattedScore = String.format("%.1f", finalScore);
            f.setScore(formattedScore);

            filmMapper.updateScore(f.getId(), f.getScore());

        } catch (Exception e) {
            // 如果出现异常，使用平均评分作为备选
            double avgRating = commentMapper.selectSum(f.getId()) / total;
            String formattedScore = String.format("%.1f", avgRating);
            f.setScore(formattedScore);
            filmMapper.updateScore(f.getId(), f.getScore());
            System.err.println("计算评分时出错，使用平均评分作为备选: " + e.getMessage());
        }
    }

    // 协同过滤推荐
    @Override
    public List<Film> selectCollaborativeFilteringRecommend(Integer userId) {
        // 获取用户历史评论的电影ID列表
        List<Integer> userCommentedFilmIds = commentMapper.selectFilmIdsByUserId(userId);

        // 如果用户没有评论过任何电影，直接返回随机推荐
        if (userCommentedFilmIds.isEmpty()) {
            return selectRecommend(userId);
        }

        // 获取用户历史评论的电影分类ID列表
        List<Integer> userCommentedCategoryIds = filmMapper.selectCategoryIdsByFilmIds(userCommentedFilmIds);

        // 获取所有电影
        List<Film> films = this.selectAll();

        // 过滤掉用户已经评论过的电影
        films = films.stream().filter(film -> !userCommentedFilmIds.contains(film.getId())).collect(Collectors.toList());

        // 计算相似度并排序
        films.sort((f1, f2) -> {
            double similarity1 = calculateCollaborativeSimilarity(userCommentedCategoryIds, f1.getCategoryId());
            double similarity2 = calculateCollaborativeSimilarity(userCommentedCategoryIds, f2.getCategoryId());
            return Double.compare(similarity2, similarity1); // 降序排列
        });

        // 取前三个
        List<Film> filmList = films.stream().limit(3).collect(Collectors.toList());

        // 如果推荐电影不足三个，补充随机推荐
        if (filmList.size() < 3) {
            List<Film> randomRecommendations = selectRecommend(userId);
            filmList.addAll(randomRecommendations.stream().limit(3 - filmList.size()).toList());
        }

        // 设置评分和总分
        for (Film f : filmList) {
            this.setScore(f);
        }
        return filmList;
    }

    // 计算电影与用户历史评论电影分类的相似度
    private double calculateCollaborativeSimilarity(List<Integer> userCommentedCategoryIds, Integer filmCategoryId) {
        // 计算电影分类与用户历史评论电影分类的相似度
        long matchCount = userCommentedCategoryIds.stream().filter(categoryId -> categoryId.equals(filmCategoryId)).count();
        return (double) matchCount / userCommentedCategoryIds.size();
    }

    // 随机推荐
    @Override
    public List<Film> selectRecommend(Integer filmId) {
        // 获取所有电影
        List<Film> films = this.selectAll();

        // 过滤掉当前电影
        films = films.stream().filter(film -> !film.getId().equals(filmId)).collect(Collectors.toList());

        // 随机排序
        Collections.shuffle(films);

        // 取前三个
        List<Film> filmList = films.stream().limit(3).collect(Collectors.toList());

        // 设置评分和总分
        for (Film f : filmList) {
            this.setScore(f);
        }
        return filmList;
    }

}
