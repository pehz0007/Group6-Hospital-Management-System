package com.group6.hms.framework.screens;

public class ApplicationHandle{

    private ScreenManager screenManager;
    private boolean switchScreen = false;
    private Thread appThread;

    public void start(ScreenManager screenManager) {
        this.screenManager = screenManager;
        screenManager.setApplicationHandle(this);
        appThread = new Thread(this::run);
        appThread.start();
        try {
            appThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set the switchScreen value to true, the stack frame from the previous screen will be unwind before
     * the next screen is called
     */
    protected void requireSwitchingScreen() {
        this.switchScreen = true;
    }

    private void run() {
        //Implement switching between screen
        do{
            switchScreen = false;
            screenManager.run();
            //Check if there is a new screen, if yes we will call the screen manager again
        }while (switchScreen);
    }
}
