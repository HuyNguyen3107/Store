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
    public ResponseEntity<AuthDTO> login(@Valid @RequestBody AuthDTO AuthDTO) {
        Optional<AuthDTO> user = authService.login(AuthDTO.getEmail(), AuthDTO.getPassword());
        System.out.println(user);
        // if (user.isPresent()) {
        //     String token = TokenHelper.generateToken(user.get().getEmail(), user.get().getPassword());
        //     user.get().setToken(token);
        //     userService.saveToken(user.get().getId(), token);
        //     return ResponseEntity.ok(user.get());
        // } else {
        //     return ResponseEntity.status(401).build(); // Unauthorized
        // }
    }
}

