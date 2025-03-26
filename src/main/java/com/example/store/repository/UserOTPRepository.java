package com.example.store.repository;

import com.example.store.model.UserOTP;
import com.example.store.dto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserOTPRepository extends JpaRepository<UserOTP, Integer> {
    // Find UserOTP by userId
    UserOTP findByUserId(Integer userId);
    // Find UserOTP by OTP
    UserOTP findByOtp(String otp);

    
}