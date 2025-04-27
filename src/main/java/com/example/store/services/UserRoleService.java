package com.example.store.service;

import com.example.store.model.UserRole;
import com.example.store.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.store.dto.*;
import java.util.*;
import com.example.store.helper.GeneratePwHelper;
import com.example.store.util.*;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }
    public String updateRolesToUser(UserRoleDTO userRoleDTO) {
        int userId = Integer.parseInt(userRoleDTO.getUserId());

        userRoleRepository.deleteAllByUserId(userId);
        List<UserRole> userRoles = new ArrayList<>();
        for (String roleId : userRoleDTO.getRoleIds()) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(Integer.parseInt(roleId));
            userRoles.add(userRole);
        }

        userRoleRepository.saveAll(userRoles);
        return "Roles updated successfully";
    }
}
