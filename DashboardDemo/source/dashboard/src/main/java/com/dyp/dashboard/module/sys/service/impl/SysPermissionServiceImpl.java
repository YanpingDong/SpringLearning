package com.dyp.dashboard.module.sys.service.impl;

import com.dyp.dashboard.module.sys.entity.SysPermission;
import com.dyp.dashboard.module.sys.repository.SysPermissionMapper;
import com.dyp.dashboard.module.sys.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionDao;

    @Override
    public List<SysPermission> getAllPermission() {
        return sysPermissionDao.selectAll();
    }
}
