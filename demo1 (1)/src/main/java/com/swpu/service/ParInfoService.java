package com.swpu.service;

import com.swpu.pojo.ParInfo;

import java.util.HashMap;

public interface ParInfoService {
    //查询出队人员
    HashMap<String,Object> select(ParInfo parInfo);
}
