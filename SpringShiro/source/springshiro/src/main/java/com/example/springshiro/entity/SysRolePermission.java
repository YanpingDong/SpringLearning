package com.example.springshiro.entity;

/**
 * @Author: xu.dm
 * @Date: 2018/9/24 15:40
 * @Description:角色权限对照表
 */

public class SysRolePermission {

    private Integer id; // 编号
    private Integer roleId; //
    private Integer permissionId;//

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }
}
