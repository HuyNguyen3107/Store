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
@RequestMapping("/courses")
@Validated
public class CourseController {
    private final CourseService courseService;
    private final UserService userService;
    private final ClassroomService classroomService;

    @Autowired
    public CourseController(CourseService courseService, UserService userService, ClassroomService classroomService) {
        this.classroomService = classroomService;
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses(@RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "courses.view");
        if (!hasPermission) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Integer id, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "courses.view");
        if (!hasPermission) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        Course course = courseService.getCourseById(id);
        if (course != null) {
            return ResponseEntity.ok(course);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createCourse(@Valid @RequestBody CourseDTO courseDTO, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "courses.create");
        if (!hasPermission) {
            return ResponseEntity.status(403).body("Forbidden"); // Forbidden
        }
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
    public ResponseEntity<String> updateCourse(@PathVariable Integer id, @Valid @RequestBody CourseDTO courseDTO, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "courses.update");
        if (!hasPermission) {
            return ResponseEntity.status(403).body("Forbidden"); // Forbidden
        }
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
    public ResponseEntity<String> deleteCourse(@PathVariable Integer id, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "courses.delete");
        if (!hasPermission) {
            return ResponseEntity.status(403).body("Forbidden"); // Forbidden
        }
        Course existingCourse = courseService.getCourseById(id);
        if (existingCourse == null) {
            return ResponseEntity.notFound().build();
        }
        List<Classroom> classrooms = existingCourse.getClassrooms();
        int[] classroomIds = new int[classrooms.size()];
        for (int i = 0; i < classrooms.size(); i++) {
            classroomIds[i] = classrooms.get(i).getId();
        }
        classroomService.updateCourseIdToNull(classroomIds);
        Course courseToDelete = courseService.getCourseById(id);
        String status = courseService.deleteCourse(courseToDelete);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.status(500).body("Error deleting course");
        }
    }
}
