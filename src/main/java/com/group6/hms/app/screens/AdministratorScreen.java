package com.group6.hms.app.screens;

import com.group6.hms.framework.screens.OptionScreen;

public class AdministratorScreen extends OptionScreen {

    private final int CREATE_USER = 1;
    private final int VIEW_APPOINTMENTS = 2;

    /**
     * Constructor to initialize the AdministratorScreen.
     **/
    protected AdministratorScreen() {
        super("Administrator Screen");

    }

    @Override
    public void onStart() {
        setAllowBack(true);
        addOption(VIEW_APPOINTMENTS, "View All Appointments");


        super.onStart();
    }

    @Override
    protected void handleOption(int optionId) {
        switch (optionId) {
            case CREATE_USER -> {
                //Create user

            }
            case VIEW_APPOINTMENTS -> {
                //Retrieve all Appointments in the database

                //Display all the Appointments

            }
        }
    }
}
