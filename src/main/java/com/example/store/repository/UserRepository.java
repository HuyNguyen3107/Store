package com.example.store.repository;

import com.example.store.model.User;
import com.example.store.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    User findByToken(String token);

    User findUserById(Integer userId);

    User findByEmailAndToken(String email, String token);

    User findUserByIdAndEmail(Integer userId, String email);
}
