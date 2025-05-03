package com.example.store.service;

import com.example.store.model.*;
import com.example.store.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.store.dto.*;
import java.util.*;
import com.example.store.helper.GeneratePwHelper;
import com.example.store.util.*;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository, RolePermissionRepository rolePermissionRepository, UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
    
    public Role getRoleById(Integer id) {
        return roleRepository.findById(id).orElse(null);
    }

    public String createRole(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        Role savedRole = roleRepository.save(role);

        int roleId = savedRole.getId();
        rolePermissionRepository.deleteAllByRoleId(roleId);
        List<RolePermission> rolePermissions = new ArrayList<>();

        for (String permissionId : roleDTO.getPermissionIds()) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(Integer.parseInt(permissionId));
            rolePermissions.add(rolePermission);
        }

        rolePermissionRepository.saveAll(rolePermissions);
        return savedRole != null ? "Role created successfully" : null;
    }
    
    public String updateRole(Role existingRole, RoleDTO roleDTO) {
        existingRole.setName(roleDTO.getName());
        Role updatedRole = roleRepository.save(existingRole);
        return updatedRole != null ? "Role updated successfully" : null;
    }

    public String deleteRole(Integer id) {
        rolePermissionRepository.deleteAllByRoleId(id);
        userRoleRepository.deleteAllByRoleId(id);
        Role existingRole = roleRepository.findById(id).orElse(null);
        roleRepository.delete(existingRole);
        return "Role deleted successfully";
    }
}
