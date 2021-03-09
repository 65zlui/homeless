package com.swpu.service;

import com.swpu.dao.InfoDao;
import com.swpu.pojo.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class InfoServiceImpl implements InfoService{
    @Autowired(required = false)
    InfoDao infoDao;


    @Override
    public HashMap<String, Object> bing(Info info) {
        HashMap<String, Object> map = new HashMap<>();
        List<Info> list = infoDao.select(info);

        //构建饼图需要的数据类型
        List<HashMap<String, Object>> mapList = new ArrayList<>();
        for(Info i:list){//遍历查询的数据集合
            HashMap<String,Object> m = new HashMap<>();
            //判断用户查询的类型
            if(info.getCon().equals("0")) {
                m.put("value", i.getConfirmCount());
            }else if(info.getCon().equals("1")){
                m.put("value", i.getCuredCount());
            }else if(info.getCon().equals("2")){
                m.put("value", i.getDeadCount());
            }else {
                m.put("value", i.getConfirmCount());
            }
            m.put("name",i.getProvinceName());
            mapList.add(m);
        }

        map.put("info",mapList);
        return map;
    }

    @Override
    public List<Info> selectTime() {
        List<Info> info = infoDao.selectTime();
        return info;
    }

    @Override
    public List<Info> selectPro() {
        List<Info> info = infoDao.selectPro();
        return info;
    }

    @Override
    public HashMap<String, Object> zhexian(Info info) {
        HashMap<String, Object> map = new HashMap<>();
        List<Info> list = infoDao.selectByProvinceName(info);
        //封装数据
//        List<HashMap<String, Object>> mapList = new ArrayList<>();
        List<String> time = new ArrayList<>();
        List<Integer> confirmCount = new ArrayList<>();
        List<Integer> curedCount = new ArrayList<>();
        List<Integer> deadCount = new ArrayList<>();
        for(Info i:list){
            time.add(i.getTime());
            confirmCount.add(i.getConfirmCount());
            curedCount.add(i.getCuredCount());
            deadCount.add(i.getDeadCount());
        }
        map.put("time",time);
        map.put("confirmCount",confirmCount);
        map.put("curedCount",curedCount);
        map.put("deadCount",deadCount);
        return map;
    }
}
