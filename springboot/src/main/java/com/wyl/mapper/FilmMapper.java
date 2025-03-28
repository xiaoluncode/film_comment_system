package com.wyl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyl.pojo.Film;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
@作者：wyl
@创建时间：2025/1/7 18:51  
*/
@Repository
public interface FilmMapper extends BaseMapper<Film> {
    /**
     * 查询所有电影信息，包含分类名称
     * 
     * @return 电影列表
     */
    List<Film> selectAll();

    // 自定义联表分页查询
    List<Film> selectFilmPage(@Param("film") Film film);

    /**
     * 更新电影的评分
     * 
     * @param id    电影ID
     * @param score 新的评分
     * @return 影响的行数
     */
    int updateScore(Integer id, String score);

}
