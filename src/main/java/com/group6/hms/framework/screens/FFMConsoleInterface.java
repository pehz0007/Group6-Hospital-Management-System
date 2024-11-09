package com.group6.hms.framework.screens;

import com.group6.hms.framework.screens.option.ConsoleInputFormatException;
import com.group6.hms.framework.screens.terminal.AnsiColor;
import com.group6.hms.framework.screens.terminal.Terminal;
import com.group6.hms.framework.screens.terminal.mac.MacOsTerminal;
import com.group6.hms.framework.screens.terminal.win.WindowsTerminal;

import java.io.Console;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.group6.hms.framework.screens.pagination.TextUtils.skipNewLines;
import static com.group6.hms.framework.screens.terminal.OSUtils.isMacOS;
import static com.group6.hms.framework.screens.terminal.OSUtils.isWindows;

/**
 * The {@code FFMConsoleInterface} provides an interactive console for interacting with the application
 */
public class FFMConsoleInterface implements InteractiveConsoleInterface {

    // Terminal instance that implements the Terminal interface
    private final Terminal terminal;

    // ANSI Color management
    private Scanner scanner = new Scanner(System.in);
    private Console console = System.console();
    private ConsoleColor textConsoleColor = ConsoleColor.WHITE;
    private ConsoleColor backgroundConsoleColor = null;

    private Map<CharSequence, Operation> keyMap = new HashMap<>();

    // Constructor to initialize the terminal
    public FFMConsoleInterface() {
        if (isWindows()) {
            terminal = new WindowsTerminal();
        } else if (isMacOS()) {
            terminal = new MacOsTerminal();
        } else {
            throw new RuntimeException("Running on an unsupported OS.");
        }
        this.terminal.enableRawMode();  // Enable raw mode using the terminal interface

        keyMap.put("\033[A", Operation.UP);
        keyMap.put("\033[B", Operation.DOWN);
        keyMap.put("\033[D", Operation.LEFT);
        keyMap.put("\033[C", Operation.RIGHT);
        keyMap.put("\r", Operation.ENTER);
        keyMap.put("q", Operation.EXIT);

    }

    @Override
    public boolean isColorSupported() {
        return terminal != null; // Assuming terminal's presence indicates color support
    }

    @Override
    public void setCurrentTextConsoleColor(ConsoleColor color) {
        if (color == null) this.textConsoleColor = ConsoleColor.WHITE;
        else this.textConsoleColor = color;
    }

    @Override
    public void setCurrentBackgroundConsoleColor(ConsoleColor color) {
        this.backgroundConsoleColor = color;
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
        this.textConsoleColor = ConsoleColor.WHITE;
        this.backgroundConsoleColor = null;
    }

