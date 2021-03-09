package com.swpu.dao;

import com.swpu.pojo.Info;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InfoDao {
//    @Select("SELECT * FROM info WHERE time=#{time} and areaName is null")
    //根据时间查询数据
    @Select("SELECT * FROM info WHERE time=#{time} AND areaName='' OR time=#{time} AND areaName is NULL")
    List<Info> select(Info info);
    //查询数据库中所有的时间(去重)
    @Select("SELECT DISTINCT(time) from info")
    List<Info> selectTime();
    //查询省份
    @Select("SELECT DISTINCT(provinceName) from info")
    List<Info> selectPro();
    //根据省份名称查询该省所有数据
    @Select("SELECT * FROM info WHERE provinceName=#{provinceName} and areaName is NULL")
    List<Info> selectByProvinceName(Info info);
}
