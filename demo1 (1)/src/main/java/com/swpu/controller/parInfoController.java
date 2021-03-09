package com.swpu.controller;

import com.swpu.pojo.ParInfo;
import com.swpu.pojo.UserInfo;
import com.swpu.service.ParInfoService;
import com.swpu.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
public class parInfoController {

    @Autowired
    ParInfoService parInfoService;

    //访问出队人员页面
    @RequestMapping("/parInfo")
    public String parInfo(ParInfo parInfo, ModelMap modelMap){
        //查询分页数据
        HashMap<String,Object> map = parInfoService.select(parInfo);
        modelMap.put("info",map);
        //把用户输入的查询条件传到前端
        modelMap.put("v",parInfo.getConValue());
        return "parInfo";
    }
}
