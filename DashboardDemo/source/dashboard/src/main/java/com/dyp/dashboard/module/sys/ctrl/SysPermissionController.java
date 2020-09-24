package com.dyp.dashboard.module.sys.ctrl;

import com.dyp.dashboard.module.sys.entity.SysPermission;
import com.dyp.dashboard.module.sys.model.PermissionResponse;
import com.dyp.dashboard.module.sys.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/permission")
public class SysPermissionController {
    @Autowired
    private SysPermissionService sysPermissionService;

    @GetMapping(value="/plist")
    public String toPermission()
    {
        return "/sys/permissionList";
    }

    @GetMapping(value="/all")
    @ResponseBody
    public Object getAllPermission()
    {
        List<PermissionResponse> permissionResponses = new ArrayList<>();

        List<SysPermission>  sysPermissions = sysPermissionService.getAllPermission();
        if(null != sysPermissions)
        {
            for(SysPermission sysPermission : sysPermissions)
            {
                PermissionResponse permissionResponse = new PermissionResponse();
                permissionResponse.setId(sysPermission.getPermissionId());
                permissionResponse.setName(sysPermission.getPermissionName());
                permissionResponse.setPermissionValue(sysPermission.getPermission());
                permissionResponse.setPid(sysPermission.getParentId());
                permissionResponse.setStatus(sysPermission.getAvailable() == true ? 1 : 0);
                permissionResponse.setUrl(sysPermission.getUrl());
                permissionResponses.add(permissionResponse);
            }
        }

        return permissionResponses;
    }
}
