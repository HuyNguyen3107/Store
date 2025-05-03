package com.example.store.repository;

import com.example.store.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    // delete all by user id
    @Modifying
    @Transactional
    void deleteAllByUserId(Integer userId);

    @Modifying
    @Transactional
    void deleteAllByRoleId(Integer userId);
}
