package com.example.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PermissionDTO {
    @NotBlank(message = "value cannot be blank")
    private String value;

    public PermissionDTO() {
    }

    public PermissionDTO(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }   
}
