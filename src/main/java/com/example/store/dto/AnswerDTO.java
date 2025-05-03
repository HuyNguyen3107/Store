package com.example.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AnswerDTO {
    @NotBlank(message = "Content is required")
    private String content;

    @NotBlank(message = "Homework ID is required")
    @Size(min = 1, message = "Homework ID must be at least 1 character long")
    private String homeworkId;

    @NotBlank(message = "User ID is required")
    @Size(min = 1, message = "User ID must be at least 1 character long")
    private String userId;

    public AnswerDTO() {
    }

    public AnswerDTO(String content, String homeworkId, String userId) {
        this.content = content;
        this.homeworkId = homeworkId;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
