package com.baizhi.service.impl;

import com.baizhi.dao.LogDao;
import com.baizhi.entity.Log;
import com.baizhi.service.LogService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class LogServiceImpl implements LogService {
    @Autowired
    private LogDao logDao;

    @Override
    public HashMap<String, Object> queryPageLog(Integer page, Integer rows) {
        //将页码  总条数 每页的页数存起来吗  展示到jsp
        HashMap<String, Object> map = new HashMap<>();

        Log log = new Log();
        RowBounds row = new RowBounds((page - 1) * rows, rows);
        List<Log> logs = logDao.selectByRowBounds(log, row);
        int i = logDao.selectCount(log);

        map.put("total", (i % rows == 0) ? (i / rows) : (i / rows + 1));
        map.put("records", i);
        map.put("rows", logs);
        map.put("page", page);
        return map;
    }

    @Override
    public void add(Log log) {
        System.out.println("日志的入库：" + log);
        logDao.insertSelective(log);
    }
}
