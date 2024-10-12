package com.group6.hms.app.screens;

import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.OptionScreen;

public class UserConfigurationScreen extends OptionScreen {

    private static final int CHANGE_PASSWORD = 1;
    /**
     * Constructor to initialize the UserConfigurationScreen.
     */
    public UserConfigurationScreen() {
        super("User Configuration");
    }

    @Override
    public void onStart() {
        setAllowBack(true);
        addOption(CHANGE_PASSWORD, "Change Password", ConsoleColor.YELLOW);
        super.onStart();
    }

    @Override
    protected void handleOption(int optionId) {

    }
}
