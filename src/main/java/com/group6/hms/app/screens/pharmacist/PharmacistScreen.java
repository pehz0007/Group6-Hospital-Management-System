package com.group6.hms.app.screens.pharmacist;

import com.group6.hms.app.screens.MainScreen;
import com.group6.hms.app.auth.LogoutScreen;

public class PharmacistScreen extends LogoutScreen {

    /**
     * Constructor to initialize the PharmacistScreen.
     */
    public PharmacistScreen() {
        super("Pharmacist Menu");
        addOption(2, "View Appointment Outcome Record");
        addOption(3, "Update Prescription Status");
        addOption(4, "View Medication Inventory");
        addOption(5, "Submit Replenishment Request");
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
//        switch (optionId) {
//            case 2 -> navigateToScreen();
//        }
    }
}
