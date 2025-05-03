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
@RequestMapping("/answers")
@Validated
public class AnswerController {
    private final AnswerService answerService;
    private final HomeworkService homeworkService;
    private final StudentService studentService;
    private final UserService userService;

    @Autowired
    public AnswerController(AnswerService answerService, HomeworkService homeworkService, StudentService studentService, UserService userService) {
        this.userService = userService;
        this.answerService = answerService;
        this.homeworkService = homeworkService;
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Answer>> getAllAnswers(@RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "answers.view");
        if (!hasPermission) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        List<Answer> answers = answerService.getAllAnswers();
        return ResponseEntity.ok(answers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Answer> getAnswerById(@PathVariable Integer id, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "answers.view");
        if (!hasPermission) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        Answer answer = answerService.getAnswerById(id);
        if (answer != null) {
            return ResponseEntity.ok(answer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createAnswer(@Valid @RequestBody AnswerDTO answerDTO, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "answers.create");
        if (!hasPermission) {
            return ResponseEntity.status(403).body("Forbidden"); // Forbidden
        }
        Homework homework = homeworkService.getHomeworkById(Integer.parseInt(answerDTO.getHomeworkId()));
        if (homework == null) {
            return ResponseEntity.badRequest().body("Homework not found");
        }

        User userExist = userService.getUserById(Integer.parseInt(answerDTO.getUserId()));
        if (userExist == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        String status = answerService.createAnswer(answerDTO);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.badRequest().body("Failed to create answer");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAnswer(@PathVariable Integer id, @Valid @RequestBody AnswerDTO answerDTO, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "answers.update");
        if (!hasPermission) {
            return ResponseEntity.status(403).body("Forbidden"); // Forbidden
        }
        Answer existingAnswer = answerService.getAnswerById(id);
        if (existingAnswer == null) {
            return ResponseEntity.notFound().build();
        }

        Homework homework = homeworkService.getHomeworkById(Integer.parseInt(answerDTO.getHomeworkId()));
        if (homework == null) {
            return ResponseEntity.badRequest().body("Homework not found");
        }

        User userExist = userService.getUserById(Integer.parseInt(answerDTO.getUserId()));
        if (userExist == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        String status = answerService.updateAnswer(existingAnswer, answerDTO);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.badRequest().body("Failed to update answer");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnswer(@PathVariable Integer id, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "answers.delete");
        if (!hasPermission) {
            return ResponseEntity.status(403).body("Forbidden"); // Forbidden
        }
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
