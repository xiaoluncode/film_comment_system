package com.wyl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyl.pojo.Comment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
@作者：wyl
@创建时间：2025/1/7 18:51  
*/
@Repository
public interface CommentMapper extends BaseMapper<Comment> {
     List<Comment> selectCommentPage(@Param("comment")Comment comment);
    @Select("select count(*) from comment where film_id=#{filmId}")
    int selectTotal(Integer filmId);
    @Select("select sum(score) from comment where film_id=#{id}")
    Double selectSum(Integer id);
    //获取每条评论的评论文本
    @Select("SELECT comment FROM comment WHERE film_id = #{filmId}")
    List<String> selectCommentContents(@Param("filmId") Integer filmId);


    List<Integer> selectFilmIdsByUserId(Integer userId);
}
