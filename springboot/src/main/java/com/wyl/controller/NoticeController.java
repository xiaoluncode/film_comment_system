package com.wyl.controller;

import com.github.pagehelper.PageInfo;
import com.wyl.common.Result;
import com.wyl.pojo.Notice;
import com.wyl.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/*
@作者：wyl
@创建时间：2025/1/7 18:43  
*/
@RestController
@RequestMapping("notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @PostMapping("add")
    public Result add(@RequestBody Notice notice){
        noticeService.add(notice);
        return Result.success();
    }

    @PutMapping("update")
    public Result update(@RequestBody Notice notice){
        noticeService.update(notice);
        return Result.success();
    }

    @DeleteMapping("delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        noticeService.deleteById(id);
        return Result.success();
    }
    /*
    * 查询所有*/
    @GetMapping("/selectAll")
    public Result selectAll(){
        List<Notice> list = noticeService.selectAll();
        return Result.success(list);
    }
     @GetMapping("/selectPage")
        public Result selectPage(Notice notice,
                                 @RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize){
         PageInfo<Notice> noticePageInfo = noticeService.selectPage(notice,pageNum, pageSize);
         return Result.success(noticePageInfo);
        }

    
}
