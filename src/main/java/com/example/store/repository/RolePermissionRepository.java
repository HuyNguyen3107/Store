package com.example.store.repository;

import com.example.store.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {

    // delete all role permissions by role id
    @Modifying
    @Transactional
    void deleteAllByRoleId(Integer roleId);
}
