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
    private AnsiColor consoleColor = AnsiColor.ANSI_WHITE;
    private Console console = System.console();

    @Override
    public boolean isColorSupported() {
        //Color text is supported
        return true;
    }

    @Override
    public void setCurrentConsoleColor(ConsoleColor color) {
        //Find the matching color from ConsoleColor to AnsiColor
        for (AnsiColor ansiColor : AnsiColor.values()) {
            if (color.equals(ansiColor.getColor())) {
                this.consoleColor = ansiColor;
                break;
            }
        }
    }

    @Override
    public char[] readPassword() {
        if(Objects.isNull(console)){
            return scanner.nextLine().toCharArray();
        }else {
            return console.readPassword();
        }
    }

    @Override
    public void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ex) {}
    }

    @Override
    public String readString() {
        return scanner.nextLine();
    }

    @Override
    public int readInt() {
        String number = scanner.nextLine();
        Integer result = Ints.tryParse(number);
        if(result == null)throw new ConsoleInputFormatException("Unable to parse number: " + number);
        return result;
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
