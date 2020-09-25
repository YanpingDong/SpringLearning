package com.dyp.dashboard.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActionResultResponse {
    private Integer status;
    private String msg;
    private String url;
}
