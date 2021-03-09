package com.swpu.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.swpu.dao.ActionDao;
import com.swpu.pojo.Action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service

public class ActionServiceImpl implements ActionService {

    @Autowired(required = false)
    ActionDao actionDao;
    //创建操作redis库的操作对象
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Override
    public String fill(Action action) {
        System.out.println(action);
        if(action.getmProvince()!=null){//填写救援行动保存
            String misAddr = action.getmProvince()+action.getmCity()+action.getmCounty()+action.getdMisAddr();
            String misDomicile = action.getdProvince()+action.getdCity()+action.getdCounty()+action.getdDomicile();
            action.setMisAddr(misAddr);
            action.setMisDomicile(misDomicile);
            int n = actionDao.fill(action);
            if(n>0){
                return "保存成功";
            }else {
                return "保存失败";
            }
        }else{//修改救援行动保存
            int id = action.getrId();
            String misAddr = action.getdMisAddr();
            String misDomicile = action.getdDomicile();
            action.setMisAddr(misAddr);
            action.setMisDomicile(misDomicile);
            int n = actionDao.updateAction(action);
            if(n>0){
                return "保存成功";
            }else {
                return "保存失败";
            }
        }

    }

    @Override
    public HashMap<String, Object> find(Action action) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        //1设置分页参数
        PageHelper.startPage(action.getPage(),action.getRow());
        List<Action> list = new ArrayList<>();

        if(action.getConValue()==null){
            list = actionDao.findAll();
            //判断redis缓存中是否有数据
            String key = "wen";
            Object obj = redisTemplate.opsForValue().get(key);
        }else{
            ////2查询数据库表数据  根据用户选择的条件判断用户
            if(action.getCondition().equals("救援编号")){
                //设置用户输入的查询条件
                action.setrId(Integer.parseInt(action.getConValue()));
                list = actionDao.findByrId(action);
            }else if(action.getCondition().equals("失踪人姓名")){
                //设置用户输入的查询条件
                action.setMisName(action.getConValue());
                list = actionDao.findByMisName(action);
            }else if(action.getCondition().equals("联系人姓名")){
                action.setConName(action.getConValue());
                list = actionDao.findByConName(action);
            }else if(action.getCondition().equals("联系人电话")){
                action.setConTel(action.getConValue());
                list = actionDao.findByConTel(action);
            } else{
                list = actionDao.findAll();
            }
        }

        //3查询的数据转换成分页对象
        PageInfo<Action> page = new PageInfo<>(list);

        //获取分页的当前页的集合
        map.put("list",page.getList());
        //总条数
        map.put("total",page.getTotal());
        //总页数
        map.put("totalPage",page.getPages());
        //上一页
        if(page.getPrePage()==0){
            map.put("pre",1);
        }else{
            map.put("pre",page.getPrePage());
        }
        //下一页
        if(page.getNextPage()==0){//最后一页时固定最后一页
            map.put("next",page.getPages());
        }else{//不是最后一页，获取下一页
            map.put("next",page.getNextPage());
        }
        //当前页
        map.put("currentPage",page.getPageNum());
        //当前页数据条数
        map.put("curPageSize",page.getSize());

        map.put("row",action);
        return map;
    }

    //打开修改页面时根据rId查出对应信息
    @Override
    public Action selectByrId(Action action) {
        return actionDao.selectByrId(action);
    }

    @Override
    public String delAction(Action action) {
        int n = actionDao.delAction(action);
        if(n>0){
            return "删除成功";
        }else {
            return "删除失败";
        }
    }
}
