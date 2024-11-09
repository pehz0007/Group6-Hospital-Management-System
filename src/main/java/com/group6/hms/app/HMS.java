package com.group6.hms.app;

import com.group6.hms.app.screens.MainScreen;
import com.group6.hms.framework.screens.*;

/**
 * The {@code HMS} class serves as the entry point for the Hospital Management System application.
 * It initializes the application handle and manages the main screen for UI.
 */
public class HMS {

    /**
     * The main method is the entry point of the application.
     * It checks for console availability and starts the application with the main screen.
     *
    */
    public static void main(String[] args) {
        ApplicationHandle appHandle = new ApplicationHandle();
        if (System.console() != null) {
            ScreenManager sm = new ScreenManager(new MainScreen(), new SimpleConsoleInterface());
            appHandle.start(sm);
        } else {
            ScreenManager sm = new ScreenManager(new MainScreen(), new SimpleConsoleInterface());
            appHandle.start(sm);
        }

    }

}
