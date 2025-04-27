package com.example.store.repository;

import com.example.store.model.RolePermission;
import com.example.store.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {
    // delete all role permission by role id 
    void deleteAllByRoleId(Integer roleId);
}