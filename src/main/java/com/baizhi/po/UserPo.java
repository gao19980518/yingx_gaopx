package com.baizhi.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPo {
    private String id;
    private String username;
    private String headImg;
    private String phone;
    private String sign;
    private String status;
    private String wechat;
    private Date createDate;
}
