package com.wyl.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyl.mapper.CategoryMapper;
import com.wyl.pojo.Category;
import com.wyl.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
@作者：wyl
@创建时间：2025/1/7 18:48  
*/
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public List<Category> selectAll() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Category::getId,Category::getName);
        return categoryMapper.selectList(wrapper);
    }

    @Override
    public PageInfo<Category> selectPage(Category category,Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        if (category.getName() != null && !category.getName().isEmpty()) {
            wrapper.like(Category::getName, "%" + category.getName().trim() + "%");
        }
        wrapper.orderByDesc(Category::getId);
        List<Category> list = categoryMapper.selectList(wrapper);

        return PageInfo.of(list);
    }

    @Override
    public Integer getCategoryIdByName(String name) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        Category category = categoryMapper.selectOne(wrapper);
        return category != null ? category.getId() : null;
    }

    @Override
    public void add(Category category) {
        categoryMapper.insert(category);
    }

    @Override
    public void update(Category category) {
        categoryMapper.updateById(category);
    }

    @Override
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }
}
