package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entities.CredentialEntity;
import com.udacity.jwdnd.course1.cloudstorage.entities.UserEntity;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    EncryptionService encryptionService;
    @Autowired
    FileService fileService;
    @Autowired
    UserService userService;
    @Autowired
    NoteService noteService;
    @Autowired
    CredentialService credentialService;

    @GetMapping
    public String getHome(Model model){

        int LoginUser = userService.getId(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );

        model.addAttribute("files",fileService.getAllFiles(LoginUser));
        model.addAttribute("notes", noteService.getAllnotes(LoginUser));
        List<CredentialEntity> credentials = credentialService.getAllcredentials((LoginUser));
        credentials.forEach(credential ->
                credential.setPassword(
                        encryptionService.decryptValue(credential.getPassword(),credential.getKey())
                )
        );
        model.addAttribute("credentials", credentials);

        return "home";
    }

}
