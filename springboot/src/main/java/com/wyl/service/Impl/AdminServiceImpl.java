package com.wyl.service.Impl;

import cn.hutool.core.util.ObjectUtil;
import com.wyl.pojo.Account;
import com.wyl.pojo.Admin;
import com.wyl.exception.CustomException;
import com.wyl.mapper.AdminMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyl.service.AdminService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 管理员业务处理
 **/
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    /**
     * 新增
     */
    @Override
    public void add(Admin admin) {
        Admin dbAdmin = adminMapper.selectByUsername(admin.getUsername());
        if (ObjectUtil.isNotNull(dbAdmin)) {
            throw new CustomException("用户不存在");
        }
        if (ObjectUtil.isEmpty(admin.getPassword())) {
            admin.setPassword("admin");
        }
        if (ObjectUtil.isEmpty(admin.getName())) {
            admin.setName(admin.getUsername());
        }
        admin.setRole("ADMIN");
        adminMapper.insert(admin);
    }

    /**
     * 删除
     */
    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    /**
     * 修改
     */
    @Override
    public void updateById(Admin admin) {
        adminMapper.updateById(admin);
    }

    /**
     * 根据ID查询
     */
    @Override
    public Admin selectById(Integer id) {
        return adminMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    @Override
    public List<Admin> selectAll(Admin admin) {
        return adminMapper.selectAll(admin);
    }

    /**
     * 分页查询
     */
    @Override
    public PageInfo<Admin> selectPage(Admin admin, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> list = adminMapper.selectAll(admin);
        return PageInfo.of(list);
    }

    /**
     * 登录
     */
    @Override
    public Account login(Account account) {
        Account dbAdmin = adminMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbAdmin)) {
            throw new CustomException("用户不存在");
        }
        if (!account.getPassword().equals(dbAdmin.getPassword())) {
            throw new CustomException("账号或密码错误");
        }
        return dbAdmin;
    }

    /**
     * 修改密码
     */
    @Override
    public void updatePassword(Account account) {
        Admin dbAdmin = adminMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbAdmin)) {
            throw new CustomException("用户不存在");
        }
        if (!account.getPassword().equals(dbAdmin.getPassword())) {
            throw new CustomException("原密码错误");
        }
        dbAdmin.setPassword(account.getNewPassword());
        adminMapper.updateById(dbAdmin);
    }

}