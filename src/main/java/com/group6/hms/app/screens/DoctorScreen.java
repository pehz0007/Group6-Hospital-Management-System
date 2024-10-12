package com.group6.hms.app.screens;

import com.group6.hms.framework.auth.LoginManager;
import com.group6.hms.framework.screens.OptionScreen;

public class DoctorScreen extends OptionScreen {

    /**
     * Constructor to initialize the DoctorScreen.
     */
    protected DoctorScreen() {
        super("Doctor Menu");
    }

    @Override
    public void onStart() {
        setAllowBack(false);
        println("Welcome, " + LoginManager.getCurrentlyLoggedInUser().getUsername());
        super.onStart();
    }

    @Override
    protected void handleOption(int optionId) {

    }
}
