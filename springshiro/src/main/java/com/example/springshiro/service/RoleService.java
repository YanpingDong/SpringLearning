package com.example.springshiro.service;

import com.example.springshiro.entity.SysRole;
import com.example.springshiro.entity.SysRolePermission;
import com.example.springshiro.model.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @Author: xu.dm
 * @Date: 2018/9/5 22:09
 * @Description:
 */
public interface RoleService {
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
