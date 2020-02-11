package com.example.springshiro.repository;

import com.example.springshiro.entity.SysLog;
import com.example.springshiro.entity.SysRole;
import com.example.springshiro.entity.SysRolePermission;
import com.example.springshiro.model.Pageable;

import org.springframework.stereotype.Repository;

import java.util.*;


/**
 * @Author: xu.dm
 * @Date: 2018/9/5 21:57
 * @Description:
 */
@Repository
public class RoleRepository {

    public SysRole save(SysRole role)
    {
        SysRole sysRole1 = new SysRole();
        sysRole1.setRoleId(1);
        sysRole1.setAvailable(true);
        sysRole1.setDescription("超级管理员");
        sysRole1.setRole("admin");
        return sysRole1;
    }

    public Optional<SysRole> findById(int id)
    {
        SysRole sysRole1 = new SysRole();
        sysRole1.setRoleId(1);
        sysRole1.setAvailable(true);
        sysRole1.setDescription("超级管理员");
        sysRole1.setRole("admin");

        SysRole sysRole2 = new SysRole();
        sysRole2.setRoleId(2);
        sysRole2.setAvailable(true);
        sysRole2.setDescription("高级用户");
        sysRole2.setRole("powerUser");

        SysRole sysRole3 = new SysRole();
        sysRole3.setRoleId(3);
        sysRole3.setAvailable(true);
        sysRole3.setDescription("普通用户");
        sysRole3.setRole("user");

        SysRole sysRole4 = new SysRole();
        sysRole4.setRoleId(4);
        sysRole4.setAvailable(true);
        sysRole4.setDescription("游客");
        sysRole4.setRole("guest");

        Map<Integer, SysRole> sysRoleMap = new HashMap<>();
        sysRoleMap.put(1, sysRole1);
        sysRoleMap.put(2, sysRole2);
        sysRoleMap.put(3, sysRole3);
        sysRoleMap.put(4, sysRole4);

        return Optional.ofNullable(sysRoleMap.get(id));

    }
    public List<SysRole>findAll(Pageable pageable)
    {
        SysRole sysRole1 = new SysRole();
        sysRole1.setRoleId(1);
        sysRole1.setAvailable(true);
        sysRole1.setDescription("超级管理员");
        sysRole1.setRole("admin");

        SysRole sysRole2 = new SysRole();
        sysRole2.setRoleId(2);
        sysRole2.setAvailable(true);
        sysRole2.setDescription("高级用户");
        sysRole2.setRole("powerUser");

        SysRole sysRole3 = new SysRole();
        sysRole3.setRoleId(3);
        sysRole3.setAvailable(true);
        sysRole3.setDescription("普通用户");
        sysRole3.setRole("user");

        SysRole sysRole4 = new SysRole();
        sysRole4.setRoleId(4);
        sysRole4.setAvailable(true);
        sysRole4.setDescription("游客");
        sysRole4.setRole("guest");

        List<SysRole> sysRoles = new ArrayList<>();
        sysRoles.add(sysRole1);
        sysRoles.add(sysRole2);
        sysRoles.add(sysRole3);
        sysRoles.add(sysRole4);
        return sysRoles;
    }

    public List<SysRole> findAllByRoleContains(String role, Pageable pageable)
    {
        SysRole sysRole1 = new SysRole();
        sysRole1.setRoleId(1);
        sysRole1.setAvailable(true);
        sysRole1.setDescription("超级管理员");
        sysRole1.setRole("admin");

        SysRole sysRole2 = new SysRole();
        sysRole2.setRoleId(2);
        sysRole2.setAvailable(true);
        sysRole2.setDescription("高级用户");
        sysRole2.setRole("powerUser");

        SysRole sysRole3 = new SysRole();
        sysRole3.setRoleId(3);
        sysRole3.setAvailable(true);
        sysRole3.setDescription("普通用户");
        sysRole3.setRole("user");

        SysRole sysRole4 = new SysRole();
        sysRole4.setRoleId(4);
        sysRole4.setAvailable(true);
        sysRole4.setDescription("游客");
        sysRole4.setRole("guest");

        List<SysRole> sysRoles = new ArrayList<>();
        sysRoles.add(sysRole1);
        sysRoles.add(sysRole2);
        sysRoles.add(sysRole3);
        sysRoles.add(sysRole4);
        return sysRoles;
    };
    public SysRole findSysRoleByRole(String role)
    {
        if ("admin".equalsIgnoreCase(role)){
            SysRole sysRole1 = new SysRole();
            sysRole1.setRoleId(1);
            sysRole1.setAvailable(true);
            sysRole1.setDescription("超级管理员");
            sysRole1.setRole("admin");
            return sysRole1;
        }
        else
        {
            SysRole sysRole3 = new SysRole();
            sysRole3.setRoleId(3);
            sysRole3.setAvailable(true);
            sysRole3.setDescription("普通用户");
            sysRole3.setRole("user");
            return sysRole3;
        }
    };

    public SysRole findSysRoleExists2(String oldRole, String newRole)
    {
        SysRole sysRole4 = new SysRole();
        sysRole4.setRoleId(4);
        sysRole4.setAvailable(true);
        sysRole4.setDescription("游客");
        sysRole4.setRole("guest");

        return sysRole4;
    };


