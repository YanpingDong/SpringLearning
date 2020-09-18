package com.tra.repository;



import com.tra.entity.PhotoEntity;
import org.apache.ibatis.annotations.Param;

public interface IPhoto {

    public void createPhoto(PhotoEntity entity);

    public int deletePhotoByPhotoId(String photoId);

    public int updatePhotoDate(@Param("photoId") String photoId, @Param("photoDate") byte[] photoDate);

    public PhotoEntity getPhotoEntityByPhotoId(String photoId);

}
