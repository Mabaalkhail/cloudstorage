package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entities.FileEntity;
import org.apache.ibatis.annotations.*;

import java.io.File;
import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    void insertFile(FileEntity file);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<FileEntity> getAllFiles(int userId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    FileEntity getFileById(Integer fileId);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void deleteFile(Integer fileId);
}
