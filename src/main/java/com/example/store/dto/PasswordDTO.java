package com.example.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PasswordDTO {
    @NotBlank(message = "password cannot be blank")
    @Size(min = 6, message = "password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "new password cannot be blank")
    @Size(min = 6, message = "new password must be at least 6 characters long")
    private String nwPassword;

    @NotBlank(message = "confirm password cannot be blank")
    @Size(min = 6, message = "confirm password must be at least 6 characters long")
    private String confirmPassword;

    public PasswordDTO() {
    }

    public PasswordDTO(String password, String nwPassword, String confirmPassword) {
        this.password = password;
        this.nwPassword = nwPassword;
        this.confirmPassword = confirmPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNwPassword() {
        return nwPassword;
    }

    public void setNwPassword(String nwPassword) {
        this.nwPassword = nwPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
