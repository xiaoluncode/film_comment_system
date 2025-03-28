package com.wyl.service;

import com.github.pagehelper.PageInfo;
import com.wyl.pojo.Category;

import java.util.List;

/*
@作者：wyl
@创建时间：2025/1/31 4:44  
*/
public interface CategoryService {
 void add(Category category);

 void update(Category category);

 void deleteById(Integer id);

 List<Category> selectAll();

 PageInfo<Category> selectPage(Category category, Integer pageNum, Integer pageSize);

    Integer getCategoryIdByName(String categoryName);
}
