package com.dyp.dashboard.module.sys.repository;

import com.dyp.dashboard.module.sys.entity.SysUserRole;

public interface SysUserRoleMapper {
    int insert(SysUserRole record);
    int deleteByUserId(Integer id);
}
