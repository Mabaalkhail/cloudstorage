package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entities.CredentialEntity;
import com.udacity.jwdnd.course1.cloudstorage.entities.CredentialEntity;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/credential")
public class CredentialController {
    @Autowired
    CredentialService credentialService;
    @Autowired
    UserService userService;

    @PostMapping("/upload")
    public String createCredential(Model model, CredentialEntity credential){
        if(credential.getCredentialId() != null && credential.getCredentialId() >=0){
            return "forward:/credential/update";
        }
        int LoginUser = userService.getId(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );
        try {
            credentialService.createCredential(credential, LoginUser);
            return "redirect:/result?success=true";

        }catch (Exception e){
            return "redirect:/result?success=false";

        }
        


    }

    @PostMapping("/update")
    public String updateCredential(Model model, CredentialEntity credential){
        int LoginUser = userService.getId(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );
        int credentialUser = credentialService.getCredentialById(credential.getCredentialId()).getUserId();
        if(LoginUser != credentialUser){
//            model.addAttribute("success", false);
            return "redirect:/result?success=false";
        }
        credential.setUserId(credentialUser);
        credentialService.updateCredential(credential);
//        model.addAttribute("success", true);
        return "redirect:/result?success=true";

    }
    

    @GetMapping()
    public String deleteCredential(@RequestParam("credentialId") Integer credentialId,Model model){
        int LoginUser = userService.getId(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );
        CredentialEntity credential =credentialService.getCredentialById(credentialId);
        if(LoginUser != credential.getUserId()){
            model.addAttribute("success", false);
            return "redirect:/result?success=false";
        }
        credentialService.deleteCredential(credentialId);
        return "redirect:/result?success=true";
    }
}
