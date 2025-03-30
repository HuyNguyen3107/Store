package com.example.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TeacherDTO {
    @NotBlank(message = "User id is required")
    private String userId;

    private String status = "active";

    public TeacherDTO() {
    }

    public TeacherDTO(String userId) {
        this.userId = userId;
    }

    public TeacherDTO(String userId, String status) {
        this.userId = userId;
        this.status = status;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
