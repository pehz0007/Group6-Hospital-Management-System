package com.group6.hms.framework.screens;

/**
 * Interface to define the lifecycle of a screen in the application.
 */
public interface ScreenLifeCycle {
    /**
     * Called when the screen is starting.
     */
    void onStart();

    /**
     * Called when the screen is being refreshed.
     */
    void onRefresh();

    /**
     * Called when the screen is being display.
     */
    void onDisplay();


    /**
     * Called when the screen is finishing.
     */
    void onFinish();

    /**
     * Called when navigating to the next screen.
     *
     * @param nextScreen the screen to navigate to
     */
    void onNextScreen(Screen nextScreen);

    /**
     * Called when navigating back from a screen.
     *
     * @param backFromScreen the screen to navigate back from
     */
    void onBack(Screen backFromScreen);
}
