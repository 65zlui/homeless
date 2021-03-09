package com.swpu.pojo;

import java.io.Serializable;

//
public class UserInfo extends MyPage implements Serializable {
    //描述userId列的
    private int userId;
    //描述userName列的
    private String userName;
    //描述userPwd列的
    private String userPwd;

    //描述查询条件condition的参数
    private String condition;
    //描述查询条件conValue的参数
    private String conValue;

    //描述修改密码的旧密码
    private String oldPwd;
    //描述修改密码的新密码
    private String newPwd;
    //头像
    private String url;



    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    // 描述盐值salt的列
    private String salt;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getCondition() {
        if(condition==null){
            return "";
        }
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getConValue() {
        if(conValue==null){
            return "";
        }
        return conValue;
    }

    public void setConValue(String conValue) {
        this.conValue = conValue;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}