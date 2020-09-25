package com.dyp.dashboard.module.sys.ctrl;

import com.dyp.dashboard.model.ActionResultResponse;
import com.dyp.dashboard.module.sys.entity.SysPermission;
import com.dyp.dashboard.module.sys.model.PermissionResponse;
import com.dyp.dashboard.module.sys.service.SysPermissionService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/{parentId}")
    public String toPermissionAddPage(Map<String, Object> map, @PathVariable int parentId){
        map.put("parentId",parentId);
        return "/sys/permissionAdd";
    }

    @GetMapping("/{id}/update")
    public String toPermissionUpdatePage(Map<String, Object> map, @PathVariable int id){
        SysPermission sysPermission = sysPermissionService.selectById(id);
        map.put("putAddr", "/permission/"+id);
        map.put("id",id);
        map.put("url", sysPermission.getUrl());
        return "/sys/permissionUpdate";
    }



    @PostMapping()
    @ResponseBody
    public ActionResultResponse createPermission(SysPermission sysPermission){
        sysPermissionService.createSysPermission(sysPermission);
        return createActionResultResponseWhenSuccess();
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ActionResultResponse updatePermission(@PathVariable(value = "id") int id,
                                                 SysPermission sysPermission){
        sysPermission.setPermissionId(id);
        sysPermissionService.updateById(sysPermission);
        return createActionResultResponseWhenSuccess();
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ActionResultResponse deletePermissionById(@PathVariable(value = "id") int id){
        sysPermissionService.deleteById(id);
        return createActionResultResponseWhenSuccess();
    }

    @NotNull
    private ActionResultResponse createActionResultResponseWhenSuccess() {
        ActionResultResponse actionResultResponse = new ActionResultResponse();
        actionResultResponse.setMsg("OK");
        actionResultResponse.setStatus(200);
        actionResultResponse.setUrl("/permission/plist");
        return actionResultResponse;
    }

}
