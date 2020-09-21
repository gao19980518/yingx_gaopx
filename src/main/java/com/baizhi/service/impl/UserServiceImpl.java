package com.baizhi.service.impl;

import com.baizhi.annotation.AddLog;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import com.baizhi.po.UserPo;
import com.baizhi.service.UserService;
import com.baizhi.vo.UserVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    @AddLog(value = "添加用户数据")
    public String add(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setStatus("正常");
        user.setCreateDate(new Date());
        userDao.insertSelective(user);

        return user.getId();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    @AddLog(value = "修改用户状态")
    public void edit(User user) {

        userDao.updateByPrimaryKeySelective(user);
    }


    @Override
    @AddLog(value = "查询用户数据")
    public HashMap<String, Object> queryPageUser(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();

        User user = new User();
        RowBounds row = new RowBounds((page - 1) * rows, rows);
        List<User> users = userDao.selectByRowBounds(user, row);
        int i = userDao.selectCount(user);

        map.put("total", (i % rows == 0) ? (i / rows) : (i / rows + 1));
        map.put("records", i);
        map.put("rows", users);
        map.put("page", page);
        return map;
    }

    @Override
    @AddLog(value = "删除用户数据")
    public void del(User user) {
        userDao.deleteByPrimaryKey(user);
    }

    //用来测试用户的导出Excel
    @Override
    public List<User> queryAll() {
        List<User> list = userDao.selectAll();
        System.out.println("用来测试用户的导出" + list);
        return list;
    }

    //前台查询用户数据
    @Override
    public UserVo queryByUserDetail(String userId) {
        //查询连接的数据
        UserPo userPo = userDao.queryByUserDetail(userId);
        System.out.println("service的userPo=====" + userPo);
        UserVo userVo = null;
        try {
            //转化日期格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //将po对象的值赋值给vo对象
            String time = sdf.format(userPo.getCreateDate());
            userVo = new UserVo(userPo.getId(), userPo.getUsername(), userPo.getHeadImg(), userPo.getPhone(), "111111", userPo.getSign(), "22222", userPo.getStatus(), userPo.getWechat(), time, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userVo;
    }

}
