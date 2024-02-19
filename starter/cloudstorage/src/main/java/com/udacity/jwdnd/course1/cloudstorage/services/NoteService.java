package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    NoteMapper noteMapper;
    @Autowired
    UserService userService;

    public Integer create(Note note,  Authentication authentication){
        User user = userService.selectByName(authentication.getName());
        note = new Note(null, note.getNoteTitle(), note.getNoteDescription(), user.getUserId());
        return noteMapper.insert(note);

    }

    public Integer update(Note note, Authentication authentication){
        User user = userService.selectByName(authentication.getName());
        note.setUserId(user.getUserId());
        return noteMapper.updateNote(note);
    }

    public boolean delete(Integer noteId, Authentication authentication){
        User user = userService.selectByName(authentication.getName());
        return noteMapper.deleteNote(noteId, user.getUserId());
    }

    public List<Note> get(Authentication authentication) {
        User user = userService.selectByName(authentication.getName());
        return noteMapper.selectByUser(user);
    }
}