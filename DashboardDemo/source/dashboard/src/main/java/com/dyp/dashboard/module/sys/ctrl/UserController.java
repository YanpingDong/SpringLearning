package com.dyp.dashboard.module.sys.ctrl;

import com.dyp.dashboard.factory.LogFactory;
import com.dyp.dashboard.model.ListTableData;
import com.dyp.dashboard.module.sys.entity.SysUserRole;
import com.dyp.dashboard.module.sys.entity.User;
import com.dyp.dashboard.module.sys.model.Pageable;
import com.dyp.dashboard.module.sys.model.RoleSettingRequest;
import com.dyp.dashboard.module.sys.service.LogService;
import com.dyp.dashboard.module.sys.service.LoginService;
import com.dyp.dashboard.module.sys.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: xu.dm
 * @Date: 2018/8/11 15:54
 * @Description:
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private String algorithmName = "md5";
    private int hashIterations = 2;
    private String salt = "8d78869f470951332959580424d4bf4f";

    @Resource
    UserService userService;
    @Resource
    LoginService loginService;
    @Resource
    LogService logService;
    /**
     * 用户查询.
     * @return
     */
    @GetMapping("/ulist")
    public String userList(){
        return "/sys/userList";
    }

    @GetMapping("role/setting")
    public String userRoleSetting(){
        return "/sys/userRoleSetting";
    }

    @PostMapping("role/setting")
    @ResponseBody
    public void userRoleSetting(RoleSettingRequest roleSettingRequest){
        String roles = roleSettingRequest.getRoles();
        if(roles != null)
        {
            String[] roleIds = roleSettingRequest.getRoles().split(",");
            List<SysUserRole> sysUserRoles = new ArrayList<>();
            for(String roleId : roleIds)
            {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setRoleId(Integer.valueOf(roleId));
                sysUserRole.setUserId(roleSettingRequest.getUserId());
                sysUserRoles.add(sysUserRole);
            }

            userService.saveUserRoles(sysUserRoles);
        }
    }

    @RequestMapping(value = "/all")
    @RequiresPermissions("user:view")//权限管理;
    @ResponseBody
    public ListTableData getUserList(HttpServletRequest request)
    {
        List<User> users = userService.selectAll();

        ListTableData  ajaxData = new ListTableData();
        List<List<String>> data = new ArrayList<>();

        if(users != null)
        {
            for (User user : users)
            {
                List<String> column = new ArrayList<>();
                column.add(user.getUserId().toString());
                column.add(user.getUserName());
                column.add(user.getName());
                column.add(user.getTel());
                column.add(user.getEmail());
                column.add(user.getCreateTime().toString());
                column.add("");
                column.add(user.getState() == true ? "正常" : "锁定");
                data.add(column);
            }
        }

        ajaxData.setData(data);
        return ajaxData;
    }

    @DeleteMapping(value = "/{userId}")
    @RequiresPermissions("user:delete")//权限管理;
    @ResponseBody
    public void deleteById(@PathVariable("userId")Integer userId){
        userService.deleteById(userId);
    }

    @RequestMapping(value = "/update/{userId}",method = RequestMethod.GET)
    @RequiresPermissions("user:update")//权限管理;
    public String toUserUpdate(@PathVariable int userId, Model model)
    {
        User user = userService.findUserById(userId);
        model.addAttribute("email", user.getEmail());
        model.addAttribute("name", user.getName());
        model.addAttribute("tel", user.getTel());
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("state",user.getState());
        return "/sys/userUpdate";
    }

    @GetMapping(value = "/add")
    @RequiresPermissions("user:add")//权限管理;
    public String toUserAdd(User user){
        return "/sys/userAdd";
    }


    /**
     * 用户添加;
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.POST)
    @RequiresPermissions("user:add")//权限管理;
    @ResponseBody
    public String save( User user, BindingResult bindingResult, String password2){
        if(bindingResult.hasErrors())
        {
            return "0";
        }

        user.setSalt(this.salt);
        if(user.getUserId()==null) {
            user.setCreateTime(LocalDateTime.now());
//            String encryptPwd = EncryptUtils.encrypt(user.getPassword(),user.getCredentialsSalt(),this.algorithmName,this.hashIterations);
            String  encryptPwd=user.getPassword();
            user.setPassword(encryptPwd);
        }
        else {
            if(!user.getPassword().equals(password2))
            {
//                String encryptPwd = EncryptUtils.encrypt(user.getPassword(),user.getCredentialsSalt(),this.algorithmName,this.hashIterations);
                String  encryptPwd=user.getPassword();
                user.setPassword(encryptPwd);
            }
        }
        try {
            userService.save(user);
            logService.writeLog(LogFactory.createSysLog("新增或修改用户","用户："+user.getUserName()));
            return "/user/ulist";
        }catch (Exception e)
        {
            e.printStackTrace();
            return "0";
        }
    }

    @RequestMapping("/checkUserExists")
    @ResponseBody
    public Object checkRoleExists(@RequestParam String newUserName, @RequestParam(required = false) Integer userId, @RequestParam(required = false) String oldUserName)
    {
        Map<String,Boolean> map = new HashMap<>();
        if(userId==null)
        {
            boolean result = !userService.checkUserExists(newUserName);
            map.put("valid",result);
        }
        else
        {
            boolean result = !userService.checkUserExists2(oldUserName,newUserName);
            map.put("valid",result);
        }
        return map;
    }


    @RequestMapping(value = "/edit/{id}")
    @RequiresPermissions("user:edit")
    public String edit(@PathVariable("id")Integer id, Map<String,Object> map)
    {
        User user = userService.findUserById(id);
        map.put("user",user);
        return "/sys/userForm";
    }

    /**
     * 用户删除;
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    @RequiresPermissions("user:del")//权限管理;
    public Object userDel(@RequestParam String userIdList){
        if(userIdList==null || userIdList.isEmpty())
        {
            return "0";
        }
        String[] sList = userIdList.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String s:sList )
        {
            if(s.equalsIgnoreCase("1"))
                return "0";
            idList.add(Integer.parseInt(s));

        }
        Map<String,String> map = new HashMap<>();
        try {
            userService.deleteAllUserByUserIdList(idList);
            map.put("success","true");
            map.put("url","/user/ulist");
            logService.writeLog(LogFactory.createSysLog("删除用户","用户id："+userIdList));
        }catch (Exception e)
        {
            e.printStackTrace();
            map.put("error","true");
        }

        return map;
    }

    @RequestMapping(value="/toListUserRole/{userId}")
    @RequiresPermissions("user:roleGrant")
    public String listUserRole(@PathVariable("userId")Integer userId, Map<String, Object> map)
    {
        User user = userService.findUserById(userId);
        map.put("user",user);
        return "/sys/userRole";
    }


    @RequestMapping(value = "/toGetUserRole/{userId}")
    @ResponseBody
    @RequiresPermissions("user:roleGrant")
    public Object getUserRole(@PathVariable("userId")Integer userId)
    {
        if(userId==null)
            return null;

        List<SysUserRole> list = userService.findAllUserRoleByUserId(userId);
        return list;
    }




    @RequestMapping(value="/toGrantUserRole")
    @ResponseBody
    @RequiresPermissions("user:roleGrant")
    public Object grantUserRole(Integer userId, String roleIdList)
    {
        if(userId==1) return 0;
        Map<String,String> map = new HashMap<>();
        if(roleIdList==null || roleIdList.isEmpty())
        {
            try {
                userService.deleteAllUserRoleByUserId(userId);
                map.put("success","true");
                map.put("url","/user/ulist");
                logService.writeLog(LogFactory.createSysLog("角色清除","用户ID："+userId));
                return map;
            }catch (Exception e)
            {
                e.printStackTrace();
                map.put("sucess","false");
                map.put("url","/user/ulist");
                return map;
            }
        }
        String[] sList = roleIdList.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String s:sList )
        {
            idList.add(Integer.parseInt(s));
        }

        try {
            userService.grantUserRole(userId,idList);
            map.put("sucess","true");
            map.put("url","/user/ulist");
            logService.writeLog(LogFactory.createSysLog("角色分配","用户ID："+userId+" 角色id列表："+roleIdList));

            return map;
        }catch (Exception e)
        {
            e.printStackTrace();
            map.put("sucess","false");
            map.put("url","/user/ulist");
            return map;
        }
    }

    @RequestMapping(value="/toChangePassword")
    public String toChangePassword(String password,String newPassword,String newPassword2)
    {
        return "/sys/changePwd";
    }

    @RequestMapping(value="/changePassword",method = RequestMethod.POST)
    @ResponseBody
    public Object changePassword(HttpServletRequest request)
    {
        Map<String,String> map = new HashMap<>();
        String password = request.getParameter("password");
        String newPassword = request.getParameter("newPassword");
        String newPassword2 = request.getParameter("newPassword2");

        if(newPassword==null)
        {
            map.put("success","false");
            map.put("result","密码不能为空");
            return map;
        }

        if(!newPassword.equals(newPassword2))
        {
            map.put("success","false");
            map.put("result","两次密码输入不一样");
            return map;
        }

        if(newPassword.length()<6)
        {
            map.put("success","false");
            map.put("result","密码长度必须大于6位");
            return map;
        }


        String userName = loginService.getCurrentUserName();
        if(userName==null || userName.isEmpty())
        {
            map.put("success","false");
            map.put("result","用户错误");
            return map;
        }
        User user = userService.findByUserName(userName);
        if(user==null)
        {
            map.put("success","false");
            map.put("result","用户错误");
            map.put("url","/logout");
            return map;
        }

//        String encryptPwd = EncryptUtils.encrypt(password,user.getCredentialsSalt(),this.algorithmName,this.hashIterations);
        String encryptPwd = password;
        if(!encryptPwd.equals(user.getPassword()))
        {
            map.put("success","false");
            map.put("result","当前用户密码不正确");
            map.put("url","/user/edit/1");
            return map;
        }

//        String encryptNewPwd =EncryptUtils.encrypt(newPassword,user.getCredentialsSalt(),this.algorithmName,this.hashIterations);
        String encryptNewPwd = password;
        user.setPassword(encryptNewPwd);
        userService.save(user);
        map.put("success","true");
        map.put("result","密码修改成功，请重新登录");
        map.put("url","/logout");
        logService.writeLog(LogFactory.createSysLog("修改密码","成功"));
        return map;
    }

    @RequestMapping(value="/toCheckPwd")
    @ResponseBody
    public Object checkCurrentPwd(@RequestParam String password)
    {
        Map<String,Boolean> map = new HashMap<>();
        if(password==null)
        {
            map.put("valid",true);
            return map;
        }

        String userName = loginService.getCurrentUserName();
        if(userName==null || userName.isEmpty())
        {
            map.put("valid",true);
            return map;
        }
        User user = userService.findByUserName(userName);
        if(user==null)
        {
            map.put("valid",true);
            return map;
        }

//        String encryptPwd = EncryptUtils.encrypt(password,user.getCredentialsSalt(),this.algorithmName,this.hashIterations);
        String encryptPwd = password;
        if(!encryptPwd.equals(user.getPassword()))
        {
            map.put("valid",true);
            return map;
        }

        map.put("valid",true);
        return map;
    }





}
