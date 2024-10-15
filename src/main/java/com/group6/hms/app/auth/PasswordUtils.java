package com.group6.hms.app.auth;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

public class PasswordUtils {

    public static UUID generateUUID() {
        return UUID.randomUUID();
    }

    public static byte[] hashPassword(char[] password){
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] encodedhash = digest.digest(toBytes(password));
        return encodedhash;
    }

    public static void verifyPassword(char[] password) {
        // Define the minimum required length for the password
        int minLength = 8;

        // Check for minimum length
        if (password.length < minLength) {
            throw new UserInvalidPasswordException("Password length must be at least " + minLength + " characters.");
        }

        // Initialize flags for password complexity requirements
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        // Check each character in the password array
        for (char c : password) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (isSpecialCharacter(c)) {
                hasSpecialChar = true;
            }
        }

        // Throw exceptions if any requirement is not met
        if (!hasUpperCase) {
            throw new UserInvalidPasswordException("Password must contain at least one uppercase letter.");
        }
        if (!hasLowerCase) {
            throw new UserInvalidPasswordException("Password must contain at least one lowercase letter.");
        }
        if (!hasDigit) {
            throw new UserInvalidPasswordException("Password must contain at least one digit.");
        }
        if (!hasSpecialChar) {
            throw new UserInvalidPasswordException("Password must contain at least one special character (@#$%^&+=!).");
        }
    }

    // Helper method to determine if a character is a special character
    private static boolean isSpecialCharacter(char c) {
        String specialChars = "@#$%^&+=!";
        return specialChars.indexOf(c) != -1;
    }

    private static byte[] toBytes(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
                byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
        return bytes;
    }

}
