package com.dyp.dashboard.module.sys.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleSettingRequest {
    private Integer userId;
    private String roles;
}
