package com.group6.hms.app.screens;

import com.group6.hms.framework.auth.LoginManager;
import com.group6.hms.framework.screens.OptionScreen;

public class PatientScreen extends OptionScreen {

    /**
     * Constructor to initialize the PatientScreen.
     */
    protected PatientScreen() {
        super("Patient Menu");

    }

    @Override
    public void onStart() {
        setAllowBack(false);
        addOption(1, "Show Medical Record");
        super.onStart();
        println("Welcome, " + LoginManager.getCurrentlyLoggedInUser().getUsername());
    }

    @Override
    protected void handleOption(int optionId) {

    }
}
