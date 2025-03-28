package com.wyl.controller;

import com.github.pagehelper.PageInfo;
import com.wyl.common.Result;
import com.wyl.pojo.Comment;
import com.wyl.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
@作者：wyl
@创建时间：2025/1/7 18:43  
*/
@RestController
@RequestMapping("comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("add")
    public Result add(@RequestBody Comment comment){
        commentService.add(comment);
        return Result.success();
    }

    @PutMapping("update")
    public Result update(@RequestBody Comment comment){
        commentService.update(comment);
        return Result.success();
    }

    @DeleteMapping("delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        commentService.deleteById(id);
        return Result.success();
    }
    /*
    * 查询所有*/
    @GetMapping("/selectAll")
    public Result selectAll(){
        List<Comment> list = commentService.selectAll();
        return Result.success(list);
    }
     @GetMapping("/selectPage")
        public Result selectPage(Comment comment,
                                 @RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize){
         PageInfo<Comment> commentPageInfo = commentService.selectPage(comment,pageNum, pageSize);
         return Result.success(commentPageInfo);
        }

    
}
