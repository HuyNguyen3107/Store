package com.example.store.controller;

import com.example.store.model.*;
import com.example.store.service.*;
import com.example.store.helper.*;
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
    private final ClassroomService classroomService;

    @Autowired
    public TeacherController(TeacherService teacherService, UserService userService, ClassroomService classroomService) {
        this.classroomService = classroomService;
        this.userService = userService;
        this.teacherService = teacherService;
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers(@RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "teachers.view");
        if (!hasPermission) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        List<Teacher> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Integer id, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "teachers.view");
        if (!hasPermission) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        Teacher teacher = teacherService.getTeacherById(id);
        if (teacher != null) {
            return ResponseEntity.ok(teacher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createTeacher(@Valid @RequestBody TeacherDTO teacherDTO, @RequestHeader("Email") String email) {
        User userCheck = userService.getUserByEmail(email);
        if (userCheck == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        boolean hasPermission = ValidPermission.hasPermission(userCheck, "teachers.create");
        if (!hasPermission) {
            return ResponseEntity.status(403).body("Forbidden"); // Forbidden
        }
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
    public ResponseEntity<String> updateTeacher(@PathVariable Integer id, @Valid @RequestBody TeacherDTO teacherDTO, @RequestHeader("Email") String email) {
        User userCheck = userService.getUserByEmail(email);
        if (userCheck == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        boolean hasPermission = ValidPermission.hasPermission(userCheck, "teachers.update");
        if (!hasPermission) {
            return ResponseEntity.status(403).body("Forbidden"); // Forbidden
        }
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
    public ResponseEntity<String> deleteTeacher(@PathVariable Integer id, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "teachers.delete");
        if (!hasPermission) {
            return ResponseEntity.status(403).body("Forbidden"); // Forbidden
        }
        Teacher existingTeacher = teacherService.getTeacherById(id);
        if (existingTeacher == null) {
            return ResponseEntity.notFound().build();
        }
        List<Classroom> classrooms = existingTeacher.getClassrooms();
        int[] classroomIds = new int[classrooms.size()];
        for (int i = 0; i < classrooms.size(); i++) {
            classroomIds[i] = classrooms.get(i).getId();
        }
        classroomService.updateTeacherIdToNull(classroomIds);
        Teacher teacher = teacherService.getTeacherById(id);
        String status = teacherService.deleteTeacher(teacher);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.badRequest().body("Failed to delete teacher");
        }
    }
}
