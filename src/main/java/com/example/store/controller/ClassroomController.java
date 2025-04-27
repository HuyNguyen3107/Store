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
@RequestMapping("/classrooms")
@Validated
public class ClassroomController {
    private final ClassroomService classroomService;
    private final CourseService courseService;
    private final TeacherService teacherService;
    private final StudentClassroomService studentClassroomService;

    @Autowired
    public ClassroomController(ClassroomService classroomService, CourseService courseService, TeacherService teacherService, StudentClassroomService studentClassroomService) {
        this.studentClassroomService = studentClassroomService;
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.classroomService = classroomService;
    }

    @GetMapping
    public ResponseEntity<List<Classroom>> getAllClassrooms() {
        List<Classroom> classrooms = classroomService.getAllClassrooms();
        return ResponseEntity.ok(classrooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Classroom> getClassroomById(@PathVariable Integer id) {
        Classroom classroom = classroomService.getClassroomById(id);
        if (classroom != null) {
            return ResponseEntity.ok(classroom);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createClassroom(@Valid @RequestBody ClassroomDTO classroomDTO) {
       Teacher teacher = teacherService.getTeacherById(Integer.parseInt(classroomDTO.getTeacherId()));
        if (teacher == null) {
            return ResponseEntity.badRequest().body("Teacher not found");
        }

        Course course = courseService.getCourseById(Integer.parseInt(classroomDTO.getCourseId()));
        if (course == null) {
            return ResponseEntity.badRequest().body("Course not found");
        }
        String status = classroomService.createClassroom(classroomDTO);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.status(500).body("Error creating classroom");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateClassroom(@PathVariable Integer id, @Valid @RequestBody ClassroomDTO classroomDTO) {
        Classroom existingClassroom = classroomService.getClassroomById(id);
        if (existingClassroom == null) {
            return ResponseEntity.notFound().build();
        }

        Teacher teacher = teacherService.getTeacherById(Integer.parseInt(classroomDTO.getTeacherId()));
        if (teacher == null) {
            return ResponseEntity.badRequest().body("Teacher not found");
        }

        Course course = courseService.getCourseById(Integer.parseInt(classroomDTO.getCourseId()));
        if (course == null) {
            return ResponseEntity.badRequest().body("Course not found");
        }

        String status = classroomService.updateClassroom(existingClassroom, classroomDTO);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.status(500).body("Error updating classroom");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClassroom(@PathVariable Integer id) {
        Classroom classroom = classroomService.getClassroomById(id);
        if (classroom == null) {
            return ResponseEntity.notFound().build();
        }

        classroomService.deleteClassroom(classroom);
        return ResponseEntity.ok("Classroom deleted successfully");
    }

    @PostMapping("/add-students")
    public ResponseEntity<String> addStudentsToClassroom(@Valid @RequestBody SCRequestDTO requestDTO) {
        String status = studentClassroomService.addStudentsToClassroom(requestDTO);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.status(500).body("Error adding students to classroom");
        }
    }

    @PostMapping("/remove-students")
    public ResponseEntity<String> removeStudentsFromClassroom(@Valid @RequestBody SCRequestDTO requestDTO) {
        String status = studentClassroomService.removeStudentsFromClassroom(requestDTO);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.status(500).body("Error removing students from classroom");
        }
    }
}
