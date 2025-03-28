package com.wyl.service;

import com.github.pagehelper.PageInfo;
import com.wyl.pojo.Account;
import com.wyl.pojo.Admin;

import java.util.List;

/*
@作者：wyl
@创建时间：2025/1/7 18:28  
*/
public interface AdminService {
    void add(Admin admin);

    void deleteById(Integer id);

    void updateById(Admin admin);

    Admin selectById(Integer id);

    List<Admin> selectAll(Admin admin);

    PageInfo<Admin> selectPage(Admin admin, Integer pageNum, Integer pageSize);

    Account login(Account account);

    void updatePassword(Account account);
}
