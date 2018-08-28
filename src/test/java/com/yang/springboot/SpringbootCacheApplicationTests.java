package com.yang.springboot;

import com.yang.springboot.bean.Employee;
import com.yang.springboot.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootCacheApplicationTests {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;//操作字符串

    @Autowired
    RedisTemplate redisTemplate;//操作对象

    @Autowired
    RedisTemplate<Object, Employee> empTemplate;

    /**
     * 五大数据类型
     * String,List,Set,Hash,ZSet(有序集合)
     * stringRedisTemplate.opsForValue()----String
     * stringRedisTemplate.opsForList()-----List
     * stringRedisTemplate.opsForSet()------Set
     * stringRedisTemplate.opsForHash()------Hash
     * stringRedisTemplate.opsForZSet()------ZSet
     */
    @Test
    public void testRedis01() {
//        stringRedisTemplate.opsForValue().append("msg", "helloworld");
        String msg = stringRedisTemplate.opsForValue().get("msg");
        System.out.println(msg);
    }
    //测试保存对象
    @Test
    public void testRedis02() {
        Employee emp = employeeMapper.getEmpById(1);
        //默认保存对象以序列化的方式
//        redisTemplate.opsForValue().set("emp-01",emp);
        //将数据以json方式保存
        //改变默认的序列化规则--MyRedisConfig
        empTemplate.opsForValue().set("emp-01",emp);
    }

    @Test
    public void contextLoads() {
        Employee emp = employeeMapper.getEmpById(1);
        System.out.println(emp);
    }

}
