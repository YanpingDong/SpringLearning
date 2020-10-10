package com.dyp.dashboard.module.sys.service;

import com.dyp.dashboard.module.sys.entity.SysPermission;
import com.dyp.dashboard.module.sys.entity.SysUserRole;
import com.dyp.dashboard.module.sys.entity.User;
import com.dyp.dashboard.module.sys.model.Pageable;

import java.util.List;

public interface UserService {

    String getDbVersion();

    int saveUserRoles(List<SysUserRole> sysUserRole);

    User findByUserName(String userName);

    User findUserById(Integer userId);

    User save(User user);

    List<User> selectAll();

    int deleteById(int id);

    //新增用户判断是否有重名的
    boolean checkUserExists(String userName);
    //修改用户判断是否有重名的，不包括即将被修改的原名
    boolean checkUserExists2(String oldUserName, String newUserName);

    List<SysUserRole> findUserRoleByUserName(String userName);

    List<SysUserRole> findAllUserRoleByUserId(Integer userId);

    List<SysPermission> findUserRolePermissionByUserName(String userName);
    List<SysPermission> findUserRolePermissionByUserId(int id);

    List<User> findAllByUserNameContains(String userName, Pageable pageable);

    void deleteAllUserByUserIdList(List<Integer> userIdList);

    void deleteAllUserRoleByUserIdList(List<Integer> userIdList);

    void deleteAllUserRoleByUserId(Integer userId);

    void grantUserRole(Integer userId, List<Integer> roleIdList);
    
}
