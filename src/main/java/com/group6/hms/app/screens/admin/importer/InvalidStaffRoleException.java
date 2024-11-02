package com.group6.hms.app.screens.admin.importer;

/**
 * The {@code InvalidStaffRoleException} is a custom runtime exception
 * that is thrown when an invalid staff role is encountered in the system.
 */
public class InvalidStaffRoleException extends RuntimeException {

    /**
     * Constructs a new {@code InvalidStaffRoleException} with the specified detail message.
     *
     * @param message the detail message that explains the reason for the exception
     */
    public InvalidStaffRoleException(String message) {
        super(message);
    }
}
