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

    public int countSpecifiedCharacter(String searchStr, String keyStr){
        int count=0;
        int i=0;
        while(searchStr.indexOf(keyStr,i)>=0){
            count++;
            i=searchStr.indexOf(keyStr,i) + keyStr.length();
        }

        return count;
    }

    @Override
    public void createSysPermission(SysPermission record) {
        int parentId = record.getParentId();
        SysPermission parentsSysPermission = sysPermissionDao.selectById(parentId);
        String parentIds="";
        int level = 0;
        if(parentsSysPermission != null)
        {
            int parentLevel =  parentsSysPermission.getLevel();
            if(parentLevel > 1)
            {
                parentIds = parentsSysPermission.getParentIds();
                if(null !=  parentIds)
                {
                    parentIds = parentIds.concat("/").concat(String.valueOf(parentId));
                }
            }
            else
            {
                parentIds = String.valueOf(parentId);
            }
            level = parentLevel + 1;

            record.setParentIds(parentIds);
            record.setLevel(level);
        }
        else
        {
            record.setParentIds("0");
            record.setParentId(0);
            record.setUrl("#");
            record.setLevel(1);
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
