package com.swpu.controller;

import com.swpu.pojo.UserInfo;
import com.swpu.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@RequestMapping("/register")
public class RegisterController {
    //创建一个userInfoService的实现类对象
    @Autowired
    UserInfoService userInfoService;

    //Ajax注册控制层
    @RequestMapping("registerAjax")
    @ResponseBody  //不再返回页面，返回数据，数据转换为前端要求格式
    public HashMap<String,Object> registerAjax(UserInfo user){
        HashMap<String,Object> map = new HashMap<String,Object>();
        String info = userInfoService.register(user);
        map.put("info",info);
        return map;
    }
    //Form注册
    @RequestMapping("registerForm")
    public String registerForm(UserInfo user, ModelMap map){
        System.out.println("用户名："+user.getUserName());
        System.out.println("密码："+user.getUserPwd());

        String info = userInfoService.register(user);
        map.addAttribute("info",info);

        return "register";
    }
    //访问注册页面的方法
    @RequestMapping("/registerPage")
    public String loginPage(){
        return "register";
    }

}
