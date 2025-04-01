package com.example.store.service;

import com.example.store.model.Student;
import com.example.store.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.store.dto.*;
import java.util.*;
import com.example.store.helper.GeneratePwHelper;
import com.example.store.util.*;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Integer id) {
        return studentRepository.findById(id).orElse(null);
    }

    public String createStudent(StudentDTO studentDTO) {
        Student student = new Student();
        student.setUserId(Integer.parseInt(studentDTO.getUserId()));
        student.setStatus(studentDTO.getStatus());
        Student savedStudent = studentRepository.save(student);
        return savedStudent != null ? "Student created successfully" : null;
    }

    public String updateStudent(Student existingStudent, StudentDTO studentDTO) {
        existingStudent.setUserId(Integer.parseInt(studentDTO.getUserId()));
        existingStudent.setStatus(studentDTO.getStatus());
        Student updatedStudent = studentRepository.save(existingStudent);
        return updatedStudent != null ? "Student updated successfully" : null;
    }

    public String deleteStudent(Student existingStudent) {
        try {
            studentRepository.delete(existingStudent);
            return "Student deleted successfully";
        } catch (Exception e) {
            return null;
        }
    }
}
