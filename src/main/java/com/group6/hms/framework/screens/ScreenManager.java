package com.group6.hms.framework.screens;

import java.util.Stack;

public class ScreenManager {

    private Screen currentScreen;
    private Stack<Screen> screenStack;

    public ScreenManager(Screen mainScreen) {
        this.screenStack = new Stack<>();
        this.currentScreen = mainScreen;
        navigateToScreen(mainScreen);
    }

    public void navigateToScreen(Screen screen) {
        Screen previousScreen = currentScreen;
        Screen nextScreen = screen;
        currentScreen = nextScreen;
        nextScreen.setScreenManager(this);
        screenStack.push(screen);

        printScreen(nextScreen);
        nextScreen.onStart();

        previousScreen.onNextScreen(nextScreen);
    }

    private static void printScreen(Screen nextScreen) {
        nextScreen.displayHeader();
    }

    public void navigateBack() {
        Screen previousScreen = currentScreen;
        previousScreen.onFinish();
        Screen nextScreen = screenStack.pop();
        currentScreen = nextScreen;
        nextScreen.setScreenManager(this);

        nextScreen.onBack(previousScreen);

        printScreen(nextScreen);
    }

}
