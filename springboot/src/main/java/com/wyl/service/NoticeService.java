package com.wyl.service;

import com.github.pagehelper.PageInfo;
import com.wyl.pojo.Notice;

import java.util.List;

/*
@作者：wyl
@创建时间：2025/1/7 18:48  
*/
public interface NoticeService {
    List<Notice> selectAll();

    PageInfo<Notice> selectPage(Notice notice,Integer pageNum, Integer pageSize);

    void add(Notice notice);

    void update(Notice notice);

    void deleteById(Integer id);
}
