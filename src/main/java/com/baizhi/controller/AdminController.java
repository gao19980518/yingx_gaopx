package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import java.util.HashMap;

@Controller
@Scope("prototype")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @ResponseBody
    //登录
    @RequestMapping("/login")
    public HashMap<String, Object> login(Admin admin, String image) {
        System.out.println(admin + "-----" + image);
        HashMap<String, Object> map = adminService.queryByUsername(admin, image);
        return map;
    }

    //退出
    @RequestMapping("/safeOut")
    public String safeOut(SessionStatus sessionStatus) {
        //获取session标记
        sessionStatus.setComplete();
        return "redirect:/login/login.jsp";
    }

}
