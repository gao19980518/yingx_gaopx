package com.baizhi.service.impl;

import com.baizhi.dao.NewUserDao;
import com.baizhi.entity.Relation;
import com.baizhi.service.NewUserService;
import com.baizhi.vo.CityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class NewUserServiceImpl implements NewUserService {
    @Autowired
    private NewUserDao newUserDao;

    @Override
    public List<Integer> queryBoy() {
        List<Relation> boyList = newUserDao.queryBoy();
        System.out.println("boyList" + boyList);
        List<Integer> boysnum = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            //做标记
            int a = 0;
            for (Relation r : boyList) {
                if (r.getMonth().equals(i + "月")) {
                    boysnum.add(r.getNumber());
                    a = 1;
                    break;
                }
            }
            if (a == 0) {
                //当前月份没有数量（没有当前月份）
                boysnum.add(0);
            }

        }
        System.out.println("boysnum" + boysnum);
        return boysnum;
    }

    @Override
    public List<Integer> queryGirl() {
        List<Relation> girlList = newUserDao.queryGirl();
        System.out.println("girlList" + girlList);
        List<Integer> girlsnum = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            int a = 0;
            for (Relation r : girlList) {
                if (r.getMonth().equals(i + "月")) {
                    girlsnum.add(r.getNumber());
                    a = 1;
                    break;
                }
            }
            if (a == 0) {
                girlsnum.add(0);
            }
        }
        System.out.println("girlsnum" + girlsnum);
        return girlsnum;
    }

    @Override
    public List<CityVo> queryBoyCity() {
        List<CityVo> boyCity = newUserDao.queryBoyCity();
        System.out.println("boyCity" + boyCity);
        return boyCity;
    }

    @Override
    public List<CityVo> queryGirlCity() {
        List<CityVo> girlCity = newUserDao.queryGirlCity();
        System.out.println("girlCity" + girlCity);
        return girlCity;
    }
}
