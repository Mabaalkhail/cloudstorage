package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entities.NoteEntity;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    @Autowired
    NoteMapper noteMapper;

    public List<NoteEntity> getAllnotes(int userId){
        return noteMapper.getAllNotes(userId);
    }

    public void createNote(NoteEntity note, int userId){
        note.setUserId(userId);
        noteMapper.createNote(note); }

    public NoteEntity getNoteById(Integer noteId) {
       return noteMapper.getNoteById(noteId);
    }

    public void deleteNote(Integer noteId) {
        noteMapper.deleteNote(noteId);
    }

    public void updateNote(NoteEntity note) {
        noteMapper.updateNote(note);
    }
}
