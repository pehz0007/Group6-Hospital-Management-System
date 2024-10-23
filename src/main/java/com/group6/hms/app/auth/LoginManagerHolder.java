package com.group6.hms.app.auth;

public class LoginManagerHolder {

    private static volatile LoginManager instance;

    // Private constructor to prevent instantiation
    private LoginManagerHolder() {
    }

    public static LoginManager getLoginManager() {
        if (instance == null) {
            synchronized (LoginManagerHolder.class) {
                if (instance == null) {
                    instance = new LoginManager();
                }
            }
        }
        return instance;
    }

}
