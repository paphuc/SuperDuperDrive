package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private final FileMapper fileMapper;
    @Autowired
    private final UserService userService;
    public FileService(FileMapper noteMapper, UserService userService) {
        this.fileMapper = noteMapper;
        this.userService = userService;
    }

    public Boolean create(MultipartFile multipartFile, Authentication authentication) {
        User user = userService.selectByName(authentication.getName());
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        File file = null;
        try {
            file = new File(null, fileName, multipartFile.getContentType(), String.valueOf(multipartFile.getSize()), user.getUserId(), multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (fileMapper.select(file).isEmpty())
            return fileMapper.insert(file) == 1;
        else
            return false;
    }

    public Boolean delete(Integer fileId,  Authentication authentication){
        User user = userService.selectByName(authentication.getName());
        return this.fileMapper.deleteFile(fileId, user.getUserId());
    }

    public List<File> get(Authentication authentication){
        User user = userService.selectByName(authentication.getName());
        return this.fileMapper.selectByUser(user);
    }

    public File get(Integer fileId, Authentication authentication){
        User user = userService.selectByName(authentication.getName());
        return this.fileMapper.selectById(fileId, user.getUserId());
    }
}