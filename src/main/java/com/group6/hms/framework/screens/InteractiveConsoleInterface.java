package com.group6.hms.framework.screens;

public interface InteractiveConsoleInterface extends ConsoleInterface {

    Operation readUserKey();
    void saveCurrentScreen();
    void restoreCurrentScreen();
    /**
     * Clear partial screen from the cursor to the end of the screen
     */
    void clearPartialScreen();

    /**
     * Move cursor to a specific position (row, column)
     * @param row - row of the cursor
     * @param column - column of the cursor
     */
    void moveCursor(int row, int column);

    /**
     * Get the current cursor position.
     *
     * @return An array where index 0 is the row, and index 1 is the column.
     */
    int[] getCursorPosition();
}
