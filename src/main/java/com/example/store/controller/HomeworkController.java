package com.example.store.controller;

import com.example.store.model.*;
import com.example.store.service.*;
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

    @Autowired
    public HomeworkController(HomeworkService homeworkService, ClassroomService classroomService) {
        this.homeworkService = homeworkService;
        this.classroomService = classroomService;
    }

    @GetMapping
    public ResponseEntity<List<Homework>> getAllHomeworks() {
        List<Homework> homeworks = homeworkService.getAllHomeworks();
        return ResponseEntity.ok(homeworks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Homework> getHomeworkById(@PathVariable Integer id) {
        Homework homework = homeworkService.getHomeworkById(id);
        if (homework != null) {
            return ResponseEntity.ok(homework);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createHomework(@Valid @RequestBody HomeworkDTO homeworkDTO) {
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
    public ResponseEntity<String> updateHomework(@PathVariable Integer id, @Valid @RequestBody HomeworkDTO homeworkDTO) {
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
    public ResponseEntity<String> deleteHomework(@PathVariable Integer id) {
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
