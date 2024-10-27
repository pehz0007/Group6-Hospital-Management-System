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
    //Whether a new screen is shown
    private boolean isNewScreen = true;

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
        isNewScreen = true;
        applicationHandle.requireSwitchingScreen();
        this.currentScreen = newScreen;
        this.navigationStack.clear();
        this.navigationStack.push(currentScreen);
    }

    /**
     * Do not display the screen again and will instead end the application once the screen exit
     */
    protected void doNotLoopScreen() {
        applicationHandle.doNotKeepRunning();
    }

    /**
     * Display the current {@code Screen} and push the screen to the navigation stack calls the necessary lifecycle methods.
     *
     * @param nextScreen The screen to display.
     */
    public void displayScreen(Screen nextScreen) {
        //Set current screen to the next screen
        currentScreen = nextScreen;
        initScreen(nextScreen);

        //Clear console
        consoleInterface.clearConsole();
        //Print the next screen header to the console
        printHeaderScreen(nextScreen);

        //Lifecycle (OnStart): Call the onStart to start the screen
        if(isNewScreen){
            nextScreen.onStart();
            isNewScreen = false;
        }
        else nextScreen.onRefresh(); //Lifecycle (onRefresh): Call the onRefresh to refresh the screen

        //Lifecycle (OnDisplay): Call the OnDisplay to display the screen contents to the console
        nextScreen.onDisplay();
    }

    /**
     * Navigates to the specified {@code Screen}. It sets the new {@code Screen} as the current screen,
     * adds it to the {@code Screen} stack, and calls the necessary lifecycle methods.
     *
     * @param nextScreen The screen to navigate to.
     */
    public void navigateToScreen(Screen nextScreen) {
        isNewScreen = true;
        Screen previousScreen = currentScreen;
        //Set current screen to the next screen
        currentScreen = nextScreen;
        initScreen(nextScreen);
        navigationStack.push(currentScreen);

        //Lifecycle (onNextScreen): Call the onNextScreen to inform the previous screen that the screen has been changed
        previousScreen.onNextScreen(nextScreen);

        applicationHandle.requireSwitchingScreen();
    }

    /**
     * Navigates back to the previous screen. It pops the current screen off the stack,
     * sets the previous screen as the current screen, and calls the necessary lifecycle methods.
     */
    public void navigateBack() {
        if(navigationStack.size() == 1) throw new ScreenManagerEmptyNavigationStackException("Cannot navigate back when the navigation stack has only one screen");
        //Pop the current screen
        Screen previousScreen = navigationStack.pop();

        //Peek the new screen
        Screen nextScreen = navigationStack.peek();
        currentScreen = nextScreen;
        //Lifecycle (onFinish): Call the onFinish to inform the screen its has been pop off the navigation stack
        previousScreen.onFinish();

        initScreen(nextScreen);

        //Clear console
        consoleInterface.clearConsole();

        applicationHandle.requireSwitchingScreen();

        //Lifecycle (onBack): Call the onFinish to inform the screen its has been pop off the navigation stack
        nextScreen.onBack(previousScreen);
    }

    /**
     * Helper method to print the next {@code Screen}'s header and display content.
     *
     * @param screen The screen whose header and content will be displayed.
     */
    private void printHeaderScreen(Screen screen) {
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


    @Override
    public void run() {
        displayScreen(currentScreen);
    }
}