   public void deleteAllByRoleIdList(List<Integer> roleIdList)
    {
        //delete data from db
    };


   public List<SysRolePermission> findSysRolePermissionByRoleId(Integer roleId)
    {
        SysRolePermission sysRolePermission1 = new SysRolePermission();
        sysRolePermission1.setId(1);
        sysRolePermission1.setPermissionId(1);
        sysRolePermission1.setRoleId(1);

        SysRolePermission sysRolePermission2 = new SysRolePermission();
        sysRolePermission2.setId(2);
        sysRolePermission2.setPermissionId(2);
        sysRolePermission2.setRoleId(1);

        SysRolePermission sysRolePermission3 = new SysRolePermission();
        sysRolePermission3.setId(3);
        sysRolePermission3.setPermissionId(3);
        sysRolePermission3.setRoleId(1);

        SysRolePermission sysRolePermission4 = new SysRolePermission();
        sysRolePermission4.setId(4);
        sysRolePermission4.setPermissionId(4);
        sysRolePermission4.setRoleId(1);

        SysRolePermission sysRolePermission5 = new SysRolePermission();
        sysRolePermission5.setId(5);
        sysRolePermission5.setPermissionId(5);
        sysRolePermission5.setRoleId(1);

        SysRolePermission sysRolePermission6 = new SysRolePermission();
        sysRolePermission6.setId(6);
        sysRolePermission6.setPermissionId(6);
        sysRolePermission6.setRoleId(1);

        SysRolePermission sysRolePermission7 = new SysRolePermission();
        sysRolePermission7.setId(7);
        sysRolePermission7.setPermissionId(7);
        sysRolePermission7.setRoleId(1);

        SysRolePermission sysRolePermission8 = new SysRolePermission();
        sysRolePermission8.setId(8);
        sysRolePermission8.setPermissionId(8);
        sysRolePermission8.setRoleId(1);

        SysRolePermission sysRolePermission9 = new SysRolePermission();
        sysRolePermission9.setId(9);
        sysRolePermission9.setPermissionId(9);
        sysRolePermission9.setRoleId(1);

        SysRolePermission sysRolePermission10 = new SysRolePermission();
        sysRolePermission10.setId(10);
        sysRolePermission10.setPermissionId(10);
        sysRolePermission10.setRoleId(1);

        SysRolePermission sysRolePermission16 = new SysRolePermission();
        sysRolePermission16.setId(16);
        sysRolePermission16.setPermissionId(11);
        sysRolePermission16.setRoleId(1);
        //==============================another user==================================
        SysRolePermission sysRolePermission11 = new SysRolePermission();
        sysRolePermission11.setId(11);
        sysRolePermission11.setPermissionId(1);
        sysRolePermission11.setRoleId(2);

        SysRolePermission sysRolePermission12 = new SysRolePermission();
        sysRolePermission12.setId(12);
        sysRolePermission12.setPermissionId(2);
        sysRolePermission12.setRoleId(2);

        SysRolePermission sysRolePermission13 = new SysRolePermission();
        sysRolePermission13.setId(13);
        sysRolePermission13.setPermissionId(7);
        sysRolePermission13.setRoleId(2);

        SysRolePermission sysRolePermission14 = new SysRolePermission();
        sysRolePermission14.setId(14);
        sysRolePermission14.setPermissionId(8);
        sysRolePermission14.setRoleId(2);

        SysRolePermission sysRolePermission15 = new SysRolePermission();
        sysRolePermission15.setId(15);
        sysRolePermission15.setPermissionId(9);
        sysRolePermission15.setRoleId(2);

        List<SysRolePermission> sysRolePermissions = new ArrayList<>();

        if(1 == roleId)
        {
            sysRolePermissions.add(sysRolePermission1);
            sysRolePermissions.add(sysRolePermission2);
            sysRolePermissions.add(sysRolePermission3);
            sysRolePermissions.add(sysRolePermission4);
            sysRolePermissions.add(sysRolePermission5);
            sysRolePermissions.add(sysRolePermission6);
            sysRolePermissions.add(sysRolePermission7);
            sysRolePermissions.add(sysRolePermission8);
            sysRolePermissions.add(sysRolePermission9);
            sysRolePermissions.add(sysRolePermission10);
            sysRolePermissions.add(sysRolePermission16);
            return sysRolePermissions;
        }
        else
        {
            sysRolePermissions.add(sysRolePermission11);
            sysRolePermissions.add(sysRolePermission12);
            sysRolePermissions.add(sysRolePermission13);
            sysRolePermissions.add(sysRolePermission14);
            sysRolePermissions.add(sysRolePermission15);
            sysRolePermissions.add(sysRolePermission16);
            return sysRolePermissions;
        }
    };

    //根据roleid删除角色权限关联表里所有角色权限
//    @Modifying
//    @Query(value = "delete from sysrolepermission where roleId=?1",nativeQuery = true)
    public  void deleteRolePermission(Integer roleId)
    {
        //delete role permmission from db
    };

    //插入角色和权限
//    @Modifying
//    @Query(value="insert into sysrolepermission(roleId,permissionId) VALUES(?1,?2) ",nativeQuery = true)
    public void insertRolePermission(Integer roleId, Integer permissionId)
    {
        //insert role permission to db
    };
}
