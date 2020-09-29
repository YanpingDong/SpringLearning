package com.dyp.dashboard.module.sys.repository;

import com.dyp.dashboard.module.sys.entity.SysPermission;
import com.dyp.dashboard.module.sys.entity.SysRole;
import com.dyp.dashboard.module.sys.entity.User;

import java.util.List;

public interface UserMapper {
    List<User> selectAll();
    int deleteById(int id);
    User selectById(int id);
    int updateById(User record);
    int insert(User record);
    User selectByUerName(String name);
    List<SysPermission>selectPermissionByUserId(int id);
}
