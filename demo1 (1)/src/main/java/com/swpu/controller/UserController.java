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
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserInfoService userInfoService;
    //访问userList页面
    @RequestMapping("/list")
    public String userList(UserInfo user, ModelMap modelMap){
        //查询分页数据
        HashMap<String,Object> map = userInfoService.select(user);
        modelMap.put("info",map);
        //把用户输入的查询条件传到前端
        modelMap.put("v",user.getConValue());
        return "user/user-list";
    }

    //处理qianduan查询表格的ajax数据
    @RequestMapping("/listAjax")
    @ResponseBody
    public HashMap<String,Object> listAjax(UserInfo user){
        return userInfoService.select(user);
    }


    //打开修改页面
    @RequestMapping("/editPage")
    public String editPage(UserInfo user, ModelMap m){
        //根据userId查询
        UserInfo u = userInfoService.selectByUserId(user);
        m.addAttribute("user",u);
        return "user/user-edit";
    }

    //处理修改的ajax请求
    @RequestMapping("/edit")
    @ResponseBody
    public HashMap<String,Object> edit(UserInfo user){
        HashMap<String,Object> map = new HashMap<>();
        String info = userInfoService.update(user);
        map.put("info",info);
        return map;
    }

    //处理删除的ajax请求
    @RequestMapping("/del")
    @ResponseBody
    public HashMap<String,Object> del(UserInfo user){
        HashMap<String,Object> map = new HashMap<>();
        String info = userInfoService.del(user);
        map.put("info",info);
        return map;
    }
}
