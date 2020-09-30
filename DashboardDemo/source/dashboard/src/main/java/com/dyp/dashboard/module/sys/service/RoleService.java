package com.dyp.dashboard.module.sys.service;


import com.dyp.dashboard.module.sys.entity.SysRole;
import com.dyp.dashboard.module.sys.entity.SysRolePermission;
import com.dyp.dashboard.module.sys.model.Pageable;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<SysRole> findAll();
    List<SysRole> findAll(Pageable pageable);

    List<SysRole> findAllByRoleContains(String role, Pageable pageable);

    Optional<SysRole> findById(Integer roleId);

    SysRole save(SysRole sysRole);

    boolean checkRoleExists(String role);

    boolean checkRoleExists(String oldRole, String newRole);

    boolean deleteAllByRoleIdIn(List<Integer> roleIdList);

    List<SysRolePermission> findSysRolePermissionByRoleId(Integer roleId);

    void grantAuthorization(Integer roleId, List<Integer> permissionList);

    void clearAuthorization(Integer roleId);
}
