package com.swpu.controller;

import com.swpu.pojo.Action;
import com.swpu.pojo.ParInfo;
import com.swpu.pojo.UserInfo;
import com.swpu.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("/action")
public class ActionController {
    @Autowired
    ActionService actionService;

    //访问填写救援行动页面
    @RequestMapping("/fillAction")
    public String fillAction(){
        return "fillAction";
    }

    //打开修改救援行动页面
    @RequestMapping("/updateAction")
    public String updateAction(Action action, ModelMap m){
        //根据rId查询
        Action a = actionService.selectByrId(action);
        m.addAttribute("a",a);
        return "updateAction";
    }

    //访问救援行动页面
    @RequestMapping("/manageAction")
    public String manageAction(Action action, ModelMap modelMap){
        //查询分页数据
        HashMap<String,Object> map = actionService.find(action);
        modelMap.put("info",map);
        //把用户输入的查询条件传到前端
        modelMap.put("v",action.getConValue());
        return "manageAction";
    }

    //保存救援行动表
    @RequestMapping("/saveAction")
    @ResponseBody
    public HashMap<String,Object> saveAction(Action action){
        HashMap<String,Object> map = new HashMap<>();
        String info = actionService.fill(action);
        map.put("info",info);
        return map;
    }

    //处理删除action请求
    @RequestMapping("/delAction")
    @ResponseBody
    public HashMap<String,Object> delAction(Action action){
        HashMap<String,Object> map = new HashMap<>();
        String info = actionService.delAction(action);
        map.put("info",info);
        return map;
    }
}
