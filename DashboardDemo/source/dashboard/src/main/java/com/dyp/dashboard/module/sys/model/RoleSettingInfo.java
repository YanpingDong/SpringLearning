package com.dyp.dashboard.module.sys.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleSettingInfo {
    private List<RoleInfo> roleInfos;
    private String defaultSelectedRole;
}
