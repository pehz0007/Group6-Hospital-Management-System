package com.group6.hms.app.screens;

import com.group6.hms.framework.auth.LoginManager;
import com.group6.hms.framework.screens.LogoutScreen;

public class PatientScreen extends LogoutScreen {

    /**
     * Constructor to initialize the PatientScreen.
     */
    protected PatientScreen() {
        super("Patient Menu");

    }

    @Override
    public void onStart() {
        println("Welcome, " + LoginManager.getCurrentlyLoggedInUser().getUsername());
        addOption(2, "Show Medical Record");
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
