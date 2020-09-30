package com.dyp.dashboard.module.sys.repository;

import com.dyp.dashboard.module.sys.entity.SysRole;

import java.util.List;

public interface SysRoleMapper {
    List<SysRole> selectAll();
    int deleteById(int id);
    SysRole selectById(int id);
    int updateById(SysRole record);
    int insert(SysRole record);
}
