package com.dyp.dashboard.module.sys.service;

import com.dyp.dashboard.module.sys.entity.SysLog;

import java.util.List;

public interface LogService {
    void writeLog(SysLog sysLog);
    List<SysLog> findAll(/*Pageable pageable*/);
    List<SysLog> findAllByUserNameContains(String userName/*, Pageable pageable*/);
    List<SysLog> findAll(String searchText/*, Pageable pageable*/);
}
