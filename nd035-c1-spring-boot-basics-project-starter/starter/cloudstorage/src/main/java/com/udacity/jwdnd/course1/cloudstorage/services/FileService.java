package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entities.FileEntity;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    @Autowired
    FileMapper mapper;
    public List<FileEntity> getAllFiles(int userId){
        return mapper.getAllFiles(userId);
    }

    public void uploadFile(FileEntity file) {
        List<FileEntity> duplicatedfiles = getAllFiles(file.getUserId());
        if (duplicatedfiles.stream().anyMatch(file1 -> file1.getFileName().equalsIgnoreCase(file.getFileName()))){
            throw new DuplicateKeyException("Duplicated file name");

        }
        mapper.insertFile(file);
    }
    public FileEntity getFileByID(Integer fileId){
        return mapper.getFileById(fileId);
    }

    public void deleteFile(Integer fileId) {
        mapper.deleteFile(fileId);
    }
}
