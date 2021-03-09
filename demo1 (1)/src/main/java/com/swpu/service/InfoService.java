package com.swpu.service;

import com.swpu.pojo.Info;

import java.util.HashMap;
import java.util.List;

public interface InfoService {

    //饼图
    HashMap<String ,Object> bing(Info info);
    //查时间
    List<Info> selectTime();
    //查省份
    List<Info> selectPro();
    //折线图
    HashMap<String ,Object> zhexian(Info info);
}