    @Override
    public char[] readPassword() {
        StringBuilder input = new StringBuilder();
        int cursorPosition = 0;
        try {
            while (true) {
                int character = System.in.read();

                //Exit program
                if(character == 3){
                    clearConsole();
                    terminal.restoreTerminalMode();
                    System.exit(0);
                }

                // Handle arrow keys (escape sequence)
                if (character == 27) { // ESC
                    System.in.read(); // skip '['
                    int arrowKey = System.in.read();

                    switch (arrowKey) {
                        case 'C': // Right Arrow
                            if (cursorPosition < input.length()) {
                                System.out.print("*");
                                cursorPosition++;
                            }
                            break;
                        case 'D': // Left Arrow
                            if (cursorPosition > 0) {
                                System.out.print("\b");
                                cursorPosition--;
                            }
                            break;
                    }
                    continue;
                }

                // Handle backspace
                if (character == 8 || character == 127) { // ASCII for backspace
                    if (cursorPosition > 0) {
                        input.deleteCharAt(cursorPosition - 1);
                        cursorPosition--;

                        // Clear the current line and reprint
                        System.out.print("\b \b");
                        System.out.print(input.substring(cursorPosition).replaceAll(".", "*"));
                        System.out.print(" ");
                        System.out.print("\b".repeat(input.length() - cursorPosition + 1));
                    }
                    continue;
                }

                // Handle enter or carriage return
                if (character == '\n' || character == '\r') {
                    newLine();
                    break; // Exit the loop on Enter or Carriage Return
                }

                // Echo the character back to the terminal
                input.insert(cursorPosition, (char) character);
                cursorPosition++;
                System.out.print(input.substring(cursorPosition - 1).replaceAll(".", "*"));
                System.out.print("\b".repeat(input.length() - cursorPosition));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading user input", e);
        }
        return input.toString().toCharArray(); // Return the complete input as a string
    }

    @Override
    public void clearConsole() {
        moveCursor(0, 0);
        System.out.println("\033[2J"); // Clear the console using ANSI escape code
        System.out.flush();
    }

    @Override
    public void waitForKeyPress() {
        println("Press <enter> to continue");
        readString(); // Waits for a key press
    }
    @Override
    public String readString() {
        StringBuilder input = new StringBuilder();
        int cursorPosition = 0;
        try {
            while (true) {
                int character = System.in.read();

                //Exit program
                if(character == 3){
                    clearConsole();
                    terminal.restoreTerminalMode();
                    System.exit(0);
                }

                // Handle arrow keys (escape sequence)
                if (character == 27) { // ESC
                    System.in.read(); // skip '['
                    int arrowKey = System.in.read();

                    switch (arrowKey) {
                        case 'C': // Right Arrow
                            if (cursorPosition < input.length()) {
                                System.out.print(input.charAt(cursorPosition));
                                cursorPosition++;
                            }
                            break;
                        case 'D': // Left Arrow
                            if (cursorPosition > 0) {
                                System.out.print("\b");
                                cursorPosition--;
                            }
                            break;
                    }
                    continue;
                }

                // Handle backspace
                if (character == 8 || character == 127) { // ASCII for backspace
                    if (cursorPosition > 0) {
                        input.deleteCharAt(cursorPosition - 1);
                        cursorPosition--;

                        // Clear the current line and reprint
                        System.out.print("\b \b");
                        System.out.print(input.substring(cursorPosition));
                        System.out.print(" ");
                        System.out.print("\b".repeat(input.length() - cursorPosition + 1));
                    }
                    continue;
                }

                // Handle enter or carriage return
                if (character == '\n' || character == '\r') {
                    newLine();
                    break; // Exit the loop on Enter or Carriage Return
                }

                // Echo the character back to the terminal
                input.insert(cursorPosition, (char) character);
                cursorPosition++;
                System.out.print(input.substring(cursorPosition - 1));
                System.out.print("\b".repeat(input.length() - cursorPosition));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading user input", e);
        }

        return input.toString(); // Return the complete input as a string
    }



    @Override
    public int readInt() {
        String number = readString();
        Integer result = NumberUtils.tryParseInt(number);
        if (result == null) throw new ConsoleInputFormatException("Unable to parse number: " + number);
        return result;
    }

    @Override
    public double readDouble() {
        String number = readString();
        Double result = NumberUtils.tryParseDouble(number);
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

        System.out.print(code + s + AnsiColor.ANSI_RESET);
        newLine();
    }

    private void newLine(){
        int[] cursor = getCursorPosition();
        moveCursor(cursor[0] + 1, 0);
    }

    // Move cursor to a specific position (row, column)
    public void moveCursor(int row, int column) {
        System.out.printf("\033[%d;%dH", row, column); // Move to (row, column) using ANSI escape sequence
        System.out.flush();
    }

    /**
     * Get the current cursor position.
     *
     * @return An array where index 0 is the row, and index 1 is the column.
     */
    public int[] getCursorPosition() {
        try{
            // Send the request for the cursor position
            print("\033[6n");
            System.out.flush();

            // Read the response from the terminal
            StringBuilder response = new StringBuilder();
            char ch;
            while ((ch = (char) System.in.read()) != 'R') {
                response.append(ch);
            }

            // The response will be in the format "\033[y;x"
            String[] parts = response.toString().split(";");

            // Parse the row and column
            int row = Integer.parseInt(skipNewLines(parts[0]).substring(2)); // Skip the "\033[" part
            int column = Integer.parseInt(parts[1]);

            return new int[]{row, column};
        }catch(IOException e){
            throw new RuntimeException("Error reading cursor", e);
        }
    }

    public void clearPartialScreen() {
        System.out.print("\033[J"); // Clear from the cursor to the end of the screen
        System.out.flush();
    }



    @Override
    public Operation readUserKey() {
        StringBuilder input = new StringBuilder();
        try {
            while (true) {
                input.setLength(0);
                // Read a character and append to the input
                int firstChar = System.in.read();

                //Exit program
                if(firstChar == 3){
                    clearConsole();
                    terminal.restoreTerminalMode();
                    System.exit(0);
                }

                // If the first character is an escape character, we need to read more
                if (firstChar == 27) { // Escape character is ASCII 27
                    input.append((char) firstChar); // Append escape character
                    // Read the next two characters for the arrow key sequences
                    input.append((char) System.in.read()); // Read the '[' character
                    input.append((char) System.in.read()); // Read the direction character (A, B, C, D)
                } else {
                    input.append((char) firstChar); // Append the first character
                }

                // Check if the input matches any known operation
                Operation operation = keyMap.get(input.toString());
                if (operation != null) {
                    return operation; // Return the valid operation
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error reading user input", e);
        }
    }

    private int[] cursorPos;

    @Override
    public void saveCurrentScreen() {
        cursorPos = getCursorPosition();
    }

    @Override
    public void restoreCurrentScreen() {
        moveCursor(cursorPos[0], cursorPos[1]);
        clearPartialScreen();
    }
}
