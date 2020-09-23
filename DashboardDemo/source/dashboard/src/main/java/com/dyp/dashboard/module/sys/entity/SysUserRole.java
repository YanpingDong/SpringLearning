package com.dyp.dashboard.module.sys.entity;


import lombok.Data;

@Data
public class SysUserRole {

    private Integer id; // 编号
    private Integer userId;
    private Integer roleId;

}
