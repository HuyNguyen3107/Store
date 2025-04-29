package com.example.store.dto;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RoleDTO {
    @NotBlank(message = "name cannot be blank")
    private String name;

    private List<String> permissionIds;

    public RoleDTO() {
    }

    public RoleDTO(String name, List<String> permissionIds) {
        this.name = name;
        this.permissionIds = permissionIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<String> permissionIds) {
        this.permissionIds = permissionIds;
    }
}
