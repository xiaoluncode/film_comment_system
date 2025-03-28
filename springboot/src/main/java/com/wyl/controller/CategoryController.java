package com.wyl.controller;

import com.github.pagehelper.PageInfo;
import com.wyl.common.Result;
import com.wyl.pojo.Category;
import com.wyl.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
@作者：wyl
@创建时间：2025/1/7 18:43  
*/
@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("add")
    public Result add(@RequestBody Category category){
        categoryService.add(category);
        return Result.success();
    }

    @PutMapping("update")
    public Result update(@RequestBody Category category){
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping("delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        categoryService.deleteById(id);
        return Result.success();
    }
    /*
    * 查询所有*/
    @GetMapping("/selectAll")
    public Result selectAll(){
        List<Category> list = categoryService.selectAll();
        return Result.success(list);
    }
     @GetMapping("/selectPage")
        public Result selectPage(Category category,
                                 @RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize){
         PageInfo<Category> categoryPageInfo = categoryService.selectPage(category,pageNum, pageSize);
         return Result.success(categoryPageInfo);
        }

    
}
