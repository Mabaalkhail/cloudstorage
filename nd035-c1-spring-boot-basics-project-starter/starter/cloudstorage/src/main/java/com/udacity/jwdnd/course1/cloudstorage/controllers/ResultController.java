package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Controller
@RequestMapping("/result")
public class ResultController {
    @GetMapping
    public String getResult(@RequestParam("success") boolean success, Model model){
        model.addAttribute("success", success);
        return "result";
    }
}
