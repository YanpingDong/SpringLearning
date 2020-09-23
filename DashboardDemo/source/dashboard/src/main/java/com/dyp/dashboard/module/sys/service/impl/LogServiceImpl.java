package com.dyp.dashboard.module.sys.service.impl;

import com.dyp.dashboard.module.sys.entity.SysLog;
import com.dyp.dashboard.module.sys.repository.SysLogRepository;
import com.dyp.dashboard.module.sys.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class LogServiceImpl implements LogService {

    @Resource
    SysLogRepository sysLogRepository;

    private Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);

    @Override
    @Async("logThread")
    public void writeLog(SysLog sysLog)
    {
        long start = System.currentTimeMillis();
        sysLogRepository.writeLog(sysLog);
        long end = System.currentTimeMillis();
        logger.info("异步日志入库完成，耗时："+(end-start)+"毫秒，入库内容："+sysLog);
    }

    @Override
    public List<SysLog> findAll() {
        return sysLogRepository.findAll();
    }

    @Override
    public List<SysLog> findAllByUserNameContains(String userName) {
        return sysLogRepository.findAllByUserNameContains(userName);
    }

    @Override
    public List<SysLog> findAll(String searchText) {
        return sysLogRepository.findAll(searchText);
    }
}
