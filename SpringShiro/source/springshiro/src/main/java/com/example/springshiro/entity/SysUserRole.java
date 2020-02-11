package com.example.springshiro.entity;


/**
 * @Author: xu.dm
 * @Date: 2018/9/24 15:47
 * @Description:用户和角色关联表
 */


public class SysUserRole {

    private Integer id; // 编号
    private Integer userId;
    private Integer roleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
