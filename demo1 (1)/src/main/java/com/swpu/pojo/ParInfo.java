package com.swpu.pojo;

import java.io.Serializable;

//出对人员信息对象
public class ParInfo extends MyPage implements Serializable {
    private int rId;
    private int vId;
    private String vName;
    private String vTel;
    private String vTrans;
    private String vEquip;

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

    public int getvId() {
        return vId;
    }

    public void setvId(int vId) {
        this.vId = vId;
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public String getvTel() {
        return vTel;
    }

    public void setvTel(String vTel) {
        this.vTel = vTel;
    }

    public String getvTrans() {
        return vTrans;
    }

    public void setvTrans(String vTrans) {
        this.vTrans = vTrans;
    }

    public String getvEquip() {
        return vEquip;
    }

    public void setvEquip(String vEquip) {
        this.vEquip = vEquip;
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
