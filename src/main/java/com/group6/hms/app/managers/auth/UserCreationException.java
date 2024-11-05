package com.group6.hms.app.managers.auth;


/**
 * The {@code UserCreationException} class is a custom exception that is thrown when there
 * is an error during the creation of a {@code User} object, such as when a user already
 * exists or when password requirements are not met.
 */
public class UserCreationException extends RuntimeException {

    /**
     * Constructs a new {@code UserCreationException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public UserCreationException(String message) {
        super(message);
    }
}
