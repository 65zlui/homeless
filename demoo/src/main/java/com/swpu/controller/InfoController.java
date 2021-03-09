package com.swpu.controller;

import com.swpu.pojo.Info;
import com.swpu.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/info")
public class InfoController {

    @Autowired
    InfoService infoService;

    //访问饼图页面
    @RequestMapping("/bing")
    public String bing(){
        return "info/bing";
    }
    //处理饼图ajax数据
    @RequestMapping("/bingAjax")
    @ResponseBody
    public HashMap<String,Object> bingAjax(Info info){
        return infoService.bing(info);
    }

    //查询时间
    @RequestMapping("/time")
    @ResponseBody
    public List<Info> time(){
        return infoService.selectTime();
    }

    //访问折现图页面
    @RequestMapping("/zhexian")
    public String zhexian(){
        return "info/zhexian";
    }
    //查询省份
    @RequestMapping("/province")
    @ResponseBody
    public List<Info> province(){
        return infoService.selectPro();
    }
    //处折现图ajax数据
    @RequestMapping("/zhexianAjax")
    @ResponseBody
    public HashMap<String,Object> zhexianAjax(Info info){
        return infoService.zhexian(info);
    }

    //访问中国地图页面
    @RequestMapping("/china")
    public String china(){
        return "info/china";
    }

    //访问世界地图页面
    @RequestMapping("/world")
    public String world(){
        return "info/world";
    }

}
