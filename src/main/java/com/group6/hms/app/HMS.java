package com.group6.hms.app;

import com.group6.hms.app.screens.MainScreen;
import com.group6.hms.framework.screens.*;

public class HMS {


    public static void main(String[] args) {
        ApplicationHandle appHandle = new ApplicationHandle();
        if (System.console() != null) {
            ScreenManager sm = new ScreenManager(new MainScreen(), new FFMConsoleInterface());
            appHandle.start(sm);
        } else {
            ScreenManager sm = new ScreenManager(new MainScreen(), new SimpleConsoleInterface());
            appHandle.start(sm);
        }

    }

}
