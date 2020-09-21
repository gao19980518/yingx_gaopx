package com.baizhi.controller;

import com.baizhi.entity.Category;
import com.baizhi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ResponseBody
    //分页展示一级类别
    @RequestMapping("/queryPage")
    public HashMap<Object, Object> queryPage(Integer page, Integer rows) {
        HashMap<Object, Object> map = categoryService.queryPage(page, rows);
        return map;
    }

    @ResponseBody
    @RequestMapping("/queryPageSon")
    //分页展示二级类别
    public HashMap<Object, Object> queryPageSon(String pid, Integer page, Integer rows) {
        System.out.println("pid=======" + pid);
        HashMap<Object, Object> map = categoryService.queryPageSon(pid, page, rows);
        return map;
    }


    @RequestMapping("edit")
    @ResponseBody
    public HashMap<String, Object> edit(Category category, String oper) {
        HashMap<String, Object> map = new HashMap<>();
        if ("add".equals(oper)) {
            System.out.println("添加的category======" + category);
            categoryService.add(category);
        }
        if ("edit".equals(oper)) {
            categoryService.edit(category);
        }
        if ("del".equals(oper)) {
            System.out.println("删除的category" + category);
            map = categoryService.del(category);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println("key" + entry.getKey() + "---value" + entry.getValue());
            }
        }
        return map;
    }

}
