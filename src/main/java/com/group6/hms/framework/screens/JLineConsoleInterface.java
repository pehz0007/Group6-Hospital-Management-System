package com.group6.hms.framework.screens;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import com.group6.hms.framework.screens.option.ConsoleInputFormatException;
import org.fusesource.jansi.Ansi;
import org.jline.keymap.BindingReader;
import org.jline.keymap.KeyMap;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.security.InvalidParameterException;

import static org.jline.keymap.KeyMap.key;

public class JLineConsoleInterface implements InteractiveConsoleInterface {
    private Terminal terminal;
    private LineReader reader;
    private BindingReader bindingReader;
    private static KeyMap<Operation> keyMap = new KeyMap<>();

    private Ansi.Color textColor = Ansi.Color.WHITE;
    private Ansi.Color backgroundColor = Ansi.Color.DEFAULT;
    public JLineConsoleInterface() {
        try {
            terminal = TerminalBuilder.terminal();
            terminal.puts(InfoCmp.Capability.enter_ca_mode);
            terminal.puts(InfoCmp.Capability.keypad_xmit);
            reader = LineReaderBuilder.builder().terminal(terminal).build();
            bindingReader = new BindingReader(terminal.reader());
            terminal.enterRawMode();

            // Add navigation bindings for arrow keys
//            keyMap.bind(Operation.UP, key(terminal, InfoCmp.Capability.key_up));
//            keyMap.bind(Operation.DOWN, key(terminal, InfoCmp.Capability.key_down));
//            keyMap.bind(Operation.LEFT, key(terminal, InfoCmp.Capability.key_left));
//            keyMap.bind(Operation.RIGHT, key(terminal, InfoCmp.Capability.key_right));
            keyMap.bind(Operation.UP, "\033[A");    // Escape sequence for UP arrow
            keyMap.bind(Operation.DOWN, "\033[B");  // Escape sequence for DOWN arrow
            keyMap.bind(Operation.LEFT, "\033[D");  // Escape sequence for LEFT arrow
            keyMap.bind(Operation.RIGHT, "\033[C"); // Escape sequence for RIGHT arrow
            keyMap.bind(Operation.ENTER, key(terminal, InfoCmp.Capability.carriage_return));
            keyMap.bind(Operation.EXIT, "q");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isColorSupported() {
        return terminal.getType() != null;
    }

    @Override
    public void setCurrentTextConsoleColor(ConsoleColor color) {
        if(color == null) this.textColor = Ansi.Color.WHITE;
        else this.textColor = toAnsiColor(color);
    }

    @Override
    public void setCurrentBackgroundConsoleColor(ConsoleColor color) {
        if(color == null) this.backgroundColor = Ansi.Color.DEFAULT;
        else this.backgroundColor = toAnsiColor(color);
    }

    @Override
    public ConsoleColor getCurrentTextConsoleColor() {
        return toConsoleColor(textColor);
    }

    @Override
    public ConsoleColor getCurrentBackgroundConsoleColor() {
        return toConsoleColor(backgroundColor);
    }

    @Override
    public void resetColor() {
        this.textColor = Ansi.Color.WHITE;
        this.backgroundColor = Ansi.Color.DEFAULT;
    }

    @Override
    public char[] readPassword() {
        try {
            return reader.readLine("", '*').toCharArray();
        } catch (UserInterruptException e) {
            return new char[0]; // Return an empty char array if interrupted
        }
    }

    @Override
    public void clearConsole() {
        terminal.puts(InfoCmp.Capability.clear_screen);
        terminal.flush();
    }

    @Override
    public void waitForKeyPress() {
        println("Press <enter> to continue");
        readString(); // Waits for a key press
    }

    @Override
    public String readString() {
        return reader.readLine();
    }

    @Override
    public int readInt() {
        String number = readString();
        Integer result = Ints.tryParse(number);
        if (result == null) throw new ConsoleInputFormatException("Unable to parse number: " + number);
        return result;
    }

    @Override
    public double readDouble() {
        String number = readString();
        Double result = Doubles.tryParse(number);
        if (result == null) throw new ConsoleInputFormatException("Unable to parse number: " + number);
        return result;
    }

    @Override
    public void print(String s) {
        String coloredMessage = Ansi.ansi().fgBright(textColor).bg(backgroundColor).a(s).reset().toString();
        terminal.writer().write(coloredMessage);
        terminal.flush();
    }

    @Override
    public void println(String s) {
        String coloredMessage = Ansi.ansi().fgBright(textColor).bg(backgroundColor).a(s).reset().toString();
        terminal.writer().write(coloredMessage);
        terminal.writer().write("\n");
        terminal.flush();
    }

    private Ansi.Color toAnsiColor(ConsoleColor color) {
        return switch (color) {
            case BLACK -> Ansi.Color.BLACK;
            case RED -> Ansi.Color.RED;
            case GREEN -> Ansi.Color.GREEN;
            case YELLOW -> Ansi.Color.YELLOW;
            case BLUE -> Ansi.Color.BLUE;
            case PURPLE -> Ansi.Color.MAGENTA;
            case CYAN -> Ansi.Color.CYAN;
            case WHITE -> Ansi.Color.WHITE;
        };
    }

    private ConsoleColor toConsoleColor(Ansi.Color ansiColor) {
        return switch (ansiColor) {
            case BLACK -> ConsoleColor.BLACK;
            case RED -> ConsoleColor.RED;
            case GREEN -> ConsoleColor.GREEN;
            case YELLOW -> ConsoleColor.YELLOW;
            case BLUE -> ConsoleColor.BLUE;
            case MAGENTA -> ConsoleColor.PURPLE;
            case CYAN -> ConsoleColor.CYAN;
            case WHITE -> ConsoleColor.WHITE;
            default -> throw new InvalidParameterException("Unknown color!");
        };
    }

    // Move cursor to a specific position (row, column)
    private void moveCursor(int row, int column) {
        terminal.writer().write(String.format("\033[%d;%dH", row, column)); // Move to (row, column)
        terminal.writer().flush();
    }

    /**
     * Get the current cursor position.
     * @return An array where index 0 is the row, and index 1 is the column.
     * @throws IOException if an I/O error occurs.
     */
    private int[] getCursorPosition() throws IOException {
        // Send the request for the cursor position
        terminal.writer().write("\033[6n");
        terminal.writer().flush();

        // Read the response from the terminal
        StringBuilder response = new StringBuilder();
        char ch;
        while ((ch = (char) terminal.reader().read()) != 'R') {
            response.append(ch);
        }

        // The response will be in the format "\033[y;x"
        String[] parts = response.toString().split(";");

        // Parse the row and column
        int row = Integer.parseInt(parts[0].substring(2)); // Skip the "\033[" part
        int column = Integer.parseInt(parts[1]);

        return new int[]{row, column};
    }

    private void clearPartialScreen() {
        terminal.writer().write("\033[J"); // Clear from the cursor to the end of the screen
        terminal.writer().flush();
    }

    @Override
    public Operation readUserKey() {
        Operation operation = bindingReader.readBinding(keyMap);
        return operation;
    }

    private int[] cursorPos;

    @Override
    public void saveCurrentScreen() {
        try {
            cursorPos = getCursorPosition();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void restoreCurrentScreen() {
        moveCursor(cursorPos[0], cursorPos[1]);
        clearPartialScreen();
    }
}
