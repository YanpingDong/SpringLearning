package com.dyp.dashboard.module.sys.service.impl;

import com.dyp.dashboard.module.sys.entity.SysRole;
import com.dyp.dashboard.module.sys.entity.SysRolePermission;
import com.dyp.dashboard.module.sys.model.Pageable;
import com.dyp.dashboard.module.sys.repository.RoleRepository;
import com.dyp.dashboard.module.sys.repository.SysRoleMapper;
import com.dyp.dashboard.module.sys.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @Author: xu.dm
 * @Date: 2018/9/5 22:16
 * @Description:
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    RoleRepository roleRepository;

    @Autowired
    SysRoleMapper sysRoleDao;

    @Override
    public List<SysRole> findAll() {
        return sysRoleDao.selectAll();
    }

    @Override
    public List<SysRole> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public Optional<SysRole> findById(Integer roleId) {
        return roleRepository.findById(roleId);
    }

    @Override
    public List<SysRole> findAllByRoleContains(String role, Pageable pageable) {
        return roleRepository.findAllByRoleContains(role,pageable);
    }

    @Override
    public SysRole save(SysRole sysRole) {
        return roleRepository.save(sysRole);
    }

    @Override
    public boolean checkRoleExists(String role) {
        SysRole sysRole = roleRepository.findSysRoleByRole(role);
        if(sysRole!=null)
            return true;
        else
            return false;
    }

    @Override
    public boolean checkRoleExists(String oldRole, String newRole) {
        SysRole sysRole = roleRepository.findSysRoleExists2(oldRole,newRole);
        if(sysRole!=null)
            return true;
        else
            return false;
    }

    //删除角色权限和角色
    @Override
    public boolean deleteAllByRoleIdIn(List<Integer> roleIdList) {
        try {
            for(Integer roleId:roleIdList)
            {
                roleRepository.deleteRolePermission(roleId);
            }
            roleRepository.deleteAllByRoleIdList(roleIdList);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<SysRolePermission> findSysRolePermissionByRoleId(Integer roleId) {
        return roleRepository.findSysRolePermissionByRoleId(roleId);
    }

    //授权前先清除原角色权限，然后重新新增授权
    @Override
    public void grantAuthorization(Integer roleId, List<Integer> permissionList) {
        roleRepository.deleteRolePermission(roleId);
        for(Integer permissionId:permissionList)
        {
            roleRepository.insertRolePermission(roleId,permissionId);
        }
    }

    @Override
    public void clearAuthorization(Integer roleId) {
        roleRepository.deleteRolePermission(roleId);
    }
}
