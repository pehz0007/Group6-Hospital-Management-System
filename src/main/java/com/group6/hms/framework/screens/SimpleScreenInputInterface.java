package com.group6.hms.framework.screens;

import java.util.Scanner;
import java.util.stream.Stream;

import static com.group6.hms.framework.screens.ConsoleColor.*;

public class SimpleScreenInputInterface implements ScreenInputInterface {

    public enum AnsiColor {

        ANSI_RESET(null, "\u001B[0m"),
        ANSI_BLACK(BLACK, "\033[0;90m"),
        ANSI_RED(RED, "\033[0;91m"),
        ANSI_GREEN(GREEN, "\033[0;92m"),
        ANSI_YELLOW(YELLOW, "\033[0;93m"),
        ANSI_BLUE(BLUE, "\033[0;94m"),
        ANSI_PURPLE(PURPLE, "\033[0;95m"),
        ANSI_CYAN(CYAN, "\033[0;96m"),
        ANSI_WHITE(WHITE, "\033[0;97m");

        private final ConsoleColor color;
        private final String consolePrefix;

        AnsiColor(ConsoleColor color, String consolePrefix) {
            this.color = color;
            this.consolePrefix = consolePrefix;
        }

        public ConsoleColor getColor() {
            return color;
        }

        public String getConsolePrefix() {
            return consolePrefix;
        }

        @Override
        public String toString() {
            return getConsolePrefix();
        }
    }

    private Scanner scanner = new Scanner(System.in);
    private Stream<String> inputsTokens = scanner.tokens();
    private AnsiColor consoleColor = AnsiColor.ANSI_WHITE;

    @Override
    public boolean isColorSupported() {
        return true;
    }

    @Override
    public void setCurrentConsoleColor(ConsoleColor color) {
        for (AnsiColor ansiColor : AnsiColor.values()) {
            if (color.equals(ansiColor.getColor())) {
                this.consoleColor = ansiColor;
                break;
            }
        }
    }

    @Override
    public String readString() {
        return scanner.nextLine();
    }

    @Override
    public int readInt() {
        return Integer.parseInt(inputsTokens.findFirst().get());
    }

    @Override
    public double readDouble() {
        return 0;
    }

    @Override
    public void print(String s) {
        System.out.print(consoleColor + s + AnsiColor.ANSI_RESET);
    }

    @Override
    public void println(String s) {
        System.out.println(consoleColor + s + AnsiColor.ANSI_RESET);
    }
}
