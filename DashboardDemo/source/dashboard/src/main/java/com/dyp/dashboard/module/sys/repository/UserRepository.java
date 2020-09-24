package com.dyp.dashboard.module.sys.repository;

import com.dyp.dashboard.module.sys.entity.SysPermission;
import com.dyp.dashboard.module.sys.entity.SysUserRole;
import com.dyp.dashboard.module.sys.entity.User;
import com.dyp.dashboard.module.sys.model.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xu.dm
 * @Date: 2018/6/10 19:54
 * @Description:
 */
@Repository
public class UserRepository {

    public User save(User user)
    {

        return user;
    }

    public User findById(Integer userId)
    {
        User user = new User();
        user.setUserId(1);
        user.setUserName("admin");
        user.setName("管理员");
        user.setPassword("123");
        user.setSalt("8d78869f470951332959580424d4bf4f");
        user.setState((byte)1);
        user.setCreateTime(LocalDateTime.now());

        User user1 = new User();
        user1.setUserId(2);
        user1.setUserName("other");
        user1.setName("管理员");
        user1.setPassword("123");
        user1.setSalt("8d78869f470951332959580424d4bf4f");
        user1.setState((byte)1);
        user1.setCreateTime(LocalDateTime.now());

        if(1 == userId)
        {
            return user;
        }
        else
        {
            return user1;
        }
    }
    public User findByUserName(String userName){
        User user = new User();
        user.setUserId(1);
        user.setUserName("admin");
        user.setName("管理员");
        user.setPassword("123");
        user.setSalt("8d78869f470951332959580424d4bf4f");
        user.setState((byte)1);
        user.setCreateTime(LocalDateTime.now());

        User user1 = new User();
        user1.setUserId(2);
        user1.setUserName("other");
        user1.setName("管理员");
        user1.setPassword("123");
        user1.setSalt("8d78869f470951332959580424d4bf4f");
        user1.setState((byte)1);
        user1.setCreateTime(LocalDateTime.now());

        if("admin".equalsIgnoreCase(userName))
        {
            return user;
        }
        else
        {
            return user1;
        }


    }

    public List<User> findAllByUserNameContains(String userName, Pageable pageable)
    {
        User user = new User();
        user.setUserId(1);
        user.setUserName("admin");
        user.setName("管理员");
        user.setPassword("123");
        user.setSalt("8d78869f470951332959580424d4bf4f");
        user.setState((byte)1);
        user.setCreateTime(LocalDateTime.now());

        User user1 = new User();
        user1.setUserId(2);
        user1.setUserName("other");
        user1.setName("管理员");
        user1.setPassword("123");
        user1.setSalt("8d78869f470951332959580424d4bf4f");
        user1.setState((byte)1);
        user1.setCreateTime(LocalDateTime.now());

        User user2 = new User();
        user2.setUserName(userName);
        user2.setCreateTime(LocalDateTime.now());
        user2.setCredentialsSalt("salt");
        user2.setEmail("email");
        user2.setExpiredDate(LocalDate.now());
        user2.setName(userName);
        user2.setTel("1224456890");
        user2.setUserId(2);

        List<User> users = new ArrayList<User>();

        users.add(user);
        users.add(user1);
        users.add(user2);

        return users;
    }

    //排除现有用户的情况下，判断新用户是否存在
    //@Query(value="select * from user where userName<> ?1 and userName=?2",nativeQuery = true)
    public User findUserExist2(String oldUserName, String newUserName)
    {
        User user = new User();
        user.setUserId(1);
        user.setUserName("admin");
        user.setName("管理员");
        user.setPassword("123");
        user.setSalt("8d78869f470951332959580424d4bf4f");
        user.setState((byte)1);
        user.setCreateTime(LocalDateTime.now());


        return user;
    }

    //根据userid列表删除所有用户
    //@Modifying
    //@Query(value="delete from user where userId in (?1)",nativeQuery = true)
    public void deleteAllUserByUserIdList(List<Integer> userIdList)
    {
        //do DB delete operation
    }

