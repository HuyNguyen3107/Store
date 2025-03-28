package com.example.store.controller;

import com.example.store.model.User;
import com.example.store.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.example.store.dto.*;
import jakarta.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public UserController(UserService userService, EmailService emailService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
         List<User> users = userService.getAllUsers();
         return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserDTO userDTO) {
        String email = userDTO.getEmail();
        User existingUser = userService.getUserByEmail(email);
        if (existingUser != null) {
            return ResponseEntity.status(409).build(); 
        }
        String password = userService.createUser(userDTO);
        if (password != null) {
            emailService.sendEmail(userDTO.getEmail(), "Welcome to our system", 
                "Your password is: " + password);
            return ResponseEntity.status(201).build(); 
        } else {
            return ResponseEntity.badRequest().build(); 
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Integer id, @RequestBody UserInfoDTO userInfoDTO, @RequestHeader("Email") String email) {
        String status = userService.updateUser(id, userInfoDTO, email);
        if (status != null) {
            return ResponseEntity.ok(status); 
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id, @RequestHeader("Email") String email) {
        boolean deleted = userService.deleteUser(id, email);
        if (deleted) {
            return ResponseEntity.ok("User deleted successfully"); 
        } else {
            return ResponseEntity.notFound().build(); 
        }
    }
}
