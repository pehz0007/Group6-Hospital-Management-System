package com.group6.hms.app.auth;

public class LoginManagerHolder {

    private final ThreadLocal<LoginManagerHolder> loginManagerInstancePerThread = new ThreadLocal<>();
    private LoginManager loginManager;

    public LoginManager getLoginManager() {
        if (loginManagerInstancePerThread.get() == null) {
            createLoginManager();
        }
        return loginManager;
    }

    private synchronized void createLoginManager() {
        if (loginManager == null) {
            loginManager = new LoginManager();
        }
        loginManagerInstancePerThread.set(this);
    }

}
