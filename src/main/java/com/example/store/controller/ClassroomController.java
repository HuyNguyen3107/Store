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
@RequestMapping("/classrooms")
@Validated
public class ClassroomController {
    private final ClassroomService classroomService;
    private final CourseService courseService;
    private final TeacherService teacherService;
    private final StudentClassroomService studentClassroomService;
    private final UserService userService;

    @Autowired
    public ClassroomController(ClassroomService classroomService, CourseService courseService, TeacherService teacherService, StudentClassroomService studentClassroomService, UserService userService) {
        this.userService = userService;
        this.studentClassroomService = studentClassroomService;
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.classroomService = classroomService;
    }

    @GetMapping
    public ResponseEntity<List<Classroom>> getAllClassrooms(@RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "classrooms.view");
        if (!hasPermission) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        List<Classroom> classrooms = classroomService.getAllClassrooms();
        return ResponseEntity.ok(classrooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Classroom> getClassroomById(@PathVariable Integer id, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "classrooms.view");
        if (!hasPermission) {
            return ResponseEntity.status(403).body(null); // Forbidden
        }
        Classroom classroom = classroomService.getClassroomById(id);
        if (classroom != null) {
            return ResponseEntity.ok(classroom);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createClassroom(@Valid @RequestBody ClassroomDTO classroomDTO, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "classrooms.create");
        if (!hasPermission) {
            return ResponseEntity.status(403).body("Forbidden"); // Forbidden
        }
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
    public ResponseEntity<String> updateClassroom(@PathVariable Integer id, @Valid @RequestBody ClassroomDTO classroomDTO, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "classrooms.update");
        if (!hasPermission) {
            return ResponseEntity.status(403).body("Forbidden"); // Forbidden
        }
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
    public ResponseEntity<String> deleteClassroom(@PathVariable Integer id, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "classrooms.delete");
        if (!hasPermission) {
            return ResponseEntity.status(403).body("Forbidden"); // Forbidden
        }
        String status = studentClassroomService.deleteAllByClassId(id);
        if (status == null) {
            return ResponseEntity.status(500).body("Error deleting students from classroom");
        }
        Classroom classroom = classroomService.getClassroomById(id);
        if (classroom == null) {
            return ResponseEntity.notFound().build();
        }

        classroomService.deleteClassroom(classroom);
        return ResponseEntity.ok("Classroom deleted successfully");
    }

    @PostMapping("/add-students")
    public ResponseEntity<String> addStudentsToClassroom(@Valid @RequestBody SCRequestDTO requestDTO, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "classrooms.add_students");
        if (!hasPermission) {
            return ResponseEntity.status(403).body("Forbidden"); // Forbidden
        }
        String status = studentClassroomService.addStudentsToClassroom(requestDTO);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.status(500).body("Error adding students to classroom");
        }
    }

    @PostMapping("/remove-students")
    public ResponseEntity<String> removeStudentsFromClassroom(@Valid @RequestBody SCRequestDTO requestDTO, @RequestHeader("Email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        boolean hasPermission = ValidPermission.hasPermission(user, "classrooms.remove_students");
        if (!hasPermission) {
            return ResponseEntity.status(403).body("Forbidden"); // Forbidden
        }
        String status = studentClassroomService.removeStudentsFromClassroom(requestDTO);
        if (status != null) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.status(500).body("Error removing students from classroom");
        }
    }
}
