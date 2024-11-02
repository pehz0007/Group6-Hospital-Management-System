package com.group6.hms.framework.screens.terminal;

/**
 * A utility class for checking the operating system type.
 */
public class OSUtils {

    /**
     * Checks if the currently running operating system is Windows.
     *
     * @return true if the operating system is Windows, otherwise false.
     */
    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    /**
     * Checks if the operating system is macOS.
     *
     * @return true if the operating system is macOS, otherwise false.
     */
    public static boolean isMacOS() {
        return System.getProperty("os.name").toLowerCase().contains("mac");
    }


}
