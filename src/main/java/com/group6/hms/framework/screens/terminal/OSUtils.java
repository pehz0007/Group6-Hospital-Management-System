package com.group6.hms.framework.screens.terminal;

public class OSUtils {

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    public static boolean isMacOS() {
        return System.getProperty("os.name").toLowerCase().contains("mac");
    }


}
