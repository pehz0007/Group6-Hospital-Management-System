package com.group6.hms.app.screens.patient;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.auth.LoginManagerHolder;
import com.group6.hms.app.auth.PasswordUtils;
import com.group6.hms.app.auth.UserInvalidPasswordException;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.option.OptionScreen;
import com.group6.hms.app.auth.User;

import java.util.Arrays;

public class PatientConfigurationScreen extends OptionScreen {

    private static final int CHANGE_PASSWORD = 1;
    private LoginManager loginManager;
    /**
     * Constructor to initialize the PatientConfigurationScreen.
     */
    public PatientConfigurationScreen() {
        super("Patient Configuration");
        addOption(CHANGE_PASSWORD, "Change Password", ConsoleColor.YELLOW);
        loginManager = LoginManagerHolder.getLoginManager();
    }

    @Override
    public void onStart() {
        setAllowBack(true);
        super.onStart();
    }

    @Override
    protected void handleOption(int optionId) {
        switch (optionId){
            case CHANGE_PASSWORD -> {
                //TODO: Add old password checking
                println("Enter Old Password:");
                char[] oldPassword = consoleInterface.readPassword();

                User currentUser = loginManager.getCurrentlyLoggedInUser();
                if (currentUser == null) {
                    setCurrentTextConsoleColor(ConsoleColor.RED);
                    println("No user is logged in.");
                    waitForKeyPress();
                    return;
                }

                byte[] hashedOldPassword = PasswordUtils.hashPassword(oldPassword);

                if(Arrays.equals(currentUser.getPasswordHashed(), hashedOldPassword)){
                    print("New Password:");
                    char[] newPassword = consoleInterface.readPassword();
                    try{
                        loginManager.changePassword(loginManager.getCurrentlyLoggedInUser(), newPassword);
                        setCurrentTextConsoleColor(ConsoleColor.GREEN);
                        println("Password has been changed");
                    }catch (UserInvalidPasswordException e){
                        setCurrentTextConsoleColor(ConsoleColor.RED);
                        println(e.getReason());
                    }finally {
                        waitForKeyPress();
                    }
                }else{
                    setCurrentTextConsoleColor(ConsoleColor.RED);
                    println("Old Password is incorrect.");
                    waitForKeyPress();
                }
            }
        }
    }
}
