package com.example.springshiro.repository;

import com.example.springshiro.entity.SysLog;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xu.dm
 * @Date: 2018/10/11 18:43
 * @Description:
 */
@Repository
public class SysLogRepository {

    public void writeLog(SysLog sysLog){
        //save to db
    }
    public List<SysLog> findAll(){
//        PageImpl pageImpl = new PageImpl();
        return null;
    }
    public List<SysLog> findAllByUserNameContains(String userName){
        return null;
    }
    public List<SysLog> findAll(String searchText){
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

        return sysLogs;
    }
}
