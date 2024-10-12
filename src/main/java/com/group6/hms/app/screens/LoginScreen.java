package com.group6.hms.app.screens;

import com.group6.hms.app.roles.DoctorRole;
import com.group6.hms.app.roles.PatientRole;
import com.group6.hms.framework.auth.LoginManager;
import com.group6.hms.framework.auth.User;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.Screen;
import com.group6.hms.framework.screens.ScreenManager;

public class LoginScreen extends Screen {

    private LoginManager loginManager = new LoginManager();

    public LoginScreen() {
        super("Login");
//        setPrintHeader(false);

        //CREATE SAMPLE USERS
        loginManager.createUser("Patient 1", "Password1".toCharArray(), new PatientRole());
        loginManager.createUser("Doctor 1", "Password2".toCharArray(), new DoctorRole());
    }

    @Override
    public void onStart() {
        super.onStart();

        setConsoleColor(ConsoleColor.PURPLE);
        boolean loginSuccessful = false;
        while (!loginSuccessful) {
            print("Username: ");
            String username = readString();
            print("Password: ");
            char[] password = consoleInterface.readPassword();

            //Perform login authentication
            if(loginManager.login(username, password)) {
                loginSuccessful = true;
                println("Login Successful");

                User currentUser = LoginManager.getCurrentlyLoggedInUser();

                switch (currentUser.getRole()) {
                    case PatientRole p -> newScreenManager(new PatientScreen());
                    case DoctorRole d -> newScreenManager(new DoctorScreen());
                    default -> throw new IllegalStateException("Unexpected value: " + currentUser.getRole());
                }

            }else {
                println("Login Failed");
            }
        }

    }
}
