package com.example.store.repository;

import com.example.store.model.UserRole;
import com.example.store.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    // delete all by user id
    void deleteAllByUserId(Integer userId);
}