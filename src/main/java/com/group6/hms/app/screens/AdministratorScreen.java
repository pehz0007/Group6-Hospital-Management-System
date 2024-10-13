package com.group6.hms.app.screens;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.framework.screens.LogoutScreen;

public class AdministratorScreen extends LogoutScreen {

    private final int CREATE_USER = 2;
    private final int VIEW_ALL_USERS = 3;
    private final int VIEW_APPOINTMENTS = 4;

    private LoginManager loginManager = new LoginManager();

    /**
     * Constructor to initialize the AdministratorScreen.
     **/
    protected AdministratorScreen() {
        super("Administrator Screen");

    }

    @Override
    public void onStart() {
        addOption(CREATE_USER, "Create User");
        addOption(VIEW_ALL_USERS, "View All Users");
        addOption(VIEW_APPOINTMENTS, "View All Appointments");

        loginManager.loadUsersFromFile();
        super.onStart();
    }

    @Override
    protected void onLogout() {
        newScreen(new MainScreen());
    }

    @Override
    protected void handleOption(int optionId) {
        switch (optionId) {
            case CREATE_USER -> {
                //Create user
                print("Username:");

            }
            case VIEW_ALL_USERS -> {
                PrintTableUtils.printItemsAsTable(this, loginManager.getAllUsers());
            }
            case VIEW_APPOINTMENTS -> {
                //Retrieve all Appointments in the database

                //Display all the Appointments

            }
        }
    }
}
