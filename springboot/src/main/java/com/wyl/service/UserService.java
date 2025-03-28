package com.wyl.service;

import com.github.pagehelper.PageInfo;
import com.wyl.pojo.Account;
import com.wyl.pojo.User;

import java.util.List;

/*
@作者：wyl
@创建时间：2025/1/7 18:28  
*/
public interface UserService {
    void add(User user);

    void deleteById(Integer id);

    void updateById(User user);

    User selectById(Integer id);

    List<User> selectAll(User user);

    PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize);

    Account login(Account account);

    void updatePassword(Account account);
}
