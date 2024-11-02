package com.group6.hms.app.auth;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

/**
 * The {@code PasswordUtils} class provides utility methods for handling password-related
 * operations, including generating UUIDs, hashing passwords, and verifying password complexity.
 */
public class PasswordUtils {

    /**
     * Hashes a password using SHA-256 and returns the hashed byte array.
     *
     * @param password the password to be hashed, provided as a {@code char} array
     * @return the SHA-256 hashed byte array of the password
     * @throws RuntimeException if the SHA-256 algorithm is not available
     */
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


    /**
     * Verifies that the provided password meets minimum complexity requirements,
     * such as length, presence of uppercase and lowercase letters, digits, and
     * special characters.
     *
     * @param password the password to verify, provided as a {@code char} array
     * @throws UserInvalidPasswordException if the password does not meet the complexity requirements
     */
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

    /**
     * Checks if a character is a special character from a predefined set.
     *
     * @param c the character to check
     * @return {@code true} if the character is a special character, {@code false} otherwise
     */
    private static boolean isSpecialCharacter(char c) {
        String specialChars = "@#$%^&+=!";
        return specialChars.indexOf(c) != -1;
    }

    /**
     * Converts a {@code char} array to a byte array using UTF-8 encoding.
     *
     * @param chars the {@code char} array to convert
     * @return the UTF-8 encoded byte array
     */
    private static byte[] toBytes(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
                byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
        return bytes;
    }

}
