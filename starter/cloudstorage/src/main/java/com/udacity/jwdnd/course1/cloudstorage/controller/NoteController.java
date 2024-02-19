package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/add")
    public String insert(@ModelAttribute Note note, Authentication authentication) {

        String response;
        if (Objects.isNull(note.getNoteId())) {
            Integer noteId = noteService.create(note, authentication);
            if (noteId == 0) {
                response = "redirect:/result?error&msg=Creating note failed. Please try again";
            } else {
                response = "redirect:/result?success&msg=Create note successfully";
            }
        } else {
            Integer noteId = noteService.update(note, authentication);
            if (noteId == 0) {
                response = "redirect:/result?error&msg=Updating note failed. Please try again";
            } else {
                response =  "redirect:/result?success&msg=Update note successfully";
            }
        }

        return response;
    }

    @GetMapping("/delete/{noteId}")
    public String delete(@PathVariable Integer noteId, Authentication authentication) {
        if (noteService.delete(noteId, authentication)) {
            return "redirect:/result?error&msg=Deleting note failed. Please try again";
        }
        return "redirect:/result?success&msg=Delete note successfully";
    }
}