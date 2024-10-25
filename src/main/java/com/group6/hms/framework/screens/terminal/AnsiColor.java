package com.group6.hms.framework.screens.terminal;

import com.group6.hms.framework.screens.ConsoleColor;

import static com.group6.hms.framework.screens.ConsoleColor.*;
import static com.group6.hms.framework.screens.ConsoleColor.WHITE;

/**
 * Color code for ansi color
 * See: <a href="https://gist.github.com/fnky/458719343aabd01cfb17a3a4f7296797#color-codes">Color Codes</a>
 */
public enum AnsiColor {

    ANSI_RESET(null, "\u001B[0m", "\u001B[0m"),
    ANSI_BLACK(BLACK, "\033[0;90", ";100"),
    ANSI_RED(RED, "\033[0;91", ";101"),
    ANSI_GREEN(GREEN, "\033[0;92", ";102"),
    ANSI_YELLOW(YELLOW, "\033[0;93", ";103"),
    ANSI_BLUE(BLUE, "\033[0;94", ";104"),
    ANSI_PURPLE(PURPLE, "\033[0;95", ";105"),
    ANSI_CYAN(CYAN, "\033[0;96", ";106"),
    ANSI_WHITE(WHITE, "\033[0;97", ";107");

    private final ConsoleColor color;
    private final String foregroundCode;
    private final String backgroundCode;

    AnsiColor(ConsoleColor color, String foregroundCode, String backgroundCode) {
        this.color = color;
        this.foregroundCode = foregroundCode;
        this.backgroundCode = backgroundCode;
    }

    public ConsoleColor getColor() {
        return color;
    }

    public String getForegroundCode() {
        return foregroundCode;
    }

    public String getBackgroundCode() {
        return backgroundCode;
    }

    @Override
    public String toString() {
        return getForegroundCode();
    }

    // Method to reset both foreground and background colors
    public static String reset() {
        return ANSI_RESET.toString();
    }

    public static AnsiColor toAsciiColor(ConsoleColor color) {
        if (color == null) return null;
        //Find the matching color from ConsoleColor to AnsiColor
        for (AnsiColor ansiColor : AnsiColor.values()) {
            if (color.equals(ansiColor.getColor())) {
                return ansiColor;
            }
        }
        return null;
    }

}