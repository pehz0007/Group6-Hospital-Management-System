package com.group6.hms.app.screens;

import com.group6.hms.app.roles.PatientRole;
import com.group6.hms.framework.auth.LoginManager;
import com.group6.hms.framework.auth.User;
import com.group6.hms.framework.screens.Screen;

public class LoginScreen extends Screen {

    private LoginManager loginManager = new LoginManager();

    public LoginScreen() {
        super("Login Screen");
        setPrintHeader(false);


        //CREATE SAMPLE USERS
        loginManager.createUser("Patient 1", "Password1".toCharArray(), new PatientRole());
        loginManager.createUser("Patient 2", "Password2".toCharArray(), new PatientRole());
    }

    @Override
    public void onStart() {
        super.onStart();

        print("Username: ");
        String username = readString();
        print("Password: ");
        char[] password = consoleInterface.readPassword();

        //Perform login authentication
        if(loginManager.login(username, password)) {
            println("Login Successful");

            User currentUser = loginManager.getCurrentlyLoggedInUser();

            println("Welcome, " + currentUser.getUsername());

        }else {
            println("Login Failed");
        }

    }
}
