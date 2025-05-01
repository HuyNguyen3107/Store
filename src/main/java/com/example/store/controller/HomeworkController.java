package com.example.store.controller;

import com.example.store.model.*;
import com.example.store.service.*;
import com.example.store.helper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.example.store.dto.*;
import jakarta.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/homeworks")
@Validated
public class HomeworkController {
    private final HomeworkService homeworkService;
    private final ClassroomService classroomService;
    private final UserService userService;

    @Autowired
    public HomeworkController(HomeworkService homeworkService, ClassroomService classroomService, UserService userService) {
        this.userService = userService;
        this.homeworkService = homeworkService;
        this.classroomService = classroomService;
    }

    @GetMapping
    public ResponseEntity<List<Homework>> getAllHomeworks(@RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "homeworks.view");
        if (!hasPermission) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        List<Homework> homeworks = homeworkService.getAllHomeworks();
        return ResponseEntity.ok(homeworks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Homework> getHomeworkById(@PathVariable Integer id, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "homeworks.view");
        if (!hasPermission) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        Homework homework = homeworkService.getHomeworkById(id);
        if (homework != null) {
            return ResponseEntity.ok(homework);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createHomework(@Valid @RequestBody HomeworkDTO homeworkDTO, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "homeworks.create");
        if (!hasPermission) {
            return ResponseEntity.status(403).body("Forbidden"); // Forbidden
        }
        Classroom classroom = classroomService.getClassroomById(Integer.parseInt(homeworkDTO.getClassId()));
        if (classroom == null) {
            return ResponseEntity.badRequest().body("Classroom not found");
        }

        String status = homeworkService.createHomework(homeworkDTO);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.badRequest().body("Failed to create homework");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateHomework(@PathVariable Integer id, @Valid @RequestBody HomeworkDTO homeworkDTO, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "homeworks.update");
        if (!hasPermission) {
            return ResponseEntity.status(403).body("Forbidden"); // Forbidden
        }
        Homework existingHomework = homeworkService.getHomeworkById(id);
        if (existingHomework == null) {
            return ResponseEntity.notFound().build();
        }

        Classroom classroom = classroomService.getClassroomById(Integer.parseInt(homeworkDTO.getClassId()));
        if (classroom == null) {
            return ResponseEntity.badRequest().body("Classroom not found");
        }

        String status = homeworkService.updateHomework(existingHomework, homeworkDTO);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.badRequest().body("Failed to update homework");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHomework(@PathVariable Integer id, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "homeworks.delete");
        if (!hasPermission) {
            return ResponseEntity.status(403).body("Forbidden"); // Forbidden
        }
        Homework existingHomework = homeworkService.getHomeworkById(id);
        if (existingHomework == null) {
            return ResponseEntity.notFound().build();
        }

        String status = homeworkService.deleteHomework(existingHomework);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.badRequest().body("Failed to delete homework");
        }
    }
}
