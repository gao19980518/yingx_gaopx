package com.baizhi.service.impl;

import com.baizhi.annotation.AddLog;
import com.baizhi.dao.CategoryDao;
import com.baizhi.entity.Category;
import com.baizhi.po.CategoryPo;
import com.baizhi.service.CategoryService;
import com.baizhi.vo.CategoryVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    //查询一级类别
    @Override
    @AddLog(value = "查询一级类别数据")
    public HashMap<Object, Object> queryPage(Integer page, Integer rows) {
        HashMap<Object, Object> map = new HashMap<>();

        Category category = new Category();
        RowBounds row = new RowBounds((page - 1) * rows, rows);
        category.setLevels("1");
        List<Category> categories = categoryDao.selectByRowBounds(category, row);
        int i = categoryDao.selectCount(category);
        System.out.println("categorys==" + categories);

        map.put("total", (i % rows == 0) ? (i / rows) : (i / rows + 1));//总页数
        map.put("records", i);//总条数
        map.put("rows", categories);//当前页的条数
        map.put("page", page);//当前页码
        return map;
    }

    //查询二级类别
    @Override
    @AddLog(value = "查询二级类别数据")
    public HashMap<Object, Object> queryPageSon(String pid, Integer page, Integer rows) {
        HashMap<Object, Object> map = new HashMap<>();

        Category category = new Category();
        RowBounds row = new RowBounds((page - 1) * rows, rows);
        category.setLevels("2");
        category.setParentId(pid);
        List<Category> categories = categoryDao.selectByRowBounds(category, row);
        int i = categoryDao.selectCount(category);
        System.out.println("categorys==" + categories);

        map.put("total", (i % rows == 0) ? (i / rows) : (i / rows + 1));
        map.put("records", i);
        map.put("rows", categories);
        map.put("page", page);
        return map;
    }

    @Override
    @AddLog(value = "添加类别数据")
    public void add(Category category) {
        System.out.println("service添加的category===" + category);
        if (category.getParentId() == null) {
            System.out.println("service添加一级类别" + category);
            //添加一级类别
            category.setId(UUID.randomUUID().toString());
            category.setLevels("1");
        } else {
            System.out.println("service添加二级类别" + category);
            category.setId(UUID.randomUUID().toString());
            category.setLevels("2");
        }
        categoryDao.insertSelective(category);
    }

    @Override
    @AddLog(value = "修改类别数据")
    public void edit(Category category) {
        categoryDao.updateByPrimaryKeySelective(category);
    }

    @Override
    @AddLog(value = "删除类别数据")
    public HashMap<String, Object> del(Category category) {
        HashMap<String, Object> map = new HashMap<>();
        //判断当前要删除的是一级类别还是二级类别
        if (category.getParentId() == null) {
            System.out.println("service删除一级类别" + category);
            //当前是个一级类别
            Example e = new Example(Category.class);
            e.createCriteria().andEqualTo("parentId", category.getId());
            int i = categoryDao.selectCountByExample(e);
            System.out.println("一级类别下二级类别的数量i==" + i);
            if (i == 0) {
                System.out.println("service可以删除一级类别" + category);
                //没有管理二级类别
                categoryDao.deleteByPrimaryKey(category);
                map.put("message", "删除成功");
            } else {
                //不能删
                map.put("message", "当前一级类别下管理着二级类别，不能删");
            }
        } else {
            System.out.println("删除二级类别==" + category);
            //当前类别时二级类别
            //判断二级类别下是否管理着二级类别
            //如果有  不能删
            //如果没有   删
            categoryDao.deleteByPrimaryKey(category);
            map.put("message", "删除成功");
        }
        return map;
    }


    //测试接口类别
    @Override
    public List<CategoryVo> queryAllCategory() {
        System.out.println("前台的查询类别");
        List<CategoryVo> list = new ArrayList<>();
        try {
            //查询连接的数据
            List<CategoryPo> categoryPos = categoryDao.queryAllCategory();
            //将po对象的值付给vo对象
            for (CategoryPo p : categoryPos) {
                CategoryVo v = new CategoryVo(p.getId(), p.getCateName(), p.getLevels(), p.getParentId(), p.getCategoryList());
                list.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


}
