package com.group6.hms.app.screens;

import com.group6.hms.framework.auth.LoginManager;
import com.group6.hms.framework.screens.LogoutScreen;

public class PatientScreen extends LogoutScreen {

    private static final int MEDICAL_RECORD = 2;
    private static final int USER_CONFIGURATION = 3;
    private static final int VIEW_AVAILABLE_APPOINTMENTS = 4;
    private static final int SCHEDULE_APPOINTMENTS = 5;
    private static final int RESCHEDULE_APPOINTMENTS = 6;
    private static final int CANCEL_APPOINTMENTS = 7;
    private static final int APPOINTMENTS_STATUS = 8;
    private static final int VIEW_PAST_OUTCOMES = 9;

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
        addOption(VIEW_AVAILABLE_APPOINTMENTS, "View Available Appointment Slots");
        addOption(SCHEDULE_APPOINTMENTS, "Schedule Appointments");
        addOption(RESCHEDULE_APPOINTMENTS, "Reschedule Appointments");
        addOption(CANCEL_APPOINTMENTS, "Cancel Appointments");
        addOption(APPOINTMENTS_STATUS, "View Scheduled Appointments Status");
        addOption(VIEW_PAST_OUTCOMES, "View Past Appointment Outcome Records");
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
