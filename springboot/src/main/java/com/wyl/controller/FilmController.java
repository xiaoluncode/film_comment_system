package com.wyl.controller;

import com.github.pagehelper.PageInfo;
import com.wyl.common.Result;
import com.wyl.mapper.CommentMapper;
import com.wyl.pojo.Film;
import com.wyl.service.FilmService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
@作者：wyl
@创建时间：2025/1/7 18:43  
*/
@RestController
@RequestMapping("film")
public class FilmController {
    @Autowired
    private FilmService filmService;


    @PostMapping("add")
    public Result add(@RequestBody Film film){
        filmService.add(film);
        return Result.success();
    }

    @PutMapping("update")
    public Result update(@RequestBody Film film){
        filmService.update(film);
        return Result.success();
    }

    @DeleteMapping("delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        filmService.deleteById(id);
        return Result.success();
    }
    /*
    * 查询所有*/
    @GetMapping("/selectAll")
    public Result selectAll(){
        List<Film> list = filmService.selectAll();
        return Result.success(list);
    }

    @GetMapping("/selectRanking")
    public Result selectRanking(){
        List<Film> list = filmService.selectRanking();
        return Result.success(list);
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Film film = filmService.selectById(id);
        return Result.success(film);
    }
     @GetMapping("/selectPage")
        public Result selectPage(Film film,
                                 @RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize){
         PageInfo<Film> filmPageInfo = filmService.selectPage(film,pageNum, pageSize);

         return Result.success(filmPageInfo);
        }

     @GetMapping("/selectRecommend/{id}")
    public Result selectRecommend(@PathVariable Integer id){
        List<Film> list = filmService.selectRecommend(id);
        return Result.success(list);
     }
    
}
