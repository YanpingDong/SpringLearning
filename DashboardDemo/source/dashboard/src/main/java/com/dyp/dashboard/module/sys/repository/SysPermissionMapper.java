package com.dyp.dashboard.module.sys.repository;

import com.dyp.dashboard.module.sys.entity.SysPermission;

import java.util.List;

public interface SysPermissionMapper {
    int insert(SysPermission record);
    SysPermission selectById(int id);
    List<SysPermission> selectAll();
}
