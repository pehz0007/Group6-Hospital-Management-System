package com.group6.hms.app.screens;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.framework.screens.LogoutScreen;

public class DoctorScreen extends LogoutScreen {

    /**
     * Constructor to initialize the DoctorScreen.
     */
    protected DoctorScreen() {
        super("Doctor Menu");
        addOption(2, "View Patient Medical Records");
        addOption(3, "Update Patient Medical Records");
        addOption(4, "View Personal Schedule");
        addOption(5, "Set Availability for Appointments");
        addOption(6, "Accept or Decline Appointment Requests");
        addOption(7, "View Upcoming Appointments");
        addOption(8, "Record Appointment Outcome");
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
