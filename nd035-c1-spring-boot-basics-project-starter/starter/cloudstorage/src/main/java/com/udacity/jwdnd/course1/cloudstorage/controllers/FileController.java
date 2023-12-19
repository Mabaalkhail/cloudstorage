package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entities.FileEntity;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;

    @PostMapping("/upload")
    public String uploadFile
            (@RequestParam("FileForm") MultipartFile file, Model model) {

        if (file.isEmpty()) {
            model.addAttribute("success",
                    false);
            return "redirect:/result?success=false";
        }

        try {
            int LoginUser = userService.getId(
                    SecurityContextHolder.getContext()
                            .getAuthentication()
                            .getName()
            );
            FileEntity file1 = new FileEntity();
            file1.setFileName(file.getOriginalFilename());
            file1.setFileData(file.getBytes());
            file1.setUserId(LoginUser);
            file1.setFileSize(file.getSize());

            fileService.uploadFile(file1);
        } catch (Exception e) {
//            model.addAttribute("success", false);
            return "redirect:/result?success=false";
        }
//        model.addAttribute("success", true);
        return "redirect:/result?success=true";
    }


    @GetMapping
    @ResponseBody
        public ResponseEntity<byte[]> downloadFile(@RequestParam("fileId") Integer fileId,Model model) {
            FileEntity file = fileService.getFileByID(fileId);

            int LoginUser = userService.getId(
                    SecurityContextHolder.getContext()
                            .getAuthentication()
                            .getName()
            );
            if(LoginUser != file.getUserId()){
                model.addAttribute("success", false);
                return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
            }

            HttpHeaders headers = new HttpHeaders();
            // Set headers
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", file.getFileName());

            return new ResponseEntity<>(file.getFileData(), headers, HttpStatus.OK);
        }


    @GetMapping("/delete")
    public String deleteFile(@RequestParam("fileId") Integer fileId,Model model){
        try {
            fileService.deleteFile(fileId);
            model.addAttribute("success", true);
            return "redirect:/result?success=true";



        }catch (Exception e){
            model.addAttribute("success", false);
            return "redirect:/result?success=false";

        }

    }
}
