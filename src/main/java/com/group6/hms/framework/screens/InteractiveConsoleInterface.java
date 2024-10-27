package com.group6.hms.framework.screens;

public interface InteractiveConsoleInterface extends ConsoleInterface {

    Operation readUserKey();
    void saveCurrentScreen();
    void restoreCurrentScreen();
}
