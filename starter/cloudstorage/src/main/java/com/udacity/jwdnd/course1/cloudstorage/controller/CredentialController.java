package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService,EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId, Authentication authentication){

        Boolean isDeleted = credentialService.delete(credentialId, authentication);

        if (isDeleted) {
            return "redirect:/result?success&msg=Delete credential successfully";
        }

        return "redirect:/result?error&msg=Delete failed. Please try again";
    }

    @PostMapping("/add")
    public String addCredential(@ModelAttribute Credential credential, Authentication authentication){
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[16];
        secureRandom.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
        Boolean status;

        if (Objects.isNull(credential.getCredentialId())) {
            status = credentialService.insert(credential, authentication);
        } else {
            status = credentialService.update(credential, authentication);
        }

        if (status) {
            return "redirect:/result?success&msg=Save credential successfully";
        }

        return "redirect:/result?error&msg=Save credential failed. Please try again";
    }
}