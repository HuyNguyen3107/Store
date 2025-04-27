package com.example.store.service;

import com.example.store.model.RolePermission;
import com.example.store.repository.RolePermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.store.dto.*;
import java.util.*;
import com.example.store.helper.GeneratePwHelper;
import com.example.store.util.*;

@Service
public class RolePermissionService {
    private final RolePermissionRepository rolePermissionRepository;

    @Autowired
    public RolePermissionService(RolePermissionRepository rolePermissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
    }
    public String updatePermissionsToRole(RolePermissionDTO rolePermissionDTO) {
        int roleId = Integer.parseInt(rolePermissionDTO.getRoleId());

        rolePermissionRepository.deleteAllByRoleId(roleId);
        List<RolePermission> rolePermissions = new ArrayList<>();

        for (String permissionId : rolePermissionDTO.getPermissionIds()) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(Integer.parseInt(permissionId));
            rolePermissions.add(rolePermission);
        }

        rolePermissionRepository.saveAll(rolePermissions);
        return "Permissions updated successfully for role ID: " + roleId;
    }
}
