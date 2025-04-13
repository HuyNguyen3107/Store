package com.example.store.service;

import com.example.store.model.Answer;
import com.example.store.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.store.dto.*;
import java.util.*;
import com.example.store.helper.GeneratePwHelper;
import com.example.store.util.*;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

    public Answer getAnswerById(Integer id) {
        return answerRepository.findById(id).orElse(null);
    }

    public String createAnswer(AnswerDTO answerDTO) {
        Answer answer = new Answer();
        answer.setContent(answerDTO.getContent());
        answer.setHomeworkId(Integer.parseInt(answerDTO.getHomeworkId()));
        answer.setStudentId(Integer.parseInt(answerDTO.getStudentId()));
        Answer savedAnswer = answerRepository.save(answer);
        return savedAnswer != null ? "Answer created successfully" : null;
    }

    
    public String updateAnswer(Answer existingAnswer, AnswerDTO answerDTO) {
        existingAnswer.setContent(answerDTO.getContent());
        existingAnswer.setHomeworkId(Integer.parseInt(answerDTO.getHomeworkId()));
        existingAnswer.setStudentId(Integer.parseInt(answerDTO.getStudentId()));
        Answer updatedAnswer = answerRepository.save(existingAnswer);
        return updatedAnswer != null ? "Answer updated successfully" : null;
    }

    
    public String deleteAnswer(Answer existingAnswer) {
        answerRepository.delete(existingAnswer);
        return "Answer deleted successfully";
    }
}
