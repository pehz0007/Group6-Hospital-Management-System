package com.group6.hms.framework.screens;

import com.group6.hms.framework.auth.LoginManager;

public abstract class LogoutScreen extends OptionScreen{

    private final static int LOGOUT_ID = 1;

    /**
     * Constructor to initialize the LogoutScreen with a header.
     * This constructor passes the header to the parent Screen class.
     *
     * @param header The title of the screen, which is displayed as a header.
     */
    protected LogoutScreen(String header) {
        super(header);
    }

    @Override
    public void onStart() {
        addOption(LOGOUT_ID, "Logout");
        super.onStart();
    }

    @Override
    protected void handleOptionOnBack(int optionId) {
        if(optionId == LOGOUT_ID) logout();
        else super.handleOptionOnBack(optionId);
    }

    protected abstract void onLogout();

    private void logout() {
        LoginManager.logout();
        onLogout();
    }
}
