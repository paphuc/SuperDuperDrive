package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public String insert(@RequestParam("fileUpload") MultipartFile multipartFile, Authentication authentication) {
        if (multipartFile.isEmpty()) {
            return "redirect:/result?error&msg=Empty file.Please try again";
        }
        boolean status = fileService.create(multipartFile, authentication);

        if (status) {
            return "redirect:/result?success&msg=Upload file successfully";
        } else {
            return "redirect:/result?error&msg=Uploading file failed. Please try again";
        }

    }

    @GetMapping("/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Integer fileId, Authentication authentication) {
        File file = fileService.get(fileId, authentication);
        if(file != null) {
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                    .body(file.getFileData());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/delete/{fileId}")
    public String delete(@PathVariable Integer fileId, Authentication authentication) {
        Boolean status = fileService.delete(fileId, authentication);

        if (status) {
            return "redirect:/result?success&msg=Delete file successfully";
        } else {
            return "redirect:/result?error&msg=Deleting file failed";
        }
    }


}