package com.example.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AnswerDTO {
    @NotBlank(message = "Content is required")
    private String content;

    @NotBlank(message = "Homework ID is required")
    @Size(min = 1, message = "Homework ID must be at least 1 character long")
    private String homeworkId;

    @NotBlank(message = "Student ID is required")
    @Size(min = 1, message = "Student ID must be at least 1 character long")
    private String studentId;

    public AnswerDTO() {
    }

    public AnswerDTO(String content, String homeworkId, String studentId) {
        this.content = content;
        this.homeworkId = homeworkId;
        this.studentId = studentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
