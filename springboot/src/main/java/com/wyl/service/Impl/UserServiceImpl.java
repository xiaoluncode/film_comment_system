package com.wyl.service.Impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyl.exception.CustomException;
import com.wyl.mapper.UserMapper;
import com.wyl.pojo.Account;
import com.wyl.pojo.User;
import com.wyl.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户业务处理
 **/
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 新增
     */
    @Override
    public void add(User user) {
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (ObjectUtil.isNotNull(dbUser)) {
            throw new CustomException("用户不存在");
        }
        if (ObjectUtil.isEmpty(user.getPassword())) {
            user.setPassword("123");
        }
        if (ObjectUtil.isEmpty(user.getName())) {
            user.setName(user.getUsername());
        }
        user.setRole("USER");
        userMapper.insert(user);
    }

    /**
     * 删除
     */
    @Override
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    /**
     * 修改
     */
    @Override
    public void updateById(User user) {
        userMapper.updateById(user);
    }

    /**
     * 根据ID查询
     */
    @Override
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    @Override
    public List<User> selectAll(User user) {
        return userMapper.selectAll(user);
    }

    /**
     * 分页查询
     */
    @Override
    public PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.selectAll(user);
        return PageInfo.of(list);
    }

    /**
     * 登录
     */
    @Override
    public Account login(Account account) {
        Account dbUser = userMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException("用户不存在");
        }
        if (!account.getPassword().equals(dbUser.getPassword())) {
            throw new CustomException("账号或密码错误");
        }
        return dbUser;
    }

    /**
     * 修改密码
     */
    @Override
    public void updatePassword(Account account) {
        User dbUser = userMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbUser)) {
            throw new CustomException("用户不存在");
        }
        if (!account.getPassword().equals(dbUser.getPassword())) {
            throw new CustomException("原密码错误");
        }
        dbUser.setPassword(account.getNewPassword());
        userMapper.updateById(dbUser);
    }

}