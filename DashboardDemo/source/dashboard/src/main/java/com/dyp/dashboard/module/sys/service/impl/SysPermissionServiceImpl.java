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

    @Override
    public void createSysPermission(SysPermission record) {
        int parentId = record.getParentId();
        SysPermission parentsSysPermission = sysPermissionDao.selectById(parentId);
        String parentIds="";
        if(parentsSysPermission != null)
        {
            parentIds = parentsSysPermission.getParentIds();
            if(null !=  parentIds)
            {
                parentIds = parentIds.concat("/").concat(String.valueOf(parentId));
            }
            else
            {
                parentIds = String.valueOf(parentId);
            }

            record.setParentIds(parentIds);
        }
        sysPermissionDao.insert(record);
    }

    /**
     *
     * @param id
     * @return null if nothing can be found
     */
    @Override
    public SysPermission selectById(int id) {
        return sysPermissionDao.selectById(id);
    }

    @Override
    public int deleteById(int id) {
        return sysPermissionDao.deleteById(id);
    }

    @Override
    public int updateById(SysPermission record) {
        return sysPermissionDao.updateById(record);
    }


}
