package com.udacity.jwdnd.course1.cloudstorage.entities;

import lombok.Data;

import java.sql.Blob;

@Data
public class FileEntity {
    private Integer fileId;

    private String fileName;

    private String contentType;

    private long fileSize;

    private Integer userId;

    private byte []fileData;

}
