package com.example.store.controller;

import com.example.store.model.*;
import com.example.store.service.*;
import com.example.store.helper.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.example.store.dto.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {
    private final UserService userService;
    private final AuthService authService;
    private final PasswordTokenService passwordTokenService;
    private final UserOTPService userOTPService;
    private final EmailService emailService;

    @Autowired
    public AuthController(UserService userService, AuthService authService, 
                          PasswordTokenService passwordTokenService, UserOTPService userOTPService, EmailService emailService) {
        this.emailService = emailService;
        this.userService = userService;
        this.authService = authService;
        this.passwordTokenService = passwordTokenService;
        this.userOTPService = userOTPService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody AuthDTO AuthDTO) {
        User user = authService.login(AuthDTO.getEmail(), AuthDTO.getPassword());
        if (user == null) {
            return ResponseEntity.status(401).build(); 
        }
        if (user != null) {
            String token = TokenHelper.generateToken();
            userService.saveToken(user.getId(), token);
            // generate OTP with 6 number and save to userOTPService 
            String otp = Math.random() * 1000000 + "";
            otp = otp.substring(0, 6);
            String expirationTime = (System.currentTimeMillis() + 5 * 60 * 1000) + ""; // 5 minutes
            UserOTP userOTP = new UserOTP(otp, expirationTime, user.getId());
            if (userOTPService.getOTPByUserId(user.getId()) != null) {
                userOTPService.updateOTPByUserId(user.getId(), otp, expirationTime);
            } else {
                userOTPService.addOTP(userOTP);
            }
            emailService.sendEmail("levi2k3ds@gmail.com", "OTP Code to login", "Your OTP code is: " + otp);
            return ResponseEntity.ok().build(); 
        } else {
            return ResponseEntity.status(401).build(); 
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        token = token.substring(7); 
        User user = userService.getUserByToken(token);
        if (user == null) {
            return ResponseEntity.status(401).build(); 
        }
        if (TokenHelper.validateToken(user.getToken() ,token)) {
            authService.saveToken(user.getId(), null); 
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(401).build(); 
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody EmailDTO emailDTO) {
        String email = emailDTO.getEmail();
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().build(); 
        }
        User user = userService.getUserByEmail(email);
        if (user != null) {
            String token = TokenHelper.generateToken();
            String expirationTime = (System.currentTimeMillis() + 5 * 60 * 1000) + "";
            PasswordToken passwordToken = new PasswordToken(token, expirationTime, user.getId());
            PasswordToken existingToken = passwordTokenService.findByUserId(user.getId());
            if (existingToken != null) {
                passwordTokenService.updateTokenByUserId(user.getId(), token, expirationTime);
            } else {
                passwordTokenService.addToken(passwordToken);
            }
            emailService.sendEmail(email, "Password Reset", "Your password reset token is: " + token);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).build(); 
        }
    }

    @GetMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String id) {
        String userId = id;
        if (token == null || token.isEmpty() || userId == null || userId.isEmpty()) {
            return ResponseEntity.badRequest().build(); 
        }
        PasswordToken passwordToken = passwordTokenService.findByUserId(Integer.parseInt(userId));
        if (passwordToken != null && passwordToken.getResetToken().equals(token)) {
            long currentTime = System.currentTimeMillis();
            if (Long.parseLong(passwordToken.getExpired()) > currentTime) {
                return ResponseEntity.ok().body("OK");
            } else {
                return ResponseEntity.status(400).body("Token expired"); 
            }
        } else {
            return ResponseEntity.status(404).body("Not Found"); 
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> changePassword(@RequestBody ResetPwDTO resetPwDTO) {
        String token = resetPwDTO.getToken();
        String userId = resetPwDTO.getUserId();
        String newPassword = resetPwDTO.getPassword();
        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().build(); 
        }
        if (userId == null || userId.isEmpty() || newPassword == null || newPassword.isEmpty()) {
            return ResponseEntity.badRequest().build(); 
        }
        PasswordToken passwordToken = passwordTokenService.getByUserIdAndToken(Integer.parseInt(userId), token);

        if (passwordToken == null) {
            return ResponseEntity.status(404).build(); 
        }
        User user = userService.getUserById(Integer.parseInt(userId));
        if (user != null) {
            userService.updatePassword(user.getId(), newPassword);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).build(); 
        }
    }

    @PostMapping("change-password")
    public ResponseEntity<Void> changePassword(@RequestHeader("Authorization") String token, @RequestBody String newPassword) {
        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().build(); 
        }
        if (newPassword == null || newPassword.isEmpty()) {
            return ResponseEntity.badRequest().build(); 
        }
        token = token.substring(7);
        User user = userService.getUserByToken(token);
        if (user != null) {
            userService.updatePassword(user.getId(), newPassword);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).build(); 
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<User> verifyOTP(@Valid @RequestBody OtpDTO otpDTO) {
        String otp = otpDTO.getOtp();
        if (otp == null || otp.isEmpty()) {
            return ResponseEntity.status(400).build(); 
        }
        UserOTP userOTP = userOTPService.getByOTP(otp);
        if (userOTP != null) {
            long currentTime = System.currentTimeMillis();
            System.out.println("Current time: " + currentTime); 
            System.out.println("Expired time: " + userOTP.getExpired());
            if (Long.parseLong(userOTP.getExpired()) > currentTime) {
                User user = userService.getUserById(userOTP.getUserId());
                if (user != null) {
                    userOTPService.deleteOTPByUserId(user.getId());
                    return ResponseEntity.ok(user); 
                } else {
                    return ResponseEntity.status(404).build(); 
                }
            } else {
                return ResponseEntity.status(400).build();
            }
        } else {
            return ResponseEntity.status(404).build(); 
        }
    }
}

 