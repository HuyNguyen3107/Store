package com.example.store.service;

import com.example.store.model.User;
import com.example.store.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.store.dto.AuthDTO;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    
    public void saveToken(int userId, String token) {
        User user = userRepository.findById(userId);
        if (user != null) {
            User existingUser = user;
            existingUser.setToken(token);
            userRepository.save(existingUser);
        }
    }
}
