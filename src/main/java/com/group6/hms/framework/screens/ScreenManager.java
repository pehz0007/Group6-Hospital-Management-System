package com.group6.hms.framework.screens;

import java.util.Stack;

/**
 * This class manages the navigation between various screens.
 * It keeps track of the current screen and a stack of previous screens for handling back navigation.
 */
public class ScreenManager implements Runnable{

    private ApplicationHandle applicationHandle;
    //Current screen which is shown to the user which is also at the top of the navigation stack
    private Screen currentScreen;
    //Console interface for displaying screen;
    private final ConsoleInterface consoleInterface;
    private final Stack<Screen> navigationStack;

    /**
     * Constructor to initialize the ScreenManager with a main (initial) screen and the console interface.
     * The main screen is set as the current screen, and navigation begins.
     *
     * @param mainScreen The initial screen that will be displayed when the application starts.
     * @param consoleInterface The console interface used to display the screens and handle input/output operations.
     */
    public ScreenManager(Screen mainScreen, ConsoleInterface consoleInterface) {
        this.navigationStack = new Stack<>();
        this.currentScreen = mainScreen;
        this.consoleInterface = consoleInterface;
    }

    protected void setApplicationHandle(ApplicationHandle applicationHandle) {
        this.applicationHandle = applicationHandle;
    }

    /**
     * Start a new {@code Screen} with a new navigation stack
     * @param newScreen the initial screen to display
     */
    protected void newScreen(Screen newScreen) {
        applicationHandle.requireSwitchingScreen();
        this.currentScreen = newScreen;
        this.navigationStack.clear();
    }

    /**
     * Navigates to the specified {@code Screen}. It sets the new {@code Screen} as the current screen,
     * adds it to the {@code Screen} stack, and calls the necessary lifecycle methods.
     *
     * @param nextScreen The screen to navigate to.
     */
    public void navigateToScreen(Screen nextScreen) {
        Screen previousScreen = currentScreen;
        //Set current screen to the next screen
        currentScreen = nextScreen;
        initScreen(nextScreen);
        navigationStack.push(nextScreen);

        //Clear console
        consoleInterface.clearConsole();
        //Print the next screen to the console
        printScreen(nextScreen);

        //Lifecycle (OnStart): Call the onStart once the screen is printed to the console
        nextScreen.onStart();

        //Lifecycle (onNextScreen): Call the onNextScreen to inform the previous screen that the screen has been changed
        previousScreen.onNextScreen(nextScreen);
    }

    /**
     * Helper method to print the next {@code Screen}'s header and display content.
     *
     * @param screen The screen whose header and content will be displayed.
     */
    private void printScreen(Screen screen) {
        screen.displayHeader(50);
    }

    /**
     * Helper method to set the necessary references for the {@code Screen} class.
     *
     * @param screen The screen whose header and content will be displayed.
     */
    private void initScreen(Screen screen) {
        //Set the references to the screen
        screen.setScreenManager(this);
        screen.setConsoleInterface(consoleInterface);
    }

    /**
     * Navigates back to the previous screen. It pops the current screen off the stack,
     * sets the previous screen as the current screen, and calls the necessary lifecycle methods.
     */
    public void navigateBack() {
        Screen previousScreen = currentScreen;

        if(navigationStack.size() == 1) throw new ScreenManagerEmptyNavigationStackException("Cannot navigate back when the navigation stack has only one screen");

        //Pop the current screen
        Screen nextScreen = navigationStack.pop();
        currentScreen = nextScreen;
        //Lifecycle (onFinish): Call the onFinish to inform the screen its has been pop off the navigation stack
        previousScreen.onFinish();

        initScreen(nextScreen);

        //Clear console
        consoleInterface.clearConsole();
        //Print the next screen to the console
        printScreen(nextScreen);
        //Lifecycle (onBack): Call the onFinish to inform the screen its has been pop off the navigation stack
        nextScreen.onBack(previousScreen);

    }

    @Override
    public void run() {
        navigateToScreen(currentScreen);
    }
}
