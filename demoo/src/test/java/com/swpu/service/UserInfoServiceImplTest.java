package com.swpu.service;

import com.swpu.pojo.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoServiceImplTest {

    //创建操作redis库的操作对象
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    //redis数据的设置和提取
    @Test
    public void add(){
        //设值
        //set(键名，值，时间长度，时间单位)
        redisTemplate.opsForValue().set("name","张三",20, TimeUnit.SECONDS);

        //获取失效时间(默认时间单位是秒)
        Long one =redisTemplate.getExpire("name");
        System.out.println("指定失效时间="+one);
        //获取键名的剩余有效时间
        Long left =redisTemplate.getExpire("name",TimeUnit.SECONDS);
        System.out.println("left="+left);

        //取值
        String name = redisTemplate.opsForValue().get("name")+"";
        System.out.println("name="+name);

        //插入一个UserInfo对象到redis中
        UserInfo user = new UserInfo();
        user.setUserId(1);
        user.setUserName("rock");
        user.setUserPwd("123");
        redisTemplate.opsForValue().set("user",user);
        UserInfo u = (UserInfo) redisTemplate.opsForValue().get("user");
        System.out.println("id="+u.getUserId());
        System.out.println("name="+u.getUserName());
        System.out.println("pwd="+u.getUserPwd());

    }
}