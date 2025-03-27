package com.example.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ResetPwDTO {
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password should be at least 6 characters long")

    @NotBlank(message = "User ID is required")

    @NotBlank(message = "Token is required")

    private String userId;
    private String password;
    private String token;

    public ResetPwDTO() {
    }

    public ResetPwDTO(String userId, String password, String token) {
        this.userId = userId;
        this.password = password;
        this.token = token;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
