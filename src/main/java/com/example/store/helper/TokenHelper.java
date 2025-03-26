package com.example.store.helper;

public class TokenHelper {
    public static String generateToken() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder token = new StringBuilder();
        long currentTimeMillis = System.currentTimeMillis();
        String timeString = Long.toHexString(currentTimeMillis);
        
        for (int i = 0; i < 26; i++) {
            int randomIndex = (int) (Math.random() * chars.length());
            token.append(chars.charAt(randomIndex));
        }
        
        return timeString + token.toString();
    }

    public static boolean validateToken(String token, String tokenToValidate) {
        return token.equals(tokenToValidate);
    }
}