    //根据userid删除用户角色关联表里的记录
    //@Modifying
    //@Query(value="delete from sysuserrole where userId in (?1)",nativeQuery = true)
    public void deleteAllUserRoleByUserIdList(List<Integer> userIdList)
    {
        //do DB delete operation
    }

    //根据userid删除用户角色关联表里的记录
    //@Modifying
    //@Query(value="delete from sysuserrole where userId = ?1",nativeQuery = true)
    public void deleteAllUserRoleByUserId(Integer userId){
        //do DB delete operation
    }

    //新增用户和角色关联记录
    //@Modifying
    //@Query(value="insert into sysuserrole(userId,roleId) VALUES(?1,?2)",nativeQuery = true)
    public void insertUserRole(Integer userId, Integer roleId)
    {
        //do DB delete operation
    }

    //根据用户名获取用户所具备的角色列表
//    @Query(value="select a.userId,a.userName,c.roleId,c.role,c.description from user a\n" +
//            "inner join sysuserrole b on a.userId = b.userId \n" +
//            "inner join sysrole c on b.roleId=c.roleId and c.available=1\n" +
//            "where a.userName=?1",
//    countQuery = "select count(*) from user a\n" +
//            "inner join sysuserrole b on a.userId = b.userId \n" +
//            "inner join sysrole c on b.roleId=c.roleId and c.available=1\n" +
//            "where a.userName=?1",
//    nativeQuery = true)
    public List<SysUserRole> findUserRoleByUserName(String userName)
    {
        SysUserRole userRole = new SysUserRole();
        userRole.setId(1);
        userRole.setRoleId(1);
        userRole.setUserId(1);

        SysUserRole userRole1 = new SysUserRole();
        userRole1.setId(2);
        userRole1.setRoleId(2);
        userRole1.setUserId(2);

        SysUserRole userRole3 = new SysUserRole();
        userRole1.setId(3);
        userRole1.setRoleId(3);
        userRole1.setUserId(2);
        List<SysUserRole> sysUserRoles = new ArrayList<>();
        if("admin".equalsIgnoreCase(userName))
        {
            sysUserRoles.add(userRole);
        }
        else
        {
            sysUserRoles.add(userRole1);
        }



        return sysUserRoles;
    }

    //根据用户id，列出所有角色，包括该用户不具备的角色，该用户不具备角色的时候，userid和username为null，可以做业务判断
    /*@Query(value="select a.roleId,a.role,a.description,c.userId,c.userName from sysrole a\n" +
            "left join sysuserrole b on a.roleId=b.roleId and a.available=1 and b.userId=?1\n" +
            "left join user c on c.userId=b.userId;",
    countQuery = "select count(*) from SysRole a\n" +
            "left join sysuserrole b on a.roleId=b.roleId and a.available=1 and b.userId=?1\n" +
            "left join user c on c.userId=b.userId;",
    nativeQuery = true)*/
    public List<SysUserRole> findAllUserRoleByUserId(Integer userId)
    {
        SysUserRole userRole = new SysUserRole();
        userRole.setId(1);
        userRole.setRoleId(1);
        userRole.setUserId(1);

        SysUserRole userRole1 = new SysUserRole();
        userRole1.setId(2);
        userRole1.setRoleId(2);
        userRole1.setUserId(2);

        SysUserRole userRole3 = new SysUserRole();
        userRole1.setId(3);
        userRole1.setRoleId(3);
        userRole1.setUserId(2);

        List<SysUserRole> sysUserRoles = new ArrayList<>();
        if(1 == userId)
        {
            sysUserRoles.add(userRole);
        }
        else
        {
            sysUserRoles.add(userRole1);
        }

        return sysUserRoles;
    }

