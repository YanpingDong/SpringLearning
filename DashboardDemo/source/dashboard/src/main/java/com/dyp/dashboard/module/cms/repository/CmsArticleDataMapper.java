package com.dyp.dashboard.module.cms.repository;

import com.dyp.dashboard.module.cms.entity.CmsArticleData;

public interface CmsArticleDataMapper {
    int insert(CmsArticleData record);
    CmsArticleData selectById(String id);
}
