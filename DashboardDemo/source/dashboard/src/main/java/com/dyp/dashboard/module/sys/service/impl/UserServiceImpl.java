package com.dyp.dashboard.module.sys.service.impl;

import com.dyp.dashboard.config.CommonCacheConfig;
import com.dyp.dashboard.module.sys.entity.SysPermission;
import com.dyp.dashboard.module.sys.entity.SysUserRole;
import com.dyp.dashboard.module.sys.entity.User;
import com.dyp.dashboard.module.sys.model.Pageable;
import com.dyp.dashboard.module.sys.repository.UserRepository;
import com.dyp.dashboard.module.sys.service.CacheService;
import com.dyp.dashboard.module.sys.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author: xu.dm
 * @Date: 2018/8/11 16:47
 * @Description:
 * 缓存设计ps：
 */
@Service
public class UserServiceImpl implements UserService {
    //key命名规则：前缀+":"+"实体ID或者实体name（userName）"+":业务（可选）"
    private String keyPrefix = "user:";

    @Resource
    private UserRepository userRepository;

    @Resource(name = "yourCacheServiceImpl")
    private CacheService cacheService;

    @Resource
    private CommonCacheConfig cacheConfig;

    @Override
    public String getDbVersion() {
        return userRepository.getDbVersion();
    }

    private User findByUserNameInCache(String userName) {
        User user;
        String key = keyPrefix + userName;

        if (cacheService.hasKey(key)) {
            user = (User) cacheService.get(key);
            cacheService.expire(key, cacheConfig.getTimeToLive());
            System.out.println("----->>findByUserName read cache：" + key);
            return user;
        }

        user = userRepository.findByUserName(userName);
        System.out.println("----->>findByUserName write cache：" + key);
        cacheService.set(key, user, cacheConfig.getTimeToLive());

        return user;
    }

    @Override
    public User findByUserName(String userName) {
        if (cacheConfig.isCacheEnable())
            return findByUserNameInCache(userName);
        return userRepository.findByUserName(userName);
    }

    @Override
    public List<SysUserRole> findUserRoleByUserName(String userName) {
        return userRepository.findUserRoleByUserName(userName);
    }

    @Override
    public List<SysUserRole> findAllUserRoleByUserId(Integer userId) {
        return userRepository.findAllUserRoleByUserId(userId);
    }

    //代理实现的接口类，暂时没有找到合适的反序列化方式
    private List<SysPermission> findUserRolePermissionInCache(String userName) {
        String key = this.keyPrefix + userName+":UserRolePermission";
        List<SysPermission> sysPermissions;
        if (cacheService.hasKey(key)) {
            System.out.println("----->>UserRolePermission read cache：" + key);
            sysPermissions = (List<SysPermission>) cacheService.get(key);
            cacheService.expire(key, cacheConfig.getTimeToLive());
            return sysPermissions;
        }
        sysPermissions = userRepository.findUserRolePermissionByUserName(userName);
        System.out.println("----->>UserRolePermission write cache：" + key);
        cacheService.set(key, sysPermissions, cacheConfig.getTimeToLive());
        return sysPermissions;
    }

    @Override
    public List<SysPermission> findUserRolePermissionByUserName(String userName) {
        //反序列化有问题，先注销
//        if (cacheConfig.isCacheEnable())
//            return findUserRolePermissionInCache(userName);
        return userRepository.findUserRolePermissionByUserName(userName);
    }

    private User findUserInCatch(Integer userId)
    {
        String key = this.keyPrefix+userId;
        User user;
        if(cacheService.hasKey(key))
        {
            System.out.println("----->>findUserById read cache：" + key);
            user = (User)cacheService.get(key);
            cacheService.expire(key,cacheConfig.getTimeToLive());
            return user;
        }

        System.out.println("----->>findUserById write cache：" + key);
        user = userRepository.findById(userId);
        cacheService.set(key,user,cacheConfig.getTimeToLive());
        return user;
    }

    @Override
    public User findUserById(Integer userId) {
        if(cacheConfig.isCacheEnable())
            return findUserInCatch(userId);
        return userRepository.findById(userId);
    }

