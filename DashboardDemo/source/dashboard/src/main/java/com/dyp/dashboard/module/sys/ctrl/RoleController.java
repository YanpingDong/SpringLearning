package com.dyp.dashboard.module.sys.ctrl;


import com.dyp.dashboard.factory.LogFactory;
import com.dyp.dashboard.model.ListTableData;
import com.dyp.dashboard.module.sys.entity.SysLog;
import com.dyp.dashboard.module.sys.entity.SysPermission;
import com.dyp.dashboard.module.sys.entity.SysRole;
import com.dyp.dashboard.module.sys.entity.User;
import com.dyp.dashboard.module.sys.model.Pageable;
import com.dyp.dashboard.module.sys.model.RoleInfo;
import com.dyp.dashboard.module.sys.model.RoleSettingInfo;
import com.dyp.dashboard.module.sys.service.LogService;
import com.dyp.dashboard.module.sys.service.RoleService;
import com.dyp.dashboard.util.IOUtils;
import com.dyp.dashboard.util.JsonUtils;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/role")
public class RoleController {
    @Resource
    RoleService roleService;
    @Resource
    LogService logService;

    @GetMapping(value="/setting/info")
    @ResponseBody
    public RoleSettingInfo data() {
        RoleSettingInfo roleSettingInfo = new RoleSettingInfo();
        List<RoleInfo> roleInfos = new ArrayList<>();
        List<SysRole>  sysRoles = roleService.findAll();
        if (sysRoles != null)
        {
            for(SysRole sysRole : sysRoles)
            {
                RoleInfo roleInfo = new RoleInfo();
                roleInfo.setRoleId(sysRole.getRoleId());
                roleInfo.setRoleName(sysRole.getRole());
                roleInfos.add(roleInfo);
            }
        }
        roleSettingInfo.setRoleInfos(roleInfos);
        roleSettingInfo.setDefaultSelectedRole("3,4");
        return roleSettingInfo;
    }

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

    @GetMapping("/all")
    @ResponseBody
    public ListTableData getAllRole()
    {
        List<SysRole> sysRoles = roleService.findAll();

        ListTableData ajaxData = new ListTableData();
        List<List<String>> data = new ArrayList<>();

        if(sysRoles != null)
        {
            for (SysRole sysRole : sysRoles)
            {
                List<String> column = new ArrayList<>();
                column.add(sysRole.getRoleId().toString());
                column.add(sysRole.getRole());
                column.add(sysRole.getAvailable() == true ? "正常" : "锁定");
                column.add(sysRole.getDescription());

                data.add(column);
            }
        }

        ajaxData.setData(data);
        return ajaxData;
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
    public String save(SysRole sysRole, BindingResult bindingResult)
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
    public String permissionList(@PathVariable("roleId")Integer roleId, Model model)
    {
        SysRole sysRole = roleService.findById(roleId).orElse(new SysRole());

        model.addAttribute("sysRole",sysRole);
        return "/sys/sysPermission";
    }

    @RequestMapping("/getPermission/{roleId}")
    @ResponseBody
    @RequiresPermissions("role:authorize")
    public Object getRolePermission(@PathVariable("roleId")Integer roleId)
    {
        if(roleId==null)
            return null;

        //以下数据应该是从数据库中取，但为了方便启动和演示就省了
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



        return sysPermissions;
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
