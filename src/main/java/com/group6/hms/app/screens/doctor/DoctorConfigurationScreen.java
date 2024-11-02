package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.auth.LoginManagerHolder;
import com.group6.hms.app.auth.UserInvalidPasswordException;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.roles.Pharmacist;
import com.group6.hms.app.screens.admin.StaffDataView;
import com.group6.hms.app.screens.patient.PatientDataView;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.option.OptionScreen;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;

 /**
 * The {@code DoctorConfigurationScreen} allows a doctor to manage their account settings,
 * including changing their password.
 */
public class DoctorConfigurationScreen extends OptionScreen {

    private static final int CHANGE_PASSWORD = 1;

    private final LoginManager loginManager;
    Doctor doctor;

    /**
     * Constructs a DoctorConfigurationScreen for the specified doctor.
     *
     * @param doctor The doctor whose configuration settings are to be managed.
     */
    public DoctorConfigurationScreen(Doctor doctor) {
        super("Doctor Configuration");
        this.doctor = doctor;
        addOption(CHANGE_PASSWORD, "Change Password", ConsoleColor.YELLOW);
        loginManager = LoginManagerHolder.getLoginManager();
    }

    /**
     * Displays the doctor's data on the screen.
     * This method overrides the onDisplay method from OptionScreen
     * to print the current staff data of the doctor.
     */
    @Override
    public void onDisplay() {
        PrintTableUtils.printItemAsVerticalTable(consoleInterface, new StaffDataView(doctor));
        super.onDisplay();
    }

    /**
     * Configures the screen to allow the user to navigate back.
     * This method overrides the onStart method from OptionScreen
     * to enable back navigation.
     */
    @Override
    public void onStart() {
        setAllowBack(true);
        super.onStart();
    }

    /**
     * Handles user options selected from the configuration menu.
     * Currently supports changing the doctor's password.
     *
     * @param optionId The ID of the selected option.
     */
    @Override
    protected void handleOption(int optionId) {
        switch (optionId){
            case CHANGE_PASSWORD -> {
                print("New Password:");
                char[] newPassword = consoleInterface.readPassword();
                try{
                    loginManager.changePassword(doctor, newPassword);
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
