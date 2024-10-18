package com.group6.hms.app.auth;

import com.group6.hms.framework.screens.OptionScreen;

public abstract class LogoutScreen extends OptionScreen {

    private LoginManager loginManager;
    private final static int LOGOUT_ID = 1;

    /**
     * Constructor to initialize the LogoutScreen with a header.
     * This constructor passes the header to the parent Screen class.
     *
     * @param title The title of the screen, which is displayed as a header.
     */
    protected LogoutScreen(String title) {
        super(title);
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

    protected LoginManager getLoginManager() {return loginManager;}

    private void logout() {
        loginManager.logout();
        onLogout();
    }
}
