package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

@Table(name = "yx_admin")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    private String id;
    private String username;
    private String password;
}
