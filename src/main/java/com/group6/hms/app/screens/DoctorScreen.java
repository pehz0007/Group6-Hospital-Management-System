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
        addOption(1, "View Patient Medical Records");
        addOption(2, "Update Patient Medical Records");
        addOption(3, "View Personal Schedule");
        addOption(4, "Set Availability for Appointments");
        addOption(5, "Accept or Decline Appointment Requests");
        addOption(6, "View Upcoming Appointments");
        addOption(7, "Record Appointment Outcome");
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
