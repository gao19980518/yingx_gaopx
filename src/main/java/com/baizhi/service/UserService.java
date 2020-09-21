package com.baizhi.service;

import com.baizhi.entity.User;
import com.baizhi.vo.UserVo;

import java.util.HashMap;
import java.util.List;

public interface UserService {
    public String add(User user);

    public void edit(User user);

    public List<User> queryAll();

    public HashMap<String, Object> queryPageUser(Integer page, Integer rows);

    public void del(User user);

    //接口查询用户
    public UserVo queryByUserDetail(String userId);


}
