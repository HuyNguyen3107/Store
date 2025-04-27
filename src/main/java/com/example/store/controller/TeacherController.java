package com.example.store.controller;

import com.example.store.model.*;
import com.example.store.service.*;
import com.example.store.util.DatetimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.example.store.dto.*;
import jakarta.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/teachers")
@Validated
public class TeacherController {
    private final TeacherService teacherService;
    private final UserService userService;

    @Autowired
    public TeacherController(TeacherService teacherService, UserService userService) {
        this.userService = userService;
        this.teacherService = teacherService;
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Integer id) {
        Teacher teacher = teacherService.getTeacherById(id);
        if (teacher != null) {
            return ResponseEntity.ok(teacher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createTeacher(@Valid @RequestBody TeacherDTO teacherDTO) {
        int userId = Integer.parseInt(teacherDTO.getUserId());
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        String status = teacherService.createTeacher(teacherDTO);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.badRequest().body("Failed to create teacher");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTeacher(@PathVariable Integer id, @Valid @RequestBody TeacherDTO teacherDTO) {
        Teacher existingTeacher = teacherService.getTeacherById(id);
        User user = userService.getUserById(Integer.parseInt(teacherDTO.getUserId()));
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        if (existingTeacher == null) {
            return ResponseEntity.notFound().build();
        }
        String status = teacherService.updateTeacher(existingTeacher, teacherDTO);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.badRequest().body("Failed to update teacher");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Integer id) {
        Teacher existingTeacher = teacherService.getTeacherById(id);
        if (existingTeacher == null) {
            return ResponseEntity.notFound().build();
        }
        String status = teacherService.deleteTeacher(id);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.badRequest().body("Failed to delete teacher");
        }
    }
}
