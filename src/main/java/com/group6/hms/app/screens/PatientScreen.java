package com.group6.hms.app.screens;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.framework.screens.LogoutScreen;

public class PatientScreen extends LogoutScreen {

    private static final int MEDICAL_RECORD = 2;
    private static final int USER_CONFIGURATION = 3;

    /**
     * Constructor to initialize the PatientScreen.
     */
    protected PatientScreen() {
        super("Patient Menu");

    }

    @Override
    public void onStart() {
        println("Welcome, " + LoginManager.getCurrentlyLoggedInUser().getUsername());
        addOption(MEDICAL_RECORD, "Show Medical Record");
        addOption(USER_CONFIGURATION, "Edit User Profile");
        super.onStart();
    }

    @Override
    protected void onLogout() {
        newScreen(new MainScreen());
    }

    @Override
    protected void handleOption(int optionId) {
        switch (optionId){
            case USER_CONFIGURATION -> navigateToScreen(new UserConfigurationScreen());
        }
    }
}
