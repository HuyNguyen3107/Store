package com.example.store.service;

import com.example.store.model.UserOTP;
import com.example.store.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserOTPService {
    private final UserOTPRepository userOTPRepository;

    @Autowired
    public UserOTPService (UserOTPRepository userOTPRepository) {
        this.userOTPRepository = userOTPRepository;
    }

    // get OTP by user id optional
    public UserOTP getOTPByUserId(Integer userId) {
        return userOTPRepository.findByUserId(userId);
    }

    // getByOTP
    public UserOTP getByOTP(String otp) {
        return userOTPRepository.findByOtp(otp);
    } 

    // add OTP
    public void addOTP(UserOTP userOTP) {
        userOTPRepository.save(userOTP);
    }

    // update OTP by user id
    public void updateOTPByUserId(Integer userId, String otp) {
        UserOTP userOTP = userOTPRepository.findByUserId(userId);
        if (userOTP != null) {
            UserOTP existingUserOTP = userOTP;
            existingUserOTP.setOtp(otp);
            userOTPRepository.save(existingUserOTP);
        }
    }
       
}
