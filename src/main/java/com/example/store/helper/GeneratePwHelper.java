package com.example.store.helper;

public class GeneratePwHelper {
    public static String generateRandomPassword() {
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specialCharacters = "!@#$%^&*()-_=+<>?";
        
        String allCharacters = upperCaseLetters + lowerCaseLetters + digits + specialCharacters;
        
        StringBuilder password = new StringBuilder();
        
        // Ensure at least one character from each category is included
        password.append(upperCaseLetters.charAt((int) (Math.random() * upperCaseLetters.length())));
        password.append(lowerCaseLetters.charAt((int) (Math.random() * lowerCaseLetters.length())));
        password.append(digits.charAt((int) (Math.random() * digits.length())));
        password.append(specialCharacters.charAt((int) (Math.random() * specialCharacters.length())));
        
        // Fill the rest of the password length with random characters
        for (int i = 4; i < 6; i++) {
            password.append(allCharacters.charAt((int) (Math.random() * allCharacters.length())));
        }
        
        return password.toString();
    }

}