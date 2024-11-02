package com.group6.hms.app.screens;

import com.group6.hms.framework.screens.option.OptionScreen;

/**
 * The main screen of the Hospital Management System (HMS).
 * This screen provides options for the user to log in or exit the application.
 */
public class MainScreen extends OptionScreen {

    /**
     * Constructs a MainScreen instance and initializes the options available
     * to the user, including "Login" and "Exit".
     */
    public MainScreen() {
        super("HMS - Hospital Management System");
        addOption(1, "Login");
        addOption(2, "Exit");
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void handleOption(int optionId) {
        switch(optionId) {
            case 1 -> navigateToScreen(new LoginScreen());
            case 2 -> {
                println("HMS - System shutting down...\n");
                doNotLoopScreen();
            }
        }
    }
}
