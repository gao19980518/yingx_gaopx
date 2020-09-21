package com.baizhi.dao;

import com.baizhi.entity.NewUser;
import com.baizhi.entity.Relation;
import com.baizhi.vo.CityVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface NewUserDao extends Mapper<NewUser> {
    public List<Relation> queryBoy();

    public List<Relation> queryGirl();


    public List<CityVo> queryBoyCity();

    public List<CityVo> queryGirlCity();
}
