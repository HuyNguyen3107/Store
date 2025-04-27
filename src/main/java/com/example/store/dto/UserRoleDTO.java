package com.example.store.dto;
import java.util.*;

public class UserRoleDTO {
    private List<String> roleIds;
    private String userId;

    public UserRoleDTO() {
    }

    public UserRoleDTO(List<String> roleIds, String userId) {
        this.roleIds = roleIds;
        this.userId = userId;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}