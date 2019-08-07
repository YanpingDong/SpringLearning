package com.example.springshiro.service;


import com.example.springshiro.entity.SysPermission;
import com.example.springshiro.entity.SysUserRole;
import com.example.springshiro.entity.User;
import com.example.springshiro.model.Pageable;


import java.util.List;

/**
 * @Author: xu.dm
 * @Date: 2018/8/11 16:45
 * @Description:
 */
public interface UserService {

    String getDbVersion();

    User findByUserName(String userName);

    User findUserById(Integer userId);

    User save(User user);

    //新增用户判断是否有重名的
    boolean checkUserExists(String userName);
    //修改用户判断是否有重名的，不包括即将被修改的原名
    boolean checkUserExists2(String oldUserName, String newUserName);

    List<SysUserRole> findUserRoleByUserName(String userName);

    List<SysUserRole> findAllUserRoleByUserId(Integer userId);

    List<SysPermission> findUserRolePermissionByUserName(String userName);

    List<User> findAllByUserNameContains(String userName, Pageable pageable);

    void deleteAllUserByUserIdList(List<Integer> userIdList);

    void deleteAllUserRoleByUserIdList(List<Integer> userIdList);

    void deleteAllUserRoleByUserId(Integer userId);

    void grantUserRole(Integer userId, List<Integer> roleIdList);
    
}
