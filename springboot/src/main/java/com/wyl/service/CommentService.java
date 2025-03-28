package com.wyl.service;

import com.github.pagehelper.PageInfo;
import com.wyl.pojo.Comment;

import java.util.List;

/*
@作者：wyl
@创建时间：2025/1/31 4:44  
*/
public interface CommentService {
 void add(Comment comment);

 void update(Comment comment);

 void deleteById(Integer id);

 List<Comment> selectAll();

 PageInfo<Comment> selectPage(Comment comment, Integer pageNum, Integer pageSize);

    Integer getCommentIdByName(String commentName);
}
