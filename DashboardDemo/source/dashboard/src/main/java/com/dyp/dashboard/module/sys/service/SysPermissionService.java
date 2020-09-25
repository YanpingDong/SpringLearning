package com.dyp.dashboard.module.sys.service;

import com.dyp.dashboard.module.sys.entity.SysPermission;

import java.util.List;

public interface SysPermissionService {
    List<SysPermission> getAllPermission();
    void createSysPermission(SysPermission record);
    SysPermission selectById(int id);
    int deleteById(int id);
    int updateById(SysPermission record);
}
