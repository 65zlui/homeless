package com.swpu.dao;

import com.swpu.pojo.Action;
import com.swpu.pojo.ParInfo;
import com.swpu.pojo.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ActionDao {

    //填写救援行动
    @Insert("insert into Action (rId,misName,misDomicile,misAddr,misInfo,misImg,conName,relation,conTel,conMail,conAddr,QQorWeChat,misSex,birthDate,misDate,misHeight) values " +
            "(#{rId},#{misName},#{misDomicile},#{misAddr},#{misInfo},#{misImg},#{conName},#{relation},#{conTel},#{conMail},#{conAddr},#{QQorWeChat},#{misSex},#{birthDate},#{misDate},#{misHeight})")
    int fill(Action action);
    //查询所有救援行动
    @Select("select * from action")
    List<Action> findAll();
    //根据rId查询
    @Select("select * from action where rId=#{rId}")
    List<Action> findByrId(Action action);
    //根据失踪人姓名查询
    @Select("select * from action where misName=#{misName}")
    List<Action> findByMisName(Action action);
    //根据联系人姓名查询
    @Select("select * from action where conName=#{conName}")
    List<Action> findByConName(Action action);
    //根据联系人电话查询
    @Select("select * from action where conTel=#{conTel}")
    List<Action> findByConTel(Action action);

    //根据rId查询对应所有信息，填充
    @Select("select * from action where rId=#{rId}")
    Action selectByrId(Action action);
    //修改救援行动
    @Update("update action set misName=#{misName},misDomicile=#{misDomicile},misAddr=#{misAddr},misInfo=#{misInfo},misImg=#{misImg},conName=#{conName},relation=#{relation},conTel=#{conTel}" +
            ",conMail=#{conMail},conAddr=#{conAddr},QQorWeChat=#{QQorWeChat},misSex=#{misSex},birthDate=#{birthDate},misHeight=#{misHeight} where rId=#{rId}")
    int updateAction(Action action);
    //删除救援行动
    @Delete("delete from action where rId=#{rId}")
    int delAction(Action action);
}
