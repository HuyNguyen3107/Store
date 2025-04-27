package com.example.store.service;

import com.example.store.model.Permission;
import com.example.store.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.store.dto.*;
import java.util.*;
import com.example.store.helper.GeneratePwHelper;
import com.example.store.util.*;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }
    
    public Permission getPermissionById(Integer id) {
        return permissionRepository.findById(id).orElse(null);
    }

    public String createPermission(PermissionDTO permissionDTO) {
        Permission permission = new Permission();
        permission.setValue(permissionDTO.getValue());
        Permission savedPermission = permissionRepository.save(permission);
        return savedPermission != null ? "Permission created successfully" : null;
    }

    
    public String updatePermission(Permission existingPermission, PermissionDTO permissionDTO) {
        existingPermission.setValue(permissionDTO.getValue());
        Permission updatedPermission = permissionRepository.save(existingPermission);
        return updatedPermission != null ? "Permission updated successfully" : null;
    }

    public String deletePermission(Permission existingPermission) {
        permissionRepository.delete(existingPermission);
        return "Permission deleted successfully";
    }
}
