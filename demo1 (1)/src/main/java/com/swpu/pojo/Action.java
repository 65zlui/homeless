package com.swpu.pojo;

import java.io.Serializable;
import java.util.Date;

public class Action extends MyPage implements Serializable {
    //救援行动编号
    private int rId;
    //失踪人姓名
    private String misName;
    //失踪人户籍
    private String misDomicile;
    //失踪地点
    private String misAddr;
    //失踪人特征描述
    private String misInfo;
    //失踪人图片
    private String misImg;
    //失踪人性别
    private String misSex;
    //失踪人出生日期
    private String birthDate;
    //失踪日期
    private String misDate;
    //失踪时身高
    private Double misHeight;
    //联系人姓名
    private String conName;
    //联系人与失踪人关系
    private String relation;
    //联系人电话
    private String conTel;
    //联系人邮箱
    private String conMail;
    //联系人地址
    private String conAddr;
    //联系人QQ或微信
    private String QQorWeChat;

    //户籍地址拼接成misDomicile
    private String dProvince;
    private String dCity;
    private String dCounty;
    private String dDomicile;

    //失踪地址拼接成misAddr
    private String mProvince;
    private String mCity;
    private String mCounty;
    private String dMisAddr;

    //描述查询条件condition的参数
    private String condition;
    //描述查询条件conValue的参数
    private String conValue;

    public int getrId() {
        return rId;
    }

    public void setrId(int rId) {
        this.rId = rId;
    }

    public String getMisDomicile() {
        return misDomicile;
    }

    public void setMisDomicile(String misDomicile) {
        this.misDomicile = misDomicile;
    }

    public String getMisAddr() {
        return misAddr;
    }

    public void setMisAddr(String misAddr) {
        this.misAddr = misAddr;
    }

    public String getMisInfo() {
        return misInfo;
    }

    public void setMisInfo(String misInfo) {
        this.misInfo = misInfo;
    }

    public String getMisImg() {
        return misImg;
    }

    public void setMisImg(String misImg) {
        this.misImg = misImg;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getConTel() {
        return conTel;
    }

    public void setConTel(String conTel) {
        this.conTel = conTel;
    }

    public String getConMail() {
        return conMail;
    }

    public void setConMail(String conMail) {
        this.conMail = conMail;
    }

    public String getConAddr() {
        return conAddr;
    }

    public void setConAddr(String conAddr) {
        this.conAddr = conAddr;
    }

    public String getQQorWeChat() {
        return QQorWeChat;
    }

    public void setQQorWeChat(String QQorWeChat) {
        this.QQorWeChat = QQorWeChat;
    }

    public String getMisSex() {
        return misSex;
    }

    public void setMisSex(String misSex) {
        this.misSex = misSex;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Double getMisHeight() {
        return misHeight;
    }

    public void setMisHeight(Double misHeight) {
        this.misHeight = misHeight;
    }

    public String getMisDate() {
        return misDate;
    }

    public void setMisDate(String misDate) {
        this.misDate = misDate;
    }

    public String getdProvince() {
        return dProvince;
    }

    public void setdProvince(String dProvince) {
        this.dProvince = dProvince;
    }

    public String getdCity() {
        return dCity;
    }

    public void setdCity(String dCity) {
        this.dCity = dCity;
    }

    public String getdCounty() {
        return dCounty;
    }

    public void setdCounty(String dCounty) {
        this.dCounty = dCounty;
    }

    public String getdDomicile() {
        return dDomicile;
    }

    public void setdDomicile(String dDomicile) {
        this.dDomicile = dDomicile;
    }

    public String getmProvince() {
        return mProvince;
    }

    public void setmProvince(String mProvince) {
        this.mProvince = mProvince;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmCounty() {
        return mCounty;
    }

    public void setmCounty(String mCounty) {
        this.mCounty = mCounty;
    }

    public String getdMisAddr() {
        return dMisAddr;
    }

    public void setdMisAddr(String dMisAddr) {
        this.dMisAddr = dMisAddr;
    }

    public String getMisName() {
        return misName;
    }

    public void setMisName(String misName) {
        this.misName = misName;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getConValue() {
        return conValue;
    }

    public void setConValue(String conValue) {
        this.conValue = conValue;
    }
}
