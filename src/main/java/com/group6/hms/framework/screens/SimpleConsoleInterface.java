package com.group6.hms.framework.screens;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import com.group6.hms.framework.screens.option.ConsoleInputFormatException;
import com.group6.hms.framework.screens.terminal.AnsiColor;

import java.io.Console;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import static com.group6.hms.framework.screens.ConsoleColor.*;

/**
 * This class implements {@link ConsoleInterface} for the local terminal and support text color and basic I/O
 */
public class SimpleConsoleInterface implements ConsoleInterface {

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

    @Override
    public ConsoleColor getCurrentTextConsoleColor() {
        return textConsoleColor;
    }

    @Override
    public ConsoleColor getCurrentBackgroundConsoleColor() {
        return backgroundConsoleColor;
    }

    @Override
    public void resetColor() {
        textConsoleColor = ConsoleColor.WHITE;
        backgroundConsoleColor = null;
    }



    @Override
    public char[] readPassword() {
        if (Objects.isNull(console)) {
            return scanner.nextLine().toCharArray();
        } else {
            char[] password = console.readPassword();
            return password;
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
    public void waitForKeyPress() {
        println("Press <enter> to continue");
        scanner.nextLine();
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
        String number = scanner.nextLine();
        Double result = Doubles.tryParse(number);
        if (result == null) throw new ConsoleInputFormatException("Unable to parse number: " + number);
        return result;
    }

    @Override
    public void print(String s) {
        AnsiColor textColor = AnsiColor.toAsciiColor(textConsoleColor);
        AnsiColor bgColor = AnsiColor.toAsciiColor(backgroundConsoleColor);

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
        AnsiColor textColor = AnsiColor.toAsciiColor(textConsoleColor);
        AnsiColor bgColor = AnsiColor.toAsciiColor(backgroundConsoleColor);

        // Apply text color and optionally background color
        String code = textColor.getForegroundCode();
        if (bgColor != null) {
            code += bgColor.getBackgroundCode();
        }
        code += "m";

        System.out.println(code + s + AnsiColor.ANSI_RESET);
    }
}
