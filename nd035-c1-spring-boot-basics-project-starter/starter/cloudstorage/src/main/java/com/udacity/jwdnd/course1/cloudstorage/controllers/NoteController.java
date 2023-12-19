package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entities.NoteEntity;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private UserService userService;
    @Autowired
    private NoteService noteService;

    @PostMapping
    public String createNote(Model model, NoteEntity note){
        if(note.getNoteId() != null && note.getNoteId() >=0){
            return "forward:/note/update";
        }
        int LoginUser = userService.getId(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );
        try {
            noteService.createNote(note, LoginUser);
//            model.addAttribute("success", true);
            return "redirect:/result?success=true";

        }catch (Exception e){
//            model.addAttribute("success", false);
            return "redirect:/result?success=false";

        }


//        return "redirect:/result";
    }
    @PostMapping("/update")
    public String updateNote(Model model, NoteEntity note){
        int LoginUser = userService.getId(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );
        int noteUser = noteService.getNoteById(note.getNoteId()).getUserId();
        if(LoginUser != noteUser){
//            model.addAttribute("success", false);
            return "redirect:/result?success=false";
        }
        note.setUserId(noteUser);
        noteService.updateNote(note);
//        model.addAttribute("success", true);
        return "redirect:/result?success=true";
    }



    @GetMapping
    public String deleteNote(@RequestParam("noteId") Integer noteId,Model model){
        int LoginUser = userService.getId(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );
        NoteEntity note =noteService.getNoteById(noteId);
        if(LoginUser != note.getUserId()){
            model.addAttribute("success", false);
            return "redirect:/result?success=false";
        }
        noteService.deleteNote(noteId);
        model.addAttribute("success", true);
        return "redirect:/result?success=true";
    }
}