    //保存和修改user对象的时候删除对应user的缓存
    private void deleteCache(User user) {

        if(user==null)
            return;

        String key = this.keyPrefix + user.getUserName();

        Set<String> keys = null;
        keys = cacheService.getKey(key+"*");
        if(keys!=null && keys.size()>0)
        {
            System.out.println("----->>delete cache：key:" + keys);
            cacheService.del(keys);
        }

        key = this.keyPrefix + user.getUserId();
        keys = cacheService.getKey(key + "*");
        if (keys != null && keys.size() > 0) {
            System.out.println("----->>delete cache：key:" + keys);
            cacheService.del(keys);
        }


        //缺省查询状态下userName=空字符
        key = this.keyPrefix + ":";
        keys = cacheService.getKey(key+"*");
        if(keys!=null && keys.size()>0)
        {
            System.out.println("----->>delete cache：key:" + keys);
            cacheService.del(keys);
        }
    }

    @Override
    public User save(User user) {

        User u = userRepository.save(user);
        if(cacheConfig.isCacheEnable())
        {
            try {
                deleteCache(u);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return u;
    }

    @Override
    public boolean checkUserExists(String userName) {
        User user = this.findByUserName(userName);
        if (user != null)
            return true;
        else
            return false;
    }

    @Override
    public boolean checkUserExists2(String oldUserName, String newUserName) {
        User user = userRepository.findUserExist2(oldUserName, newUserName);

        if (user != null)
            return true;
        else
            return false;
    }

    @Override
    public List<User> findAllByUserNameContains(String userName, Pageable pageable) {
//        if (cacheConfig.isCacheEnable())
//            return findAllByUserNameContainsInCache(userName, pageable);
        return userRepository.findAllByUserNameContains(userName, pageable);
    }

    //按id判断是否有user缓存
    private User checkCacheByUserId(Integer userId)
    {
        User user = null;
        String key = this.keyPrefix + userId;
        if(cacheService.hasKey(key))
        {
            user = (User)cacheService.get(key);
        }

        return user;
    }

    //删除和userId关联对象的所有缓存
    private void deleteCacheByUserId(List<Integer> userIdList)
    {
        User user;

        Set<String>  keys;
        String key;
        for(Integer userId:userIdList)
        {
             user = checkCacheByUserId(userId);
             if(user!=null)
             {
                key = this.keyPrefix+user.getUserId();
                keys = cacheService.getKey(key+"*");
                if(keys!=null && keys.size()>0) {
                    System.out.println("----->>delete cache：key:" + keys);
                    cacheService.del(keys);
                }

                 key = this.keyPrefix+user.getUserName();
                 keys = cacheService.getKey(key+"*");
                 if(keys!=null && keys.size()>0) {
                     System.out.println("----->>delete cache：key:" + keys);
                     cacheService.del(keys);
                 }

                 //删除userName或者userId为空的数据
                 key = this.keyPrefix+":";
                 keys = cacheService.getKey(key+"*");
                 if(keys!=null && keys.size()>0) {
                     System.out.println("----->>delete cache：key:" + keys);
                     cacheService.del(keys);
                 }
             }
        }
    }

    @Override
    public void deleteAllUserByUserIdList(List<Integer> userIdList) {
        try {
            if(cacheConfig.isCacheEnable())
                deleteCacheByUserId(userIdList);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        userRepository.deleteAllUserRoleByUserIdList(userIdList);
        userRepository.deleteAllUserByUserIdList(userIdList);
    }


    @Override
    public void deleteAllUserRoleByUserIdList(List<Integer> userIdList) {
        try {
            if(cacheConfig.isCacheEnable())
                deleteCacheByUserId(userIdList);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        userRepository.deleteAllUserRoleByUserIdList(userIdList);
    }

    @Override
    public void deleteAllUserRoleByUserId(Integer userId) {
        if(cacheConfig.isCacheEnable()) {
            try {
                List<Integer> userIdList = new ArrayList<>();
                userIdList.add(userId);
                deleteCacheByUserId(userIdList);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        userRepository.deleteAllUserRoleByUserId(userId);
    }


    @Override
    public void grantUserRole(Integer userId, List<Integer> roleIdList) {
        deleteAllUserRoleByUserId(userId);
        for (Integer roleId : roleIdList) {
            userRepository.insertUserRole(userId, roleId);
        }
    }

}
