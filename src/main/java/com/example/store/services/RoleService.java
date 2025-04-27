package com.example.store.service;

import com.example.store.model.Role;
import com.example.store.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.store.dto.*;
import java.util.*;
import com.example.store.helper.GeneratePwHelper;
import com.example.store.util.*;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
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
        return savedRole != null ? "Role created successfully" : null;
    }
    
    public String updateRole(Role existingRole, RoleDTO roleDTO) {
        existingRole.setName(roleDTO.getName());
        Role updatedRole = roleRepository.save(existingRole);
        return updatedRole != null ? "Role updated successfully" : null;
    }

    public String deleteRole(Role existingRole) {
        roleRepository.delete(existingRole);
        return "Role deleted successfully";
    }
}
