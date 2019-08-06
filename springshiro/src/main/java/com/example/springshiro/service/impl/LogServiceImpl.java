package com.example.springshiro.service.impl;

import com.example.springshiro.entity.SysLog;
import com.example.springshiro.repository.SysLogRepository;
import com.example.springshiro.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xu.dm
 * @Date: 2018/10/11 18:42
 * @Description:
 */
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
