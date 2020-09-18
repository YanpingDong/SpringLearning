package com.dyp.dashboard.module.cms.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CmsArticleData {
    private String id;
    private String title;
    private String description;
    private String createBy;
    private Date createDate;
    private String updateBy;
    private Date updateDate;
    private String content;
    private String copyFrom;
}
