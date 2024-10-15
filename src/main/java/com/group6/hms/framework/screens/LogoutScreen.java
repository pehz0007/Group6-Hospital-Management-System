package com.group6.hms.framework.screens;

import com.group6.hms.app.auth.LoginManager;

public abstract class LogoutScreen extends OptionScreen{

    private LoginManager loginManager;
    private final static int LOGOUT_ID = 1;

    /**
     * Constructor to initialize the LogoutScreen with a header.
     * This constructor passes the header to the parent Screen class.
     *
     * @param header The title of the screen, which is displayed as a header.
     */
    protected LogoutScreen(String header) {
        super(header);
        addOption(LOGOUT_ID, "Logout");
    }

    @Override
    public void onStart() {
        loginManager = LoginManager.INSTANCE.getLoginManager();
        super.onStart();
    }

    @Override
    protected void handleOptionOnBack(int optionId) {
        if(optionId == LOGOUT_ID) logout();
        else super.handleOptionOnBack(optionId);
    }

    protected abstract void onLogout();

    private void logout() {
        loginManager.logout();
        onLogout();
    }
}
