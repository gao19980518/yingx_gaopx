package com.baizhi.service;

import com.baizhi.entity.Admin;

import java.util.HashMap;

public interface AdminService {
    public HashMap<String, Object> queryByUsername(Admin admin, String image);

}
