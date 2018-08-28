package com.yang.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 搭建基本环境
 * 1.创建数据库表
 * 2.创建javaBean
 * 3.整合mybatis
 *      1)配置数据源信息
 *      2）使用注解版mybatis
 *          ①@MapperScan指定需要扫描的mapper接口所在的包
 *
 * 快速体验缓存
 *  1.开启给予注解的缓存
 *  2.标注缓存注解即可
 */
@MapperScan("com.yang.springboot.mapper")
@SpringBootApplication
@EnableCaching //开启缓存
public class SpringbootCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootCacheApplication.class, args);
    }
}
