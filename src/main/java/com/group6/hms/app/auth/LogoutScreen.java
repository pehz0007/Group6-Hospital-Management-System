package com.group6.hms.app.auth;

import com.group6.hms.framework.screens.option.OptionScreen;

/**
 * The {@code LogoutScreen} class provides a screen for users to log out. It extends
 * {@link OptionScreen} to provide a logout option and uses {@link LoginManager} to handle
 * the logout process.
 * The class is abstract. Subclasses must implement the {@code onLogout()} method to define actions upon logout.
 */
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
        loginManager = LoginManagerHolder.getLoginManager();
    }

    /**
     * Called when the screen starts. It initializes any additional setup
     * and calls the parent class's {@code onStart} method.
     */
    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * Handles options when the user navigates back from the screen. If the logout
     * option is selected, it triggers the {@code logout()} method.
     *
     * @param optionId The ID of the option selected by the user.
     */
    @Override
    protected void handleOptionOnBack(int optionId) {
        if(optionId == LOGOUT_ID) logout();
        else super.handleOptionOnBack(optionId);
    }

    /**
     * Abstract method to be implemented by subclasses to define specific actions upon logout.
     */
    protected abstract void onLogout();

    /**
     * Provides access to the {@code LoginManager} instance.
     *
     * @return the {@code LoginManager} instance
     */
    protected LoginManager getLoginManager() {return loginManager;}

    /**
     * Logs out the current user by calling the {@code logout()} method on
     * {@code LoginManager} and invoking {@code onLogout()} for additional actions.
     */
    private void logout() {
        loginManager.logout();
        onLogout();
    }
}
