package com.group6.hms.app;

import com.group6.hms.app.screens.MainScreen;
import com.group6.hms.framework.screens.ApplicationHandle;
import com.group6.hms.framework.screens.JLineConsoleInterface;
import com.group6.hms.framework.screens.ScreenManager;
import com.group6.hms.framework.screens.SimpleConsoleInterface;

public class HMS {

    public static void main(String[] args) {
        ApplicationHandle appHandle = new ApplicationHandle();
        ScreenManager sm = new ScreenManager(new MainScreen(), new SimpleConsoleInterface());
        appHandle.start(sm);
    }

}
