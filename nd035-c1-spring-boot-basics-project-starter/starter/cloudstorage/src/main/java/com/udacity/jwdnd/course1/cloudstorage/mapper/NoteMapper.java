package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entities.NoteEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<NoteEntity> getAllNotes(int userId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    void createNote(NoteEntity note);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
    NoteEntity getNoteById(int noteId);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}")
    void updateNote(NoteEntity note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    void deleteNote(Integer noteId);
}
