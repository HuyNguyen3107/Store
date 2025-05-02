package com.example.store.service;

import com.example.store.model.StudentClassroom;
import com.example.store.repository.StudentClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.store.dto.*;
import java.util.*;
import com.example.store.helper.GeneratePwHelper;
import com.example.store.util.*;

@Service
public class StudentClassroomService {
    private final StudentClassroomRepository studentClassroomRepository;

    @Autowired
    public StudentClassroomService(StudentClassroomRepository studentClassroomRepository) {
        this.studentClassroomRepository = studentClassroomRepository;
    }

    public String addStudentsToClassroom(SCRequestDTO scRequestDTO) {
        List<StudentClassroom> studentClassrooms = new ArrayList<>();
        for (String studentId : scRequestDTO.getStudentIds()) {
            StudentClassroom studentClassroom = new StudentClassroom();
            studentClassroom.setStudentId(Integer.parseInt(studentId));
            studentClassroom.setClassId(Integer.parseInt(scRequestDTO.getClassroomId()));
            studentClassrooms.add(studentClassroom);
        }
        studentClassroomRepository.saveAll(studentClassrooms);
        return "Students added to classroom successfully";
    }

    public String removeStudentsFromClassroom(SCRequestDTO scRequestDTO) {
        List<StudentClassroom> studentClassrooms = new ArrayList<>();
        if (scRequestDTO.getDeleteIds() == null || scRequestDTO.getDeleteIds().isEmpty()) {
            return "No students to remove from classroom";
        }
        for (String deleteId : scRequestDTO.getDeleteIds()) {
            StudentClassroom studentClassroom = studentClassroomRepository.findById(Integer.parseInt(deleteId)).orElse(null);
            if (studentClassroom != null) {
                studentClassrooms.add(studentClassroom);
            }
        }
        studentClassroomRepository.deleteAll(studentClassrooms);
        return "Students removed from classroom successfully";
    }

    public String deleteAllByClassId(int classId) {
        studentClassroomRepository.deleteAllByClassId(classId);
        return "All students removed from classroom successfully";
    }
}
