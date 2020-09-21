package com.baizhi.service.impl;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    HttpSession session;

    @Override
    public HashMap<String, Object> queryByUsername(Admin admin, String image) {
        //先判断验证码
        String code = (String) session.getAttribute("code");
        HashMap<String, Object> map = new HashMap<>();
        if (code.equals(image)) {
            //判断用户名是否输入正确
            Admin admin1 = adminDao.queryByUsername(admin.getUsername());
            if (admin1 != null) {
                //判断密码是否正确
                if (admin1.getPassword().equals(admin.getPassword())) {
                    map.put("status", true);
                    //做登录标记
                    session.setAttribute("admin", admin1);
                } else {
                    map.put("status", false);
                    map.put("message", "密码输入错误");
                }
            } else {
                map.put("status", false);
                map.put("message", "用户名输入错误");
            }
        } else {
            map.put("status", false);
            map.put("message", "验证码输入错误");
        }
        return map;
    }
}
