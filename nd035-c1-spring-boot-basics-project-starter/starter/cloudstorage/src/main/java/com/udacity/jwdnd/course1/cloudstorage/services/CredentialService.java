package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entities.CredentialEntity;
import com.udacity.jwdnd.course1.cloudstorage.entities.CredentialEntity;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    CredentialMapper credentialMapper;

    public List<CredentialEntity> getAllcredentials(int userId){
        return credentialMapper.getAllCredentials(userId);
    }

    public void createCredential(CredentialEntity credential, int userId){
        credential.setUserId(userId);

        String key = RandomStringUtils.randomAlphabetic(16);
        credential.setKey(key);
        credential.setPassword(encryptionService.encryptValue(
                credential.getPassword(),
                credential.getKey()));
        credentialMapper.createCredential(credential); }

    public CredentialEntity getCredentialById(Integer credentialId) {
        return credentialMapper.getCredentialById(credentialId);
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }

    public void updateCredential(CredentialEntity credential) {
        String key = RandomStringUtils.randomAlphabetic(16);
        credential.setKey(key);
        credential.setPassword(encryptionService.encryptValue(
                credential.getPassword(),
                credential.getKey()));
        credentialMapper.updateCredential(credential);
    }
}

