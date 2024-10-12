package com.group6.hms.app.screens;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.framework.screens.LogoutScreen;

public class DoctorScreen extends LogoutScreen {

    /**
     * Constructor to initialize the DoctorScreen.
     */
    protected DoctorScreen() {
        super("Doctor Menu");
    }

    @Override
    public void onStart() {
        println("Welcome, " + LoginManager.getCurrentlyLoggedInUser().getUsername());
        super.onStart();
    }

    @Override
    protected void onLogout() {
        newScreen(new MainScreen());
    }

    @Override
    protected void handleOption(int optionId) {

    }
}
