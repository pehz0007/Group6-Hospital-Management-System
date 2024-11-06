package com.group6.hms.app.screens.patient;

import com.group6.hms.app.managers.auth.LoginManager;
import com.group6.hms.app.managers.auth.LoginManagerHolder;
import com.group6.hms.app.managers.auth.PasswordUtils;
import com.group6.hms.app.managers.auth.UserInvalidPasswordException;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.option.OptionScreen;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;

import java.util.Arrays;

/**
 * The {@code PatientConfigurationScreen} class represents the screen where a patient can edit their non-medical personal information,
 * including password, email and phone number.
 *
 * <p>This screen extends {@link OptionScreen} to provide a menu for patients to select their desired option.</p>
 */
public class PatientConfigurationScreen extends OptionScreen {

    private static final int CHANGE_PASSWORD = 1;
    private static final int CHANGE_EMAIL = 2;
    private static final int CHANGE_PHONENUMBER = 3;

    private final LoginManager loginManager;
    Patient patient;
    /**
     * Constructor to initialize the PatientConfigurationScreen.
     *
     * @param patient The {@link Patient} whose cofiguration details will be displayed and can be edited.
     */
    public PatientConfigurationScreen(Patient patient) {
        super("Patient Configuration");
        this.patient = patient;
        addOption(CHANGE_PASSWORD, "Change Password", ConsoleColor.YELLOW);
        addOption(CHANGE_EMAIL, "Change Email", ConsoleColor.YELLOW);
        addOption(CHANGE_PHONENUMBER, "Change Phone Number", ConsoleColor.YELLOW);
        loginManager = LoginManagerHolder.getLoginManager();
    }

    /**
     * Called when the screen starts.
     * Sets the option to allow going back and initializes the screen.
     */
    @Override
    public void onStart() {
        setAllowBack(true);
        super.onStart();
    }

    /**
     * Display the current patient data in a vertical table format.
     * This includes information such as the patient's personal information detials.
     */
    @Override
    public void onDisplay() {
        PrintTableUtils.printItemAsVerticalTable(consoleInterface, new PatientDataView(patient));
        super.onDisplay();
    }

    /**
     * Handles the selection of a configuration option by the patient.
     * 3 information that are allowed for patient to change it, which is password, email and phone number.
     *
     * @param optionId The ID of the option selected by the user.
     */
    @Override
    protected void handleOption(int optionId) {
        switch (optionId){
            case CHANGE_PASSWORD -> {
                print("Current Password:");
                char[] currentPassword = consoleInterface.readPassword();

                if (Arrays.equals(patient.getPasswordHashed(), PasswordUtils.hashPassword(currentPassword))){
                    boolean validPassword = false;
                    setCurrentTextConsoleColor(ConsoleColor.BLUE);
                    println("New Password Requirements: ");
                    println("1. Password length must be at least 8 characters.");
                    println("2. Password must contain at least one uppercase letter.");
                    println("3. Password must contain at least one digit.");
                    println("4. Password must contain at least one special character (@#$%^&+=!).");

                    while(!validPassword){
                        setCurrentTextConsoleColor(ConsoleColor.YELLOW);
                        print("New Password:");
                        char[] newPassword = consoleInterface.readPassword();
                        try{
                            loginManager.changePassword(patient, newPassword);
                            setCurrentTextConsoleColor(ConsoleColor.GREEN);
                            println("Password has been changed");
                            validPassword = true;
                        }catch (UserInvalidPasswordException e){
                            setCurrentTextConsoleColor(ConsoleColor.RED);
                            println(e.getReason());
                        }finally {
                            setCurrentTextConsoleColor(ConsoleColor.GREEN);
                        }
                    }
                }else{
                    setCurrentTextConsoleColor(ConsoleColor.RED);
                    println("Password does not match. Please try again.");
                }waitForKeyPress();
            }
            case CHANGE_EMAIL -> {
                print("Current Email: ");//+ patient.getContactInformation());
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
            case CHANGE_PHONENUMBER -> {
                print("Current Phone Number: ");
                String currentPhoneNumber = readString();

                if(currentPhoneNumber.equals(patient.getPhoneNumber())){
                    print("New Phone Number:");
                    String phoneNumber = readString();
                    patient.setPhoneNumber(phoneNumber);
                    loginManager.saveUsersToFile();
                    setCurrentTextConsoleColor(ConsoleColor.GREEN);
                    println("Phone number has been changed");
                }else{
                    setCurrentTextConsoleColor(ConsoleColor.RED);
                    println("Phone number does not match. Please try again.");
                }
            }
        }
    }
}
