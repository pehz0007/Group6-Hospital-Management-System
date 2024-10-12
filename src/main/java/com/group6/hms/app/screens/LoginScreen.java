package com.group6.hms.app.screens;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.auth.User;
import com.group6.hms.app.auth.roles.Role;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.Screen;

public class LoginScreen extends Screen {

    private LoginManager loginManager = new LoginManager();

    public LoginScreen() {
        super("Login");
//        setPrintHeader(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        loginManager.loadUsersFromFile();

        setCurrentConsoleColor(ConsoleColor.PURPLE);
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
                    case Role.Patient -> newScreen(new PatientScreen());
                    case Role.Doctor -> newScreen(new DoctorScreen());
                    case Role.Administrator -> newScreen(new AdministratorScreen());
                    default -> throw new IllegalStateException("Unexpected value: " + currentUser.getRole());
                }

            }else {
                println("Login Failed");
            }
        }

    }
}
