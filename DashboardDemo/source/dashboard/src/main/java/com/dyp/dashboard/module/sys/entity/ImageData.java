package com.dyp.dashboard.module.sys.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ImageData implements Serializable {
    private String id;
    private String name;
    private String type;
    private byte[] data;
    private Date createDate;
}
