package com.swpu.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.swpu.dao.ParInfoDao;
import com.swpu.pojo.ParInfo;
import com.swpu.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ParInfoServiceImpl implements ParInfoService {
    @Autowired(required = false)
    ParInfoDao parInfoDao;
    //创建操作redis库的操作对象
    @Autowired
    RedisTemplate<String,Object> redisTemplate;


    @Override
    public HashMap<String, Object> select(ParInfo parInfo) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        //1设置分页参数
        PageHelper.startPage(parInfo.getPage(),parInfo.getRow());
        List<ParInfo> list = new ArrayList<>();
        if(parInfo.getConValue()==null){
            list = parInfoDao.selectParInfo();
            //判断redis缓存中是否有数据
            String key = "wen";
            Object obj = redisTemplate.opsForValue().get(key);
        }else{
            ////2查询数据库表数据  根据用户选择的条件判断用户
            if(parInfo.getCondition().equals("救援编号")){
                //设置用户输入的查询条件
                parInfo.setrId(Integer.parseInt(parInfo.getConValue()));
                list = parInfoDao.findByRId(parInfo);
            }else if(parInfo.getCondition().equals("志愿者编号")){
                //设置用户输入的查询条件
                parInfo.setvId(Integer.parseInt(parInfo.getConValue()));
                list = parInfoDao.findByVId(parInfo);
            }else{
                list = parInfoDao.selectParInfo();
            }
        }

        //3查询的数据转换成分页对象
        PageInfo<ParInfo> page = new PageInfo<>(list);

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

        map.put("row",parInfo);
        return map;
    }
}
