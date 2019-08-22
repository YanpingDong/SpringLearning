package com.example.springshiro;

import com.example.springshiro.entity.SysPermission;
import com.example.springshiro.repository.UserRepository;
import com.example.springshiro.utils.EncryptUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 * 这个使用component是因为实际工程中会引入DAO层对象，而DAO层对像一般都是由Spring框架做管理的
 * */
@Component
public class CustomRealm extends AuthorizingRealm {
    public static String algorithmName = "md5";
    public static int hashIterations = 2;
    public static String salt = "8d78869f470951332959580424d4bf4f";

    @Autowired
    UserRepository userRepository;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("-------权限认证方法--------");
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<SysPermission> sysPermissions = userRepository.findUserRolePermissionByUserName(username);
        Set<String> stringSet = new HashSet<>();

        for(SysPermission sysPermission : sysPermissions)
        {
            stringSet.add(sysPermission.getPermission());
        }
        info.setStringPermissions(stringSet);

        return info;
    }

    /**
     * 这里可以注入userService,为了方便演示，我就写死了帐号了密码 * private UserService userService; * <p> * 获取即将需要认证的信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("-------身份认证方法--------");
        String userName = (String) authenticationToken.getPrincipal();
        //根据用户名从数据库获取密码，这里就模拟下
        String userPwd = "123456";
        //模拟存数据库前的加密操作，本应该是存入前就加密过的
        String encryptPwd = EncryptUtils.encrypt(userPwd,salt,this.algorithmName,this.hashIterations);


        if (userName == null) {
            throw new AccountException("用户名不正确");
        } else if (!userPwd.equals(userPwd)) {//做简单判断
            throw new AccountException("密码不正确");
        }
//        return new SimpleAuthenticationInfo(userName, password, getName());
        return new SimpleAuthenticationInfo(userName, encryptPwd, ByteSource.Util.bytes(salt),getName());
    }

}
