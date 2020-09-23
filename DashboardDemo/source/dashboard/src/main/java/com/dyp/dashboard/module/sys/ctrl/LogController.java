package com.dyp.dashboard.module.sys.ctrl;

import com.dyp.dashboard.module.sys.entity.SysLog;
import com.dyp.dashboard.util.IOUtils;
import com.dyp.dashboard.util.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/log")
public class LogController {

    @Value("classpath:data.json")
    Resource roleData;

    @RequestMapping(value="/data")
    @ResponseBody
    public String data() throws IOException {
        String json = IOUtils.convertStreamToString(roleData.getInputStream());
        HashMap<String, Object> corpTemplates = (HashMap<String, Object>) JsonUtils.json2map(json);
        return json;
    }

    @RequestMapping(value = "/getLogList")
    @ResponseBody
    public Object getLogList(HttpServletRequest request)
    {
        int pageSize = 10;
//        try {
//            pageSize =  Integer.parseInt(request.getParameter("pageSize"));
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }

//        int pageNumber=0 ;
//        try {
//            pageNumber =  Integer.parseInt(request.getParameter("pageNumber"))-1;
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }

        Map<String, Object> map = new HashMap<>();

//        String searchText=request.getParameter("searchText")==null ? "": request.getParameter("searchText");
//
//        String sortName=request.getParameter("sortName")==null ? "roleId": request.getParameter("sortName");
//        String sortOrder=request.getParameter("sortOrder")==null ? "asc": request.getParameter("sortOrder");


//        List<SysLog> sysLogPage =  logService.findAll(searchText);
        List<SysLog>  sysLogs= new ArrayList<SysLog>();
        SysLog sysLog1 = new SysLog();
        sysLog1.setAction("GET");
        sysLog1.setEvent("event");
        sysLog1.setHost("localhost");
        sysLog1.setInsertTime(LocalDateTime.now());
        sysLog1.setLogId(1l);
        sysLog1.setUserName("dyp");

        SysLog sysLog2 = new SysLog();
        sysLog2.setAction("GET");
        sysLog2.setEvent("event2");
        sysLog2.setHost("localhost2");
        sysLog2.setInsertTime(LocalDateTime.now());
        sysLog2.setLogId(2l);
        sysLog2.setUserName("dyp2");
        sysLogs.add(sysLog1);
        sysLogs.add(sysLog2);
        map.put("total",sysLogs.size());
        map.put("rows",sysLogs);

        return map;
    }

    @RequestMapping(value="/list")
    public String list()
    {
        return "/sys/logList";
    }

    @RequestMapping(value="/tree")
    public String table()
    {
        return "/sys/menueList";
    }
}
