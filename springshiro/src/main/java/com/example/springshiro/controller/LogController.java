package com.example.springshiro.controller;

import com.example.springshiro.entity.SysLog;
import com.example.springshiro.service.LogService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: xu.dm
 * @Date: 2018/10/12 16:07
 * @Description:
 */
@Controller
@RequestMapping(value = "/log")
public class LogController {
    @Resource
    LogService logService;

    @RequestMapping(value="/list")
    public String list()
    {
        return "/sys/logList";
    }


    @RequestMapping(value = "/getLogList")
    @ResponseBody
    @RequiresPermissions("log:view")
    public Object getLogList(HttpServletRequest request)
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

        String searchText=request.getParameter("searchText")==null ? "": request.getParameter("searchText");

        String sortName=request.getParameter("sortName")==null ? "roleId": request.getParameter("sortName");
        String sortOrder=request.getParameter("sortOrder")==null ? "asc": request.getParameter("sortOrder");


        List<SysLog> sysLogPage =  logService.findAll(searchText);
        map.put("total",sysLogPage.size());
        map.put("rows",sysLogPage);

        return map;
    }
}
