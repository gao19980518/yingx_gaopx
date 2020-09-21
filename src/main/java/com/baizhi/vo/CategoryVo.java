package com.baizhi.vo;

import com.baizhi.po.CategoryPo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVo {
    private String id;
    private String cateName;
    private String levels;
    private String parentId;
    private List<CategoryPo> categoryList;
}
