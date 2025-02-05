package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, UserService userService, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    public boolean delete(Integer credentialId, Authentication auth) {
        User user = userService.selectByName((String) auth.getPrincipal());
        return credentialMapper.deleteCredential(credentialId, user.getUserId());
    }

    public Boolean insert(Credential credential, Authentication auth) {
        User user = userService.selectByName(auth.getName());
        credential.setUserId(user.getUserId());
        return credentialMapper.insert(credential) == 1;
    }

    public Boolean update(Credential credential, Authentication auth) {
        User user = userService.selectByName(auth.getName());
        credential.setUserId(user.getUserId());
        return credentialMapper.update(credential) == 1;
    }

    public List<Credential> get(Authentication authentication) {
        User user = userService.selectByName(authentication.getName());
        List<Credential> credentials =credentialMapper.selectByUser(user);
        for (int i = 0; i < credentials.size(); i++) {
            credentials.get(i).setDecryptPassword(encryptionService.decryptValue(credentials.get(i).getPassword(), credentials.get(i).getKey()));
        }
        return credentials;
    }
}
