package com.baizhi.service;

import com.baizhi.entity.Category;
import com.baizhi.vo.CategoryVo;

import java.util.HashMap;
import java.util.List;

public interface CategoryService {
    public HashMap<Object, Object> queryPage(Integer page, Integer rows);

    public HashMap<Object, Object> queryPageSon(String pid, Integer page, Integer rows);

    public void add(Category category);

    public void edit(Category category);

    public HashMap<String, Object> del(Category category);


    public List<CategoryVo> queryAllCategory();

}
