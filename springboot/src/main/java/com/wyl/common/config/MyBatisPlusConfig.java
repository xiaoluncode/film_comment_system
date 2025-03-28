//package com.wyl.common.config;
//
//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor; // 添加此行导入
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///*
//@作者：wyl
//@创建时间：2025/1/31 9:30
//*/
//@Configuration
//public class MyBatisPlusConfig {
//    @Bean
//    public MybatisPlusInterceptor mybatisPlusInterceptor() {
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
//        return interceptor;
//    }
//}
