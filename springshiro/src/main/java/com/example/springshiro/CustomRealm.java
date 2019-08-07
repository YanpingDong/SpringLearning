package com.example.springshiro;

import com.example.springshiro.entity.SysPermission;
import com.example.springshiro.repository.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
* 这个使用component是因为实际工程中会引入DAO层对象，而DAO层对像一般都是由Spring框架做管理的
* */
@Component
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    UserRepository userRepository;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("-------权限认证方法--------");
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if ("admin".equalsIgnoreCase(username))
        {
            List<SysPermission> sysPermissions = userRepository.findUserRolePermissionByUserName(username);
            Set<String> stringSet = new HashSet<>();

            for(SysPermission sysPermission : sysPermissions)
            {
                stringSet.add(sysPermission.getPermission());
            }
            info.setStringPermissions(stringSet);
        }

        return info;
    }

    /**
     * 这里可以注入userService,为了方便演示，我就写死了帐号了密码 * private UserService userService; * <p> * 获取即将需要认证的信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("-------身份认证方法--------");
        String userName = (String) authenticationToken.getPrincipal();
        String userPwd = new String((char[]) authenticationToken.getCredentials());
        //根据用户名从数据库获取密码
//        String password = "2415b95d3203ac901e287b76fcef640b";
        String password = "123";
        if (userName == null) {
            throw new AccountException("用户名不正确");
        } else if (!userPwd.equals(password)) {
            //throw new AccountException("密码不正确");
        }
        return new SimpleAuthenticationInfo(userName, password, getName());
    }

}
