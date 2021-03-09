package com.swpu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller  //前端页面交互类
@RequestMapping("/hello") //定义 这个类的映射地址，指的是浏览器访问这个类的地址
public class HelloWorldController {

    //定义一个访问helloworld页面的方法
    @RequestMapping("/test")
    public String test(){
        //return 的值是访问某个页面的前缀（helloworld.html）
        return "helloworld";
    }
}
