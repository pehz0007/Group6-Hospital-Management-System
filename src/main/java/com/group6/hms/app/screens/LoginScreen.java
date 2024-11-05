package com.group6.hms.app.screens;

import com.group6.hms.app.managers.auth.*;
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

import java.util.Arrays;

public class LoginScreen extends Screen {

    private LoginManager loginManager;

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
                if(currentUser.isFirstTimeLogin())firstTimeLoginPassword(currentUser);

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

    private void firstTimeLoginPassword(User user){
        println("Password changed required for first time user!");
        while(true){
            setCurrentTextConsoleColor(ConsoleColor.PURPLE);
            print("New Password:");
            char[] newPassword = consoleInterface.readPassword();
            try{
                loginManager.changePassword(user, newPassword);
                setCurrentTextConsoleColor(ConsoleColor.GREEN);
                println("Password has been changed");
                user.setFirstTimeLogin(false);
                loginManager.saveUsersToFile();
                return;
            }catch (UserInvalidPasswordException e){
                setCurrentTextConsoleColor(ConsoleColor.RED);
                println(e.getReason());
            }finally {
                waitForKeyPress();
            }
        }
    }

}
