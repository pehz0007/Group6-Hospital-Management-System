package com.group6.hms.framework.screens.option;

/**
 * Exception thrown to indicate that the specified console input could not be found.
 */
public class ConsoleInputNotFoundException extends RuntimeException {
    /**
     * Constructs a new ConsoleInputNotFoundException with the specified detail reason.
     *
     * @param message The detail reason, providing the reason about the cause of the exception.
     */
    public ConsoleInputNotFoundException(String message) {
        super(message);
    }
}
