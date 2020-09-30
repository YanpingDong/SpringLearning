package com.dyp.dashboard.module.sys.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionResponse {
    private int id;
    private int pid;
    private int status;
    private String name;
    private String permissionValue;
    private String url;
    private  Boolean check;
}
