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
@RequestMapping("/answers")
@Validated
public class AnswerController {
    private final AnswerService answerService;
    private final HomeworkService homeworkService;
    private final StudentService studentService;

    @Autowired
    public AnswerController(AnswerService answerService, HomeworkService homeworkService, StudentService studentService) {
        this.answerService = answerService;
        this.homeworkService = homeworkService;
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Answer>> getAllAnswers() {
        List<Answer> answers = answerService.getAllAnswers();
        return ResponseEntity.ok(answers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Answer> getAnswerById(@PathVariable Integer id) {
        Answer answer = answerService.getAnswerById(id);
        if (answer != null) {
            return ResponseEntity.ok(answer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createAnswer(@Valid @RequestBody AnswerDTO answerDTO) {
        Homework homework = homeworkService.getHomeworkById(Integer.parseInt(answerDTO.getHomeworkId()));
        if (homework == null) {
            return ResponseEntity.badRequest().body("Homework not found");
        }

        Student student = studentService.getStudentById(Integer.parseInt(answerDTO.getStudentId()));
        if (student == null) {
            return ResponseEntity.badRequest().body("Student not found");
        }

        String status = answerService.createAnswer(answerDTO);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.badRequest().body("Failed to create answer");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAnswer(@PathVariable Integer id, @Valid @RequestBody AnswerDTO answerDTO) {
        Answer existingAnswer = answerService.getAnswerById(id);
        if (existingAnswer == null) {
            return ResponseEntity.notFound().build();
        }

        Homework homework = homeworkService.getHomeworkById(Integer.parseInt(answerDTO.getHomeworkId()));
        if (homework == null) {
            return ResponseEntity.badRequest().body("Homework not found");
        }

        Student student = studentService.getStudentById(Integer.parseInt(answerDTO.getStudentId()));
        if (student == null) {
            return ResponseEntity.badRequest().body("Student not found");
        }

        String status = answerService.updateAnswer(existingAnswer, answerDTO);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.badRequest().body("Failed to update answer");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnswer(@PathVariable Integer id) {
        Answer existingAnswer = answerService.getAnswerById(id);
        if (existingAnswer == null) {
            return ResponseEntity.notFound().build();
        }

        String status = answerService.deleteAnswer(existingAnswer);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.badRequest().body("Failed to delete answer");
        }
    }
}
