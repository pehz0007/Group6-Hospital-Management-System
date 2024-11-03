package com.group6.hms.app.screens.patient;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.auth.LoginManagerHolder;
import com.group6.hms.app.auth.PasswordUtils;
import com.group6.hms.app.auth.UserInvalidPasswordException;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.option.OptionScreen;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;

import java.util.Arrays;

public class PatientConfigurationScreen extends OptionScreen {

    private static final int CHANGE_PASSWORD = 1;
    private static final int CHANGE_EMAIL = 2;

    private final LoginManager loginManager;
    Patient patient;
    /**
     * Constructor to initialize the PatientConfigurationScreen.
     */
    public PatientConfigurationScreen(Patient patient) {
        super("Patient Configuration");
        this.patient = patient;
        addOption(CHANGE_PASSWORD, "Change Password", ConsoleColor.YELLOW);
        addOption(CHANGE_EMAIL, "Change Email", ConsoleColor.YELLOW);
        loginManager = LoginManagerHolder.getLoginManager();
    }

    @Override
    public void onStart() {
        setAllowBack(true);
        super.onStart();
    }

    @Override
    public void onDisplay() {
        PrintTableUtils.printItemAsVerticalTable(consoleInterface, new PatientDataView(patient));
        super.onDisplay();
    }

    @Override
    protected void handleOption(int optionId) {
        switch (optionId){
            case CHANGE_PASSWORD -> {
                //TODO: Add old password checking
                print("Current Password:");
                char[] currentPassword = consoleInterface.readPassword();

                if (Arrays.equals(patient.getPasswordHashed(), PasswordUtils.hashPassword(currentPassword))){
                    print("New Password:");
                    char[] newPassword = consoleInterface.readPassword();
                    try{
                        loginManager.changePassword(patient, newPassword);
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
                    println("Password does not match. Please try again.");
                }
            }
            case CHANGE_EMAIL -> {
                //TODO: Add old email checking
                println("Current Email: " + patient.getContactInformation());
                String currentEmail = readString();

                if(currentEmail.equals(patient.getContactInformation())){
                    print("New Email:");
                    String email = readString();
                    patient.setContactInformation(email);
                    //Update data to file
                    loginManager.saveUsersToFile();
                    setCurrentTextConsoleColor(ConsoleColor.GREEN);
                    println("Email has been changed");
                }else{
                    setCurrentTextConsoleColor(ConsoleColor.RED);
                    println("Email does not match. Please try again.");
                }
                waitForKeyPress();
            }
        }
    }
}
