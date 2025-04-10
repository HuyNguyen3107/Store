package com.example.store.service;

import com.example.store.model.Classroom;
import com.example.store.repository.ClassroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.store.dto.*;
import java.util.*;
import com.example.store.helper.GeneratePwHelper;
import com.example.store.util.*;

@Service
public class ClassroomService {
    private final ClassroomRepository classroomRepository;

    @Autowired
    public ClassroomService(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findAll();
    }

    public Classroom getClassroomById(Integer id) {
        return classroomRepository.findById(id).orElse(null);
    }

    public String createClassroom(ClassroomDTO classroomDTO) {
        Classroom classroom = new Classroom();
        classroom.setName(classroomDTO.getName());
        classroom.setCourseId(Integer.parseInt(classroomDTO.getCourseId()));
        classroom.setTeacherId(Integer.parseInt(classroomDTO.getTeacherId()));
        classroom.setStartDay(classroomDTO.getStartDay());
        classroom.setEndDay(classroomDTO.getEndDay());
        classroom.setSchedule(classroomDTO.getSchedule());
        classroom.setStudentNumber(Integer.parseInt(classroomDTO.getStudentNumber()));

        Classroom savedClassroom = classroomRepository.save(classroom);
        return savedClassroom != null ? "Classroom created successfully" : null;
    }

    public String updateClassroom(Classroom existingClassroom, ClassroomDTO classroomDTO) {
        existingClassroom.setName(classroomDTO.getName());
        existingClassroom.setCourseId(Integer.parseInt(classroomDTO.getCourseId()));
        existingClassroom.setTeacherId(Integer.parseInt(classroomDTO.getTeacherId()));
        existingClassroom.setStartDay(classroomDTO.getStartDay());
        existingClassroom.setEndDay(classroomDTO.getEndDay());
        existingClassroom.setSchedule(classroomDTO.getSchedule());
        existingClassroom.setStudentNumber(Integer.parseInt(classroomDTO.getStudentNumber()));

        Classroom updatedClassroom = classroomRepository.save(existingClassroom);
        return updatedClassroom != null ? "Classroom updated successfully" : null;
    }

    public String deleteClassroom(Classroom existingClassroom) {
        classroomRepository.delete(existingClassroom);
        return "Classroom deleted successfully";
    }
}
