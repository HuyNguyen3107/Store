package com.example.store.service;

import com.example.store.model.PasswordToken;
import com.example.store.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PasswordTokenService {
    private final PasswordTokenRepository passwordTokenRepository;

    @Autowired
    public PasswordTokenService(PasswordTokenRepository passwordTokenRepository) {
        this.passwordTokenRepository = passwordTokenRepository;
    }

    public void addToken(PasswordToken passwordToken) {
        passwordTokenRepository.save(passwordToken);
    }

    public void updateTokenByUserId(Integer userId, String token, String expirationTime) {
        PasswordToken passwordToken = passwordTokenRepository.findByUserId(userId);
        if (passwordToken != null) {
            passwordToken.setResetToken(token);
            passwordToken.setExpired(expirationTime);
            passwordTokenRepository.save(passwordToken);
        }
    }

    public PasswordToken getByUserIdAndToken(Integer userId, String token) {
        return passwordTokenRepository.findByUserIdAndResetToken(userId, token);
    }

    public PasswordToken findByUserId(Integer userId) {
        return passwordTokenRepository.findByUserId(userId);
    }
}
