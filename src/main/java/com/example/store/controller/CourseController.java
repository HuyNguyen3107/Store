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
@RequestMapping("/courses")
@Validated
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Integer id) {
        Course course = courseService.getCourseById(id);
        if (course != null) {
            return ResponseEntity.ok(course);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createCourse(@Valid @RequestBody CourseDTO courseDTO) {
        String name = courseDTO.getName();
        Course existingCourse = courseService.getCourseByName(name);
        if (existingCourse != null) {
            return ResponseEntity.status(409).body("Course with this name already exists");
        }
        String status = courseService.createCourse(courseDTO);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.status(500).body("Error creating course");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCourse(@PathVariable Integer id, @Valid @RequestBody CourseDTO courseDTO) {
        Course existingCourse = courseService.getCourseById(id);
        if (existingCourse == null) {
            return ResponseEntity.notFound().build();
        }
        String status = courseService.updateCourse(existingCourse, courseDTO);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.status(500).body("Error updating course");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Integer id) {
        Course existingCourse = courseService.getCourseById(id);
        if (existingCourse == null) {
            return ResponseEntity.notFound().build();
        }
        String status = courseService.deleteCourse(existingCourse);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.status(500).body("Error deleting course");
        }
    }
}
