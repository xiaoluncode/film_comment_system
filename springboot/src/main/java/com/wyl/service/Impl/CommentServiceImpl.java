package com.wyl.service.Impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyl.mapper.CommentMapper;
import com.wyl.pojo.Comment;
import com.wyl.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
@作者：wyl
@创建时间：2025/1/7 18:48  
*/
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public List<Comment> selectAll() {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Comment::getId,Comment::getFilmId,Comment::getScore,Comment::getComment,Comment::getUserId,Comment::getTime,Comment::getType);
        return commentMapper.selectList(wrapper);
    }

    @Override
    public PageInfo<Comment> selectPage(Comment comment,Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Comment> comments = commentMapper.selectCommentPage(comment);
        return new PageInfo<>(comments);
    }

    @Override
    public Integer getCommentIdByName(String name) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        Comment comment = commentMapper.selectOne(wrapper);
        return comment != null ? comment.getId() : null;
    }

    @Override
    public void add(Comment comment) {
        comment.setTime(DateUtil.now());
        commentMapper.insert(comment);
    }

    @Override
    public void update(Comment comment) {
        commentMapper.updateById(comment);
    }

    @Override
    public void deleteById(Integer id) {
        commentMapper.deleteById(id);
    }
}
