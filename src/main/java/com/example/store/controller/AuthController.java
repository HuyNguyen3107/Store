package com.example.store.controller;

import com.example.store.model.*;
import com.example.store.service.*;
import com.example.store.helper.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.example.store.dto.AuthDTO;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@Valid @RequestBody AuthDTO AuthDTO) {
        Optional<User> user = authService.login(AuthDTO.getEmail(), AuthDTO.getPassword());
        if (user.isPresent()) {
            String token = TokenHelper.generateToken();
            userService.saveToken(user.get().getId(), token);
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(401).build(); 
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        token = token.substring(7); 
        Optional<User> user = userService.getUserByToken(token);
        if (user.isEmpty()) {
            return ResponseEntity.status(401).build(); 
        }
        if (TokenHelper.validateToken(user.get().getToken() ,token)) {
            authService.saveToken(user.get().getId(), null); 
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(401).build(); 
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody String email) {
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().build(); 
        }
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()) {
            String token = TokenHelper.generateToken();
            userService.saveToken(user.get().getId(), token);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).build(); 
        }
    }

}

 