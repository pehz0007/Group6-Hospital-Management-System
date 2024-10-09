package com.group6.hms.app.screens;

import com.group6.hms.framework.screens.Screen;

public class LoginScreen extends Screen {

    public LoginScreen() {
        super("Login Screen");
        setPrintHeader(false);
    }

    @Override
    public void onStart() {
        super.onStart();

        print("Username: ");
        String username = readString();
        print("Password: ");
        String password = readString();

        //Perform login authentication


    }
}
