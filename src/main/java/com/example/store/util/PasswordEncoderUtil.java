package com.example.store.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Mã hóa mật khẩu
    public static String encodePassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    // Kiểm tra mật khẩu nhập vào với mật khẩu đã mã hóa
    public static boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }

    public static void main(String[] args) {
    }
}
