package com.example.springshiro.service;

import com.example.springshiro.entity.SysLog;

import java.util.List;

/**
 * @Author: xu.dm
 * @Date: 2018/10/11 0:01
 * @Description:
 */
public interface LogService {
    void writeLog(SysLog sysLog);
    List<SysLog> findAll(/*Pageable pageable*/);
    List<SysLog> findAllByUserNameContains(String userName/*, Pageable pageable*/);
    List<SysLog> findAll(String searchText/*, Pageable pageable*/);
}
