package com.group6.hms.app.screens.patient;

import com.group6.hms.app.screens.MainScreen;
import com.group6.hms.app.auth.LogoutScreen;

public class PatientScreen extends LogoutScreen {


    private static final int MEDICAL_RECORD = 2;
    private static final int USER_CONFIGURATION = 3;

    /**
     * Constructor to initialize the PatientScreen.
     */
    public PatientScreen() {
        super("Patient Menu");
        addOption(MEDICAL_RECORD, "Show Medical Record");
        addOption(USER_CONFIGURATION, "Edit User Profile");
    }

    @Override
    public void onStart() {
        println("Welcome, " + getLoginManager().getCurrentlyLoggedInUser().getUsername());
        super.onStart();
    }

    @Override
    protected void onLogout() {
        newScreen(new MainScreen());
    }

    @Override
    protected void handleOption(int optionId) {
        switch (optionId){
            case USER_CONFIGURATION -> navigateToScreen(new PatientConfigurationScreen());
        }
    }
}
