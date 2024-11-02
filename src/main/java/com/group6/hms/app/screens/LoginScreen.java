package com.group6.hms.app.screens;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.auth.LoginManagerHolder;
import com.group6.hms.app.auth.User;
import com.group6.hms.app.roles.Administrator;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.app.roles.Pharmacist;
import com.group6.hms.app.screens.admin.AdministratorScreen;
import com.group6.hms.app.screens.doctor.DoctorScreen;
import com.group6.hms.app.screens.patient.PatientScreen;
import com.group6.hms.app.screens.pharmacist.PharmacistScreen;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.Screen;

/**
 * The login screen for the application.
 * This screen handles user authentication and directs users to
 * their respective roles upon successful login.
 */
public class LoginScreen extends Screen {

    private LoginManager loginManager;

    /**
     * Constructs a LoginScreen instance and initializes the
     * LoginManager for user authentication.
     */
    public LoginScreen() {
        super("Login");
        //        setPrintHeader(false);
        loginManager = LoginManagerHolder.getLoginManager();
        loginManager.loadUsersFromFile();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        setCurrentTextConsoleColor(ConsoleColor.PURPLE);
        boolean loginSuccessful = false;
        while (!loginSuccessful) {
            print("User ID: ");
            String userId = readString();
            print("Password: ");
            char[] password = consoleInterface.readPassword();

            //Perform login authentication
            if (loginManager.login(userId, password)) {
                loginSuccessful = true;
                println("Login Successful");

                User currentUser = loginManager.getCurrentlyLoggedInUser();

                switch (currentUser) {
                    case Patient patient -> newScreen(new PatientScreen());
                    case Doctor doctor -> newScreen(new DoctorScreen());
                    case Pharmacist pharmacist -> newScreen(new PharmacistScreen());
                    case Administrator administrator -> newScreen(new AdministratorScreen());
                    default -> throw new IllegalStateException("Unexpected value: " + currentUser);
                }

            } else {
                println("Login Failed");
            }
        }
    }
}
