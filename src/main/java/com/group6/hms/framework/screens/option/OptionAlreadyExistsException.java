package com.group6.hms.framework.screens.option;

/**
 * Exception thrown to indicate that an option with the given ID already exists
 */
public class OptionAlreadyExistsException extends RuntimeException {
    /**
     * Constructs a new OptionAlreadyExistsException with the specified detail reason.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public OptionAlreadyExistsException(String message) {
        super(message);
    }
}
