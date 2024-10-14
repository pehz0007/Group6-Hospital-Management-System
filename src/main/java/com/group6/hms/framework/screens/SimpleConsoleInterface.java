package com.group6.hms.framework.screens;

import com.google.common.primitives.Ints;

import java.io.Console;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import static com.group6.hms.framework.screens.ConsoleColor.*;

/**
 * This class implements {@link ConsoleInterface} for the local terminal and support text color and basic I/O
 */
public class SimpleConsoleInterface implements ConsoleInterface {

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
    }

    private Scanner scanner = new Scanner(System.in);
    private ConsoleColor textConsoleColor = ConsoleColor.WHITE;
    private ConsoleColor backgroundConsoleColor = null;
    private Console console = System.console();

    @Override
    public boolean isColorSupported() {
        //Color text is supported
        return true;
    }

    @Override
    public void setCurrentTextConsoleColor(ConsoleColor color) {
        textConsoleColor = color;
    }

    @Override
    public void setCurrentBackgroundConsoleColor(ConsoleColor color) {
        backgroundConsoleColor = color;
    }

    private AnsiColor toAsciiColor(ConsoleColor color) {
        if (color == null) return null;
        //Find the matching color from ConsoleColor to AnsiColor
        for (AnsiColor ansiColor : AnsiColor.values()) {
            if (color.equals(ansiColor.getColor())) {
                return ansiColor;
            }
        }
        return null;
    }

    @Override
    public char[] readPassword() {
        if (Objects.isNull(console)) {
            return scanner.nextLine().toCharArray();
        } else {
            return console.readPassword();
        }
    }

    @Override
    public void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ex) {
        }
    }

    @Override
    public String readString() {
        return scanner.nextLine();
    }

    @Override
    public int readInt() {
        String number = scanner.nextLine();
        Integer result = Ints.tryParse(number);
        if (result == null) throw new ConsoleInputFormatException("Unable to parse number: " + number);
        return result;
    }

    @Override
    public double readDouble() {
        return 0;
    }

    @Override
    public void print(String s) {
        AnsiColor textColor = toAsciiColor(textConsoleColor);
        AnsiColor bgColor = toAsciiColor(backgroundConsoleColor);

        // Apply text color and optionally background color
        String code = textColor.getForegroundCode();
        if (bgColor != null) {
            code += bgColor.getBackgroundCode();
        }
        code += "m";
        System.out.print(code + s + AnsiColor.ANSI_RESET);
    }


    @Override
    public void println(String s) {
        AnsiColor textColor = toAsciiColor(textConsoleColor);
        AnsiColor bgColor = toAsciiColor(backgroundConsoleColor);

        // Apply text color and optionally background color
        String code = textColor.getForegroundCode();
        if (bgColor != null) {
            code += bgColor.getBackgroundCode();
        }
        code += "m";

        System.out.println(code + s + AnsiColor.ANSI_RESET);
    }
}
