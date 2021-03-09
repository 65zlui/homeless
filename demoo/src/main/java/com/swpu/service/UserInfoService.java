package com.swpu.service;

import com.swpu.pojo.UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface UserInfoService {

    //登录方法
    String login(UserInfo user,HttpServletRequest request);
    //注册方法
    String register(UserInfo user);
    //邮件发送
    HashMap<String,Object> sendCode(String toEmail, HttpServletRequest request);
    //查询
    HashMap<String,Object> select(UserInfo user);
    //根据UserId查询
    UserInfo selectByUserId(UserInfo user);
    //修改
    String update(UserInfo user);
    //删除
    String del(UserInfo user);
    //修改密码
    String updatePwd(UserInfo user,HttpServletRequest request);
    //修改头像
    String updateAvatar(UserInfo user,HttpServletRequest request);

}
