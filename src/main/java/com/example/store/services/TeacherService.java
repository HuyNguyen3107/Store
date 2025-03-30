package com.example.store.service;

import com.example.store.model.Teacher;
import com.example.store.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.store.dto.*;
import java.util.*;
import com.example.store.helper.GeneratePwHelper;
import com.example.store.util.*;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher getTeacherById(Integer id) {
        return teacherRepository.findById(id).orElse(null);
    }

    public String createTeacher(TeacherDTO teacherDTO) {
        Teacher teacher = new Teacher();
        teacher.setUserId(Integer.parseInt(teacherDTO.getUserId()));
        teacher.setStatus(teacherDTO.getStatus());
        try {
            teacherRepository.save(teacher);
            return "Teacher created successfully";
        } catch (Exception e) {
            return null;
        }
    }

    public String updateTeacher(Teacher existingTeacher, TeacherDTO teacherDTO) {
        existingTeacher.setUserId(Integer.parseInt(teacherDTO.getUserId()));
        existingTeacher.setStatus(teacherDTO.getStatus());
        Teacher updatedTeacher = teacherRepository.save(existingTeacher);
        return updatedTeacher != null ? "Teacher updated successfully" : null;
    }

    public String deleteTeacher(Integer id) {
        try {
            teacherRepository.deleteById(id);
            return "Teacher deleted successfully";
        } catch (Exception e) {
            return null;
        }
    }
}
