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
@RequestMapping("/students")
@Validated
public class StudentController {
    private final StudentService studentService;
    private final UserService userService;

    @Autowired
    public StudentController(StudentService studentService, UserService userService) {
        this.userService = userService;
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Integer id) {
        Student student = studentService.getStudentById(id);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        User user = userService.getUserById(Integer.parseInt(studentDTO.getUserId()));
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        String status = studentService.createStudent(studentDTO);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.badRequest().body("Failed to create student");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable Integer id, @Valid @RequestBody StudentDTO studentDTO) {
        Student existingStudent = studentService.getStudentById(id);
        User user = userService.getUserById(Integer.parseInt(studentDTO.getUserId()));
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        if (existingStudent == null) {
            return ResponseEntity.notFound().build();
        }
        String status = studentService.updateStudent(existingStudent, studentDTO);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.badRequest().body("Failed to update student");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Integer id) {
        Student existingStudent = studentService.getStudentById(id);
        if (existingStudent == null) {
            return ResponseEntity.notFound().build();
        }
        String status = studentService.deleteStudent(existingStudent);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.badRequest().body("Failed to delete student");
        }
    }
}
