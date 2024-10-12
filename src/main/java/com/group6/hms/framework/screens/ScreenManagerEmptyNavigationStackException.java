package com.group6.hms.framework.screens;

/**
 * Exception thrown when attempting to navigate back in the ScreenManager
 * and the navigation stack is empty.
 */
public class ScreenManagerEmptyNavigationStackException extends RuntimeException {
    public ScreenManagerEmptyNavigationStackException(String message) {
        super(message);
    }
}
