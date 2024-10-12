package com.group6.hms.app.screens;

import com.group6.hms.framework.screens.OptionScreen;

public class MainScreen extends OptionScreen {
    public MainScreen() {
        super("HMS - Hospital Management System");
    }

    @Override
    public void onStart() {
        setAllowBack(false);
        addOption(1, "Login");
        addOption(2, "Exit");
        super.onStart();
    }

    @Override
    protected void handleOption(int optionId) {
        switch(optionId) {
            case 1 -> navigateToScreen(new LoginScreen());
            case 2 -> {
                println("com.group6.hms.app.HMS - System shutting down...\n");
                System.exit(0);
            }
        }
    }
}
