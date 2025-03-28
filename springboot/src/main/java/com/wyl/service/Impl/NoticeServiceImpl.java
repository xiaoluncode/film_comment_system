package com.wyl.service.Impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyl.mapper.NoticeMapper;
import com.wyl.pojo.Notice;
import com.wyl.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
@作者：wyl
@创建时间：2025/1/7 18:48  
*/
@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;
    @Override
    public List<Notice> selectAll() {
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Notice::getId,Notice::getTitle,Notice::getContent,Notice::getTime)
               .orderByDesc(Notice::getTime);
        return noticeMapper.selectList(wrapper);
    }

    @Override
    public PageInfo<Notice> selectPage(Notice notice,Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        if (notice.getTitle() != null && !notice.getTitle().isEmpty()) {
            wrapper.like(Notice::getTitle, "%" + notice.getTitle().trim() + "%");
        }
        wrapper.orderByDesc(Notice::getTime);
        List<Notice> list = noticeMapper.selectList(wrapper);

        return PageInfo.of(list);
    }

    @Override
    public void add(Notice notice) {
        notice.setTime(DateUtil.now());
        noticeMapper.insert(notice);
    }

    @Override
    public void update(Notice notice) {
        noticeMapper.updateById(notice);
    }

    @Override
    public void deleteById(Integer id) {
        noticeMapper.deleteById(id);
    }
}
