package com.baizhi.dao;

import com.baizhi.entity.Admin;
import tk.mybatis.mapper.common.Mapper;

public interface AdminDao extends Mapper<Admin> {
    /*
    后台管理员登录方法
    * */
    public Admin queryByUsername(String username);
}