    //根据用户名，获取用户具备的权限。
    /*@Query(value="select a.userId,a.userName,d.permissionId,d.permission,d.permissionName from user a \n" +
            "inner join sysuserrole b on a.userId = b.userId \n" +
            "inner join sysrolepermission c on b.roleId = c.roleId\n" +
            "inner join syspermission d on c.permissionId=d.permissionId\n" +
            "where a.userName=?1",
            countQuery = "select a.userId,a.userName,d.permissionId,d.permission,d.permissionName from user a \n" +
                    "inner join sysuserrole b on a.userId = b.userId \n" +
                    "inner join sysrolepermission c on b.roleId = c.roleId\n" +
                    "inner join syspermission d on c.permissionId=d.permissionId\n" +
                    "where a.userName=?1",
            nativeQuery = true)*/
    public List<SysPermission> findUserRolePermissionByUserName(String userName)
    {
        SysPermission sysPermission1 = new SysPermission();
        sysPermission1.setPermissionId(1);
        sysPermission1.setAvailable(true);
        sysPermission1.setPermissionName("系统管理");
        sysPermission1.setParentId(0);
        sysPermission1.setParentIds("0");
        sysPermission1.setPermission("system:view");
        sysPermission1.setResourceType("menu");
        sysPermission1.setUrl("#");
        sysPermission1.setLevel(1);

        SysPermission sysPermission2 = new SysPermission();
        sysPermission2.setPermissionId(2);
        sysPermission2.setAvailable(true);
        sysPermission2.setPermissionName("用户管理");
        sysPermission2.setParentId(1);
        sysPermission2.setParentIds("1");
        sysPermission2.setPermission("user:view");
        sysPermission2.setResourceType("menu");
        sysPermission2.setUrl("user/userList");
        sysPermission2.setLevel(2);

        SysPermission sysPermission3 = new SysPermission();
        sysPermission3.setPermissionId(3);
        sysPermission3.setAvailable(true);
        sysPermission3.setPermissionName("用户添加");
        sysPermission3.setParentId(2);
        sysPermission3.setParentIds("1/2");
        sysPermission3.setPermission("user:add");
        sysPermission3.setResourceType("button");
        sysPermission3.setUrl("user/userAdd");
        sysPermission3.setLevel(3);


        SysPermission sysPermission4 = new SysPermission();
        sysPermission4.setPermissionId(4);
        sysPermission4.setAvailable(true);
        sysPermission4.setPermissionName("用户修改");
        sysPermission4.setParentId(2);
        sysPermission4.setParentIds("1/2");
        sysPermission4.setPermission("user:edit");
        sysPermission4.setResourceType("button");
        sysPermission4.setUrl("user/userEdit");
        sysPermission4.setLevel(3);

        SysPermission sysPermission5 = new SysPermission();
        sysPermission5.setPermissionId(5);
        sysPermission5.setAvailable(true);
        sysPermission5.setPermissionName("用户删除");
        sysPermission5.setParentId(2);
        sysPermission5.setParentIds("1/2");
        sysPermission5.setPermission("user:del");
        sysPermission5.setResourceType("button");
        sysPermission5.setUrl("user/userDel");
        sysPermission5.setLevel(3);

        SysPermission sysPermission6 = new SysPermission();
        sysPermission6.setPermissionId(6);
        sysPermission6.setAvailable(true);
        sysPermission6.setPermissionName("角色管理");
        sysPermission6.setParentId(2);
        sysPermission6.setParentIds("1");
        sysPermission6.setPermission("role:view");
        sysPermission6.setResourceType("menu");
        sysPermission6.setUrl("user/rList");
        sysPermission6.setLevel(2);

        SysPermission sysPermission7 = new SysPermission();
        sysPermission7.setPermissionId(7);
        sysPermission7.setAvailable(true);
        sysPermission7.setPermissionName("角色添加");
        sysPermission7.setParentId(6);
        sysPermission7.setParentIds("1/6");
        sysPermission7.setPermission("role:add");
        sysPermission7.setResourceType("button");
        sysPermission7.setUrl("user/roleAdd");
        sysPermission7.setLevel(3);

        SysPermission sysPermission8 = new SysPermission();
        sysPermission8.setPermissionId(8);
        sysPermission8.setAvailable(true);
        sysPermission8.setPermissionName("角色修改");
        sysPermission8.setParentId(6);
        sysPermission8.setParentIds("1/6");
        sysPermission8.setPermission("role:edit");
        sysPermission8.setResourceType("button");
        sysPermission8.setUrl("user/roleEdit");
        sysPermission8.setLevel(3);

        SysPermission sysPermission9 = new SysPermission();
        sysPermission9.setPermissionId(9);
        sysPermission9.setAvailable(true);
        sysPermission9.setPermissionName("角色删除");
        sysPermission9.setParentId(6);
        sysPermission9.setParentIds("1/6");
        sysPermission9.setPermission("role:del");
        sysPermission9.setResourceType("button");
        sysPermission9.setUrl("user/roleDel");
        sysPermission9.setLevel(3);

        SysPermission sysPermission10 = new SysPermission();
        sysPermission10.setPermissionId(10);
        sysPermission10.setAvailable(true);
        sysPermission10.setPermissionName("角色授权");
        sysPermission10.setParentId(6);
        sysPermission10.setParentIds("1/6");
        sysPermission10.setPermission("role:authorize");
        sysPermission10.setResourceType("button");
        sysPermission10.setUrl("user/authorize");
        sysPermission10.setLevel(3);

        SysPermission sysPermission13 = new SysPermission();
        sysPermission13.setPermissionId(13);
        sysPermission13.setAvailable(true);
        sysPermission13.setPermissionName("角色授权");
        sysPermission13.setParentId(6);
        sysPermission13.setParentIds("1/6");
        sysPermission13.setPermission("role:authorize");
        sysPermission13.setResourceType("button");
        sysPermission13.setUrl("user/plist");
        sysPermission13.setLevel(3);

        //log:view
        SysPermission sysPermission11 = new SysPermission();
        sysPermission11.setPermissionId(11);
        sysPermission11.setAvailable(true);
        sysPermission11.setPermissionName("日志管理");
        sysPermission11.setParentId(0);
        sysPermission11.setParentIds("0");
        sysPermission11.setPermission("log:view");
        sysPermission11.setResourceType("menu");
        sysPermission11.setUrl("#");
        sysPermission11.setLevel(1);

        SysPermission sysPermission12 = new SysPermission();
        sysPermission12.setPermissionId(12);
        sysPermission12.setAvailable(true);
        sysPermission12.setPermissionName("日志查看");
        sysPermission12.setParentId(11);
        sysPermission12.setParentIds("11");
        sysPermission12.setPermission("log:view");
        sysPermission12.setResourceType("menu");
        sysPermission12.setUrl("/log/list");
        sysPermission12.setLevel(2);

        List<SysPermission> sysPermissions = new ArrayList<>();

        if("admin".equalsIgnoreCase(userName))
        {
            sysPermissions.add(sysPermission1);
            sysPermissions.add(sysPermission2);
            sysPermissions.add(sysPermission3);
            sysPermissions.add(sysPermission4);
            sysPermissions.add(sysPermission5);
            sysPermissions.add(sysPermission6);
            sysPermissions.add(sysPermission7);
            sysPermissions.add(sysPermission8);
            sysPermissions.add(sysPermission9);
            sysPermissions.add(sysPermission10);
            sysPermissions.add(sysPermission11);
            sysPermissions.add(sysPermission12);
        }
        else
        {
            sysPermissions.add(sysPermission11);
            sysPermissions.add(sysPermission12);
        }
        return sysPermissions;
    };

    /*@Query(value = "select version()",nativeQuery = true)*/
    public String getDbVersion(){
        return "5.0.0";
    };

}
