package com.dyp.dashboard.factory;


import com.dyp.dashboard.module.sys.entity.SysLog;
import com.dyp.dashboard.util.NetworkUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


public class LogFactory {
    public static SysLog createSysLog(String action, String event)
    {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SysLog sysLog = new SysLog();
        sysLog.setAction(action);
        sysLog.setEvent(event);
        sysLog.setHost(NetworkUtils.getIpAddress(request));
        sysLog.setUserName((String)request.getSession().getAttribute("userName"));
        sysLog.setInsertTime(LocalDateTime.now());

        return sysLog;
    }
}
