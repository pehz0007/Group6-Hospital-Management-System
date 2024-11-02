package com.group6.hms.framework.screens.terminal;

/**
 * The {@code Terminal} class provide an os independent interface to set the console into raw mode.
 */
public interface Terminal {

    /**
     * Enables raw mode for the terminal.
     */
    void enableRawMode();
    /**
     * Restores the terminal to its previous mode after raw mode has been enabled.
     */
    void restoreTerminalMode();

}
