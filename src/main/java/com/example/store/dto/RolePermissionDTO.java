package com.example.store.dto;
import java.util.*;

public class RolePermissionDTO {
    private List<String> permissionIds;
    private String roleId;

    public RolePermissionDTO() {
    }

    public RolePermissionDTO(List<String> permissionIds, String roleId) {
        this.permissionIds = permissionIds;
        this.roleId = roleId;
    }

    public List<String> getPermissionIds() {
        return permissionIds;
    }   

    public void setPermissionIds(List<String> permissionIds) {
        this.permissionIds = permissionIds;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}