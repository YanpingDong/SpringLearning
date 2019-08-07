package com.example.springshiro.controller;

import com.example.springshiro.entity.SysLog;
import com.example.springshiro.entity.SysRole;
import com.example.springshiro.entity.SysRolePermission;
import com.example.springshiro.factory.LogFactory;
import com.example.springshiro.model.Pageable;
import com.example.springshiro.service.LogService;
import com.example.springshiro.service.RoleService;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: xu.dm
 * @Date: 2018/9/5 22:28
 * @Description:
 */
@Controller
@RequestMapping("/user")
public class RoleController {
    @Resource
    RoleService roleService;
    @Resource
    LogService logService;

    //,produces="application/json;charset=UTF-8"
    @RequestMapping(value="/role")
    @ResponseBody
    @RequiresPermissions("role:view")
    public Object getRole(HttpServletRequest request, HttpServletResponse response)
    {

        int pageSize = 10;
        try {
            pageSize =  Integer.parseInt(request.getParameter("pageSize"));
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        int pageNumber=0 ;
        try {
            pageNumber =  Integer.parseInt(request.getParameter("pageNumber"))-1;
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        Map<String, Object> map = new HashMap<>();

        String strRole=request.getParameter("searchText")==null ? "": request.getParameter("searchText");

        String sortName=request.getParameter("sortName")==null ? "roleId": request.getParameter("sortName");
        String sortOrder=request.getParameter("sortOrder")==null ? "asc": request.getParameter("sortOrder");

        Pageable pageable = new Pageable(pageNumber,pageSize,"asc");
        List<SysRole> sysRolePage = roleService.findAllByRoleContains(strRole, pageable);
        map.put("total",sysRolePage.size());
        map.put("rows",sysRolePage);

        return map;


    }

    @RequestMapping("/rlist")
    @RequiresPermissions("role:view")
    public String list()
    {
        return "/sys/roleList";
    }


    @RequestMapping(value="/roleAdd", method= RequestMethod.GET)
    @RequiresPermissions("role:add")
    public String toAdd(SysRole sysRole) {
//        sysRole.setAvailable(false);
        return "/sys/roleAdd";
    }

    @RequestMapping(value="/roleAdd",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("role:add")
    public String save(@Valid SysRole sysRole, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "0";
        }
        if(sysRole.getRoleId()==null)
            sysRole.setCreateTime(LocalDateTime.now());
        try {
            roleService.save(sysRole);
            SysLog sysLog = LogFactory.createSysLog("操作角色（新增或修改）","角色："+sysRole.getRole());
            logService.writeLog(sysLog);

            return  "/user/rlist";
        }catch (Exception e)
        {
            e.printStackTrace();
            return "0";
        }
    }


    @RequestMapping("/checkRoleExists")
    @ResponseBody
    public Object checkRoleExists(@RequestParam String newRole, @RequestParam(required = false) Integer roleId, @RequestParam(required = false) String oldRole)
    {
        Map<String,Boolean> map = new HashMap<>();
        if(roleId==null)
        {
            boolean result = !roleService.checkRoleExists(newRole);
            map.put("valid",result);
        }
        else
        {
            boolean result = !roleService.checkRoleExists(oldRole,newRole);
            map.put("valid",result);
        }
        return map;
    }


    @RequestMapping(value = "/roleEdit/{id}")
    @RequiresPermissions("role:edit")
    public String edit(@PathVariable("id")Integer id, Map<String,Object> map)
    {
        SysRole sysRole = roleService.findById(id).orElse(new SysRole());
        map.put("sysRole",sysRole);
        return "/sys/roleAdd";
    }
    
    @RequestMapping(value = "/roleDelete")
    @ResponseBody
    @RequiresPermissions("role:del")
    public Object delete(@RequestParam String roleIdList)
    {
        if(roleIdList==null || roleIdList.isEmpty())
        {
            return "0";
        }
        String[] sList = roleIdList.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String s:sList )
        {
            if(s.equalsIgnoreCase("1"))
                return "0";
            idList.add(Integer.parseInt(s));

        }
        boolean result = roleService.deleteAllByRoleIdIn(idList);
        Map<String,String> map = new HashMap<>();
        if(result)
        {
            map.put("success","true");
            map.put("url","/user/rlist");

            logService.writeLog(LogFactory.createSysLog("删除角色","角色id："+roleIdList));
        }
        else
        {
            map.put("error","true");
        }

        return map;
    }

    @RequestMapping(value = "/plist/{roleId}")
    @RequiresPermissions("role:authorize")
    public String permissionList(@PathVariable("roleId")Integer roleId, Map<String, Object> map)
    {
        SysRole sysRole = roleService.findById(roleId).orElse(new SysRole());
        map.put("sysRole",sysRole);
        return "/sys/sysPermission";
    }

    @RequestMapping("/getPermission/{roleId}")
    @ResponseBody
    @RequiresPermissions("role:authorize")
    public Object getRolePermission(@PathVariable("roleId")Integer roleId)
    {
        if(roleId==null)
            return null;

        List<SysRolePermission> list = roleService.findSysRolePermissionByRoleId(roleId);
        return list;
    }

    @RequestMapping(value = "/toAuthorize")
    @ResponseBody
    @RequiresPermissions("role:authorize")
    public Object toAuthorize(Integer roleId,String permissionIdList)
    {
        if(roleId==1) return 0;
        Map<String,String> map = new HashMap<>();
        if(permissionIdList==null || permissionIdList.isEmpty())
        {
            try {
                roleService.clearAuthorization(roleId);
                map.put("success","true");
                map.put("url","/user/rlist");
                logService.writeLog(LogFactory.createSysLog("清除角色权限","成功"));
                return map;
            }catch (Exception e)
            {
                e.printStackTrace();
                map.put("sucess","false");
                map.put("url","/user/rlist");
                return map;
            }
        }
        String[] sList = permissionIdList.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String s:sList )
        {
            idList.add(Integer.parseInt(s));
        }

        try {
            roleService.grantAuthorization(roleId,idList);
            map.put("sucess","true");
            map.put("url","/user/rlist");
            logService.writeLog(LogFactory.createSysLog("角色授权","权限列表："+permissionIdList));
            return map;
        }catch (Exception e)
        {
            e.printStackTrace();
            map.put("sucess","false");
            map.put("url","/user/rlist");
            return map;
        }

    }
}
