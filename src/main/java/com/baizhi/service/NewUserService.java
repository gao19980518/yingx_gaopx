package com.baizhi.service;

import com.baizhi.vo.CityVo;

import java.util.List;

public interface NewUserService {
    public List<Integer> queryBoy();

    public List<Integer> queryGirl();


    public List<CityVo> queryBoyCity();

    public List<CityVo> queryGirlCity();
}
