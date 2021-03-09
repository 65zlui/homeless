package com.swpu.dao;

import com.swpu.pojo.ParInfo;
import com.swpu.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ParInfoDao {

    //查询所有出队人员信息
    @Select("select * from parInfo")
    List<ParInfo> selectParInfo();
    //根据救援编号查询出队信息
    @Select("select * from parInfo where rId=#{rId}")
    List<ParInfo> findByRId(ParInfo parInfo);
    //根据志愿者编号查询出队信息
    @Select("select * from parInfo where vId=#{vId}")
    List<ParInfo> findByVId(ParInfo parInfo);
}
