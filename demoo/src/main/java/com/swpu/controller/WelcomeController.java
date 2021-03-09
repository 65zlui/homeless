package com.swpu.controller;

import com.swpu.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class WelcomeController {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    //获取总赞数
    @RequestMapping("/loadZan")
    @ResponseBody
    public HashMap<String,Object> loadZan(HttpServletRequest request){
        HashMap<String,Object> map = new HashMap<>();
        Object obj = redisTemplate.opsForValue().get("arc");
        if(obj==null){
            map.put("info",0);
        }else {
            map.put("info",obj);
        }
        return map;
    }

    @RequestMapping("/zan")
    @ResponseBody
    public HashMap<String,Object> zan(HttpServletRequest request){
        HashMap<String,Object> map = new HashMap<>();
        //获取用户名
        HttpSession session = request.getSession();
        UserInfo user = (UserInfo) session.getAttribute("user");
        //用户键名
        String key = user.getUserName()+"#zan#";
        //文章键名
        String wenKey = "arc";
        //判断用户是否点过赞
        Object obj = redisTemplate.opsForValue().get(key);
        if(obj==null){//未点过
            //设置用户已点赞
            redisTemplate.opsForValue().set(key,"点赞状态");
            //文章赞数+1
            redisTemplate.opsForValue().increment(wenKey,1);
        }else{//点过
            //文章赞数-1
            redisTemplate.opsForValue().decrement(wenKey,1);
            //改变用户点赞状态
            redisTemplate.delete(key);
        }
        int n = (Integer) redisTemplate.opsForValue().get(wenKey);
        map.put("info",n);
        return map;
    }

}
