package com.yang.springboot.service;

import com.yang.springboot.bean.Department;
import com.yang.springboot.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

@Service
public class DeptService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Qualifier("deptCacheManager")//指定deptCacheManager作为缓存管理器
    @Autowired
    RedisCacheManager deptCacheManager;
    /**
     * 缓存的数据能够存入redis
     * 第二次从缓存中查询就不能反序列化回来
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "dept",cacheManager = "deptCacheManager")
    public Department getDeptById(Integer id) {
        System.out.println("查询部门"+id);
        Department department = departmentMapper.getDeptById(id);
        return department;
    }

    /**
     * 通过编码的方式往缓存放入数据
     * @param id
     * @return
     */
//    public Department getDeptById(Integer id) {
//        System.out.println("查询部门"+id);
//        Department department = departmentMapper.getDeptById(id);
//        //获取某个缓存
//        Cache dept = deptCacheManager.getCache("dept");
//        dept.put("dept:1",department);
//        return department;
//    }
}
