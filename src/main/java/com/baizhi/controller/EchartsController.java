package com.baizhi.controller;

import com.baizhi.service.NewUserService;
import com.baizhi.vo.CityVo;
import com.baizhi.vo.SeriesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("echarts")
public class EchartsController {
    @Autowired
    private NewUserService newUserService;

    @RequestMapping("queryUserNum")
    public HashMap<String, Object> queryUserNum() {
        /*
         * 新用户表     new_user
         * id name phone sex city create_date
         * 查询每个月份用户注册量
         * month(create_date):该函数获取当前日期的月份
         * select concat(month(create_date),'月'),count(id) from new_user where sex='男'
         *       group by month(create_date)
         * select concat(month(create_date),'月'),count(id) from new_user where sex='女'
         *      group by month(create_date)
         * */
        //根据月份 性别 查询数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("month", Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"));
        map.put("boys", newUserService.queryBoy());
        map.put("girls", newUserService.queryGirl());
        return map;
    }

    @RequestMapping("queryUserCity")
    public ArrayList<Object> queryUserCity() {
        /*
         * 新用户表     new_user
         * id name phone sex city create_date
         * 查询各个省份用户注册量
         * select city,count(id) value from new_user where sex='男' group by city
         *
         * select city,count(id) value from new_user where sex='女' group by city
         * */
        ArrayList<Object> list = new ArrayList<>();
        List<CityVo> c = newUserService.queryBoyCity();
        SeriesVo vo = new SeriesVo("男生", c);


        List<CityVo> c1 = newUserService.queryGirlCity();
        SeriesVo vo1 = new SeriesVo("女生", c1);
        list.add(vo);
        list.add(vo1);

        return list;

    }
}
