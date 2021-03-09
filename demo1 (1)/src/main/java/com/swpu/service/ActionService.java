package com.swpu.service;

import com.swpu.pojo.Action;

import java.util.HashMap;

public interface ActionService {
    //填写救援行动
    String fill(Action action);
    //查询救援行动
    HashMap<String,Object> find(Action action);
    //根据rId查询
    Action selectByrId(Action action);
    //删除救援行动
    String delAction(Action action);
}
