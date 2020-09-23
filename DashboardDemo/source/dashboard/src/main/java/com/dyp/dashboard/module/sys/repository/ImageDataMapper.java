package com.dyp.dashboard.module.sys.repository;

import com.dyp.dashboard.module.sys.entity.ImageData;

public interface ImageDataMapper {
    int insert(ImageData record);
    ImageData selectById(String id);
}
