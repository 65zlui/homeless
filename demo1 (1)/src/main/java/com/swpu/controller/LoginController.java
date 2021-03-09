package com.swpu.controller;

import com.swpu.pojo.UserInfo;
import com.swpu.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller //前端页面交互类
@RequestMapping("/login") //定义 这个类的映射地址，指的是浏览器访问这个类的地址
public class LoginController {

    //创建一个userInfoService的实现类对象
    @Autowired
    UserInfoService userInfoService;

    //接受用户发送的登录信息
    //ModelMap把服务端的值传给前端
    @RequestMapping("/loginForm")
    public String loginForm(UserInfo user, ModelMap map,HttpServletRequest request){
        System.out.println("用户名："+user.getUserName());
        System.out.println("密码："+user.getUserPwd());

        String info = userInfoService.login(user,request);
        map.addAttribute("info",info);
        //返回登录页面
        return "login";
    }

    //访问登录页面的方法
    @RequestMapping("/loginPage")
    public String loginPage(){
        return "login";
    }

    //Ajax登录控制层
    @RequestMapping("loginAjax")
    @ResponseBody  //不再返回页面，返回数据，数据转换为前端要求格式
    public HashMap<String,Object> loginAjax(UserInfo user,HttpServletRequest request){
        HashMap<String,Object> map = new HashMap<String,Object>();
        String info = userInfoService.login(user,request);
        map.put("info",info);
        return map;
    }

    //处理邮件发送请求
    @RequestMapping("/sendEmail")
    @ResponseBody
    public HashMap<String,Object> sendEmail(String email, HttpServletRequest request){
        return userInfoService.sendCode(email,request);
    }

    //创建操作redis库的操作对象
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    //邮件登录,输入验证码与邮件验证码是否一致
    @RequestMapping("/emailLogin")
    @ResponseBody
    public HashMap<String,Object> emailLogin(String code, HttpServletRequest request){
        HashMap<String,Object> map = new HashMap<String,Object>();
/*        //获取session对象
        HttpSession session = request.getSession();
        //取出存到session中的验证码
        String valCode = session.getAttribute("valCode")+"";*/

        //获取redis中的验证码
        String valCode = redisTemplate.opsForValue().get("valCode")+"";
        if(valCode.equals(code)){//判断输入验证码与邮件验证码是否一致
            map.put("info","邮箱登录成功");
        }else{
            map.put("info","邮箱登录失败");
        }
        return map;
    }
}
