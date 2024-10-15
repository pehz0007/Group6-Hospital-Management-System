package com.group6.hms.app.screens.patient;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.auth.UserInvalidPasswordException;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.OptionScreen;

public class PatientConfigurationScreen extends OptionScreen {

    private static final int CHANGE_PASSWORD = 1;
    private LoginManager loginManager;
    /**
     * Constructor to initialize the PatientConfigurationScreen.
     */
    public PatientConfigurationScreen() {
        super("Patient Configuration");
        addOption(CHANGE_PASSWORD, "Change Password", ConsoleColor.YELLOW);
    }

    @Override
    public void onStart() {
        setAllowBack(true);
        loginManager = LoginManager.INSTANCE.getLoginManager();
        super.onStart();
    }

    @Override
    protected void handleOption(int optionId) {
        switch (optionId){
            case CHANGE_PASSWORD -> {
                //TODO: Add old password checking
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
            }
        }
    }
}
