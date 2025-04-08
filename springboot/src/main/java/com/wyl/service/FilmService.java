package com.wyl.service;

import com.github.pagehelper.PageInfo;
import com.wyl.pojo.Comment;
import com.wyl.pojo.Film;

import java.util.List;

/*
@作者：wyl
@创建时间：2025/1/31 5:38  
*/
public interface FilmService {
 void add(Film film);

 void update(Film film);

 void deleteById(Integer id);

 List<Film> selectAll();

 PageInfo<Film> selectPage(Film film, Integer pageNum, Integer pageSize);

    Film selectById(Integer id);

 List<Film> selectRecommend(Integer filmId);

 List<Film> selectRanking();
 List<Film> selectCollaborativeFilteringRecommend(Integer userId);
}
