package com.group6.hms.app.screens.pharmacist;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.auth.LoginManagerHolder;
import com.group6.hms.app.auth.UserInvalidPasswordException;
import com.group6.hms.app.roles.Pharmacist;
import com.group6.hms.app.screens.admin.StaffDataView;
import com.group6.hms.app.screens.patient.PatientDataView;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.option.OptionScreen;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;

/**
 * The {@code PharmacistConfigurationScreen} allows pharmacists to manage their configuration,
 * including changing their password and viewing their information.
 */
public class PharmacistConfigurationScreen extends OptionScreen {

    private static final int CHANGE_PASSWORD = 1;

    private final LoginManager loginManager;
    Pharmacist pharmacist;

    /**
     * Constructor to initialize the PharmacistConfigurationScreen with the specified pharmacist.
     *
     * @param pharmacist the pharmacist whose configuration is being managed
     */
    public PharmacistConfigurationScreen(Pharmacist pharmacist) {
        super("Pharmacist Configuration");
        this.pharmacist = pharmacist;
        addOption(CHANGE_PASSWORD, "Change Password", ConsoleColor.YELLOW);
        loginManager = LoginManagerHolder.getLoginManager();
    }

    @Override
    public void onDisplay() {
        PrintTableUtils.printItemAsVerticalTable(consoleInterface, new StaffDataView(pharmacist));
        super.onDisplay();
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
                print("New Password:");
                char[] newPassword = consoleInterface.readPassword();
                try{
                    loginManager.changePassword(pharmacist, newPassword);
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
