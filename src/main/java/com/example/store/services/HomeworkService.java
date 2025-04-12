package com.example.store.service;

import com.example.store.model.Homework;
import com.example.store.repository.HomeworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.store.dto.*;
import java.util.*;
import com.example.store.helper.GeneratePwHelper;
import com.example.store.util.*;

@Service
public class HomeworkService {
    private final HomeworkRepository homeworkRepository;

    @Autowired
    public HomeworkService(HomeworkRepository homeworkRepository) {
        this.homeworkRepository = homeworkRepository;
    }

    public List<Homework> getAllHomeworks() {
        return homeworkRepository.findAll();
    }
    
    public Homework getHomeworkById(Integer id) {
        return homeworkRepository.findById(id).orElse(null);
    }

    public String createHomework(HomeworkDTO homeworkDTO) {
        Homework homework = new Homework(homeworkDTO.getTitle(), homeworkDTO.getContent(), Integer.parseInt(homeworkDTO.getClassId()));
        Homework savedHomework = homeworkRepository.save(homework);
        return savedHomework != null ? "Homework created successfully" : null;
    }

    
    public String updateHomework(Homework existingHomework, HomeworkDTO homeworkDTO) {
        existingHomework.setTitle(homeworkDTO.getTitle());
        existingHomework.setContent(homeworkDTO.getContent());
        existingHomework.setClassId(Integer.parseInt(homeworkDTO.getClassId()));
        Homework updatedHomework = homeworkRepository.save(existingHomework);
        return updatedHomework != null ? "Homework updated successfully" : null;
    }

    public String deleteHomework(Homework existingHomework) {
        homeworkRepository.delete(existingHomework);
        return "Homework deleted successfully";
    }
}
