package com.group6.hms.app.screens.admin;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.auth.User;
import com.group6.hms.app.screens.MainScreen;
import com.group6.hms.framework.screens.PaginationTableScreen;
import com.group6.hms.framework.screens.LogoutScreen;

import java.util.ArrayList;
import java.util.Collection;

public class AdministratorScreen extends LogoutScreen {

    private final int CREATE_USER = 2;
    private final int VIEW_ALL_USERS = 3;
    private final int VIEW_APPOINTMENTS = 4;

    private LoginManager loginManager;

    /**
     * Constructor to initialize the AdministratorScreen.
     **/
    public AdministratorScreen() {
        super("Administrator");
        loginManager = LoginManager.INSTANCE.getLoginManager();

        addOption(CREATE_USER, "Create User");
        addOption(VIEW_ALL_USERS, "View All Users");
        addOption(VIEW_APPOINTMENTS, "View All Appointments");
    }

    @Override
    public void onStart() {
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
                Collection<User> users = loginManager.getAllUsers();
                navigateToScreen(new PaginationTableScreen<>("Users", users.stream().map(UserView::new).toList()));
            }
            case VIEW_APPOINTMENTS -> {
                //Retrieve all Appointments in the database

                //Display all the Appointments

            }
        }
    }
}
