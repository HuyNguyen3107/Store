package com.example.store.helper;

public class TokenHelper {
        public static String generateToken(String email, String password) {
            long currentTimeMillis = System.currentTimeMillis();
            String randomString = Long.toHexString(currentTimeMillis) + email + password;
            return randomString.substring(0, 32); 
        }
}