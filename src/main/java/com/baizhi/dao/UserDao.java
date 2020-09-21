package com.baizhi.dao;

import com.baizhi.entity.User;
import com.baizhi.po.UserPo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface UserDao extends Mapper<User> {

    public UserPo queryByUserDetail(@Param("userId") String userId);
}
