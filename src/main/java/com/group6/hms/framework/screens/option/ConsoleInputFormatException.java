package com.group6.hms.framework.screens.option;

/**
 * Exception thrown to indicate that the format of the console input is invalid.
 */
public class ConsoleInputFormatException extends RuntimeException {

    /**
     * Constructs a new ConsoleInputFormatException with the specified reason of the invalid input
     *
     * @param message The detail message, which provides the reason about the invalid input.
     */
    public ConsoleInputFormatException(String message) {
        super(message);
    }
}
