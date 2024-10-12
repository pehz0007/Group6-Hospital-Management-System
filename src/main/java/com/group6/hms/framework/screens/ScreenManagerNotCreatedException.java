package com.group6.hms.framework.screens;

/**
 * Exception thrown when attempting to the ScreenManager is not created or references is not setup correctly by the screen manager
 */
public class ScreenManagerNotCreatedException extends RuntimeException {
    public ScreenManagerNotCreatedException(String message) {
        super(message);
    }
}
