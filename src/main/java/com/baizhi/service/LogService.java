package com.baizhi.service;

import com.baizhi.entity.Log;

import java.util.HashMap;

public interface LogService {
    public HashMap<String, Object> queryPageLog(Integer page, Integer rows);

    public void add(Log log);
}
