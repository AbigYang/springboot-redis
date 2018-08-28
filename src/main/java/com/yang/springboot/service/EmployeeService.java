package com.yang.springboot.service;

import com.yang.springboot.bean.Employee;
import com.yang.springboot.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames = "emp",cacheManager = "employeeCacheManager")//全局配置，下面的就不用配置这个属性了
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 将方法的运行结果进行缓存，
     * 以后再摇相同的数据，直接从缓存中获取，不用调用方法和数据库
     *
     * 几个属性：
     *  cacheNames/value:指定缓存组件的名字，可以指定多个缓存
     *  key:缓存数据使用的key,默认使用方法参数的值 SpEL:#id:参数id的值
     *  keyGenerator:key的生成器，可以自己制定key的生成器
     *      key/keyGenerator:二选一指定
     *  cacheManager：指定缓存管理器
     *  cacheResolver：缓存解析器 和cacheManager二选一
     *  condition：指定符合条件的情况下才缓存
     *      condition = "#a0>1"时才缓存
     *  unless：否定缓存，满足条件时不缓存，可以获取到结果进行缓存
     *      unless = "result == null"
     *  sync：是否使用异步模式3
     * @param id
     * @return
     */
    @Cacheable(cacheNames = "emp"/*,condition = "#a0>1"*/)
    public Employee getEmp(Integer id) {
        System.out.println("查询"+id+"号员工");
        Employee emp = employeeMapper.getEmpById(id);
        return emp;
    }

    /**
     * @CachePut：既调用方法，又更新缓存数据
     * 先调用方法，再更新缓存
     * 同步更新缓存 key值要相同
     */
    @CachePut(cacheNames = "emp",key = "#employee.id")
    public Employee updateEmp(Employee employee) {
        System.out.println("updateEmp"+employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }

    /**
     * @CacheEvict：缓存清除
     * allEntries:清除emp中所有缓存
     * beforeInvocation：缓存的清除是否在方法之前执行，默认false
     * 方法运行之后执行如果出现异常缓存就不会清除
     */
    @CacheEvict(cacheNames = "emp",key = "#id")
    public void deleteEmp(Integer id) {
        System.out.println("delete:"+id);
//        employeeMapper.deleteEmpById(id);
    }

    /**
     * 定义复杂的缓存规则
     * @param lastName
     * @return
     */
    @Caching(
            cacheable = {
                    @Cacheable(value = "emp",key = "#lastName")
            },
            put = {
                    @CachePut(value = "emp",key = "#result.id"),
                    @CachePut(value = "emp",key = "#result.email")
            }
    )
    public Employee getEmpLastName(String lastName) {
        return employeeMapper.getEmpByLastName(lastName);
    }
}
