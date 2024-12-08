package com.group6.hms.framework.screens;

/**
 * ConsoleInterface defines methods for interacting with a console.
 * A console interface can be implemented to interact with a terminal, stream or even a remote terminal.
 *
 * See {@link SimpleConsoleInterface}
 */
public interface ConsoleInterface {

    /**
     * Return if this current console support color output
     * @return true if color output is supported, false otherwise.
     */
    boolean isColorSupported();

    /**
     * Return if this current console support interactive console
     * @return true if interactive console is supported, false otherwise.
     */
    default boolean isConsoleInteractive(){
        return this instanceof InteractiveConsoleInterface;
    }

    /**
     * Sets the current text color in the console. The next print operation
     * will use the color specified by {@code color}.
     *
     * @param color The {@link ConsoleColor} to set the text to.
     */
    void setCurrentTextConsoleColor(ConsoleColor color);

    /**
     * Sets the current background color in the console. The next print operation
     * will use the color as background specified by {@code color}.
     *
     * @param color The {@link ConsoleColor} to set the text to.
     */
    void setCurrentBackgroundConsoleColor(ConsoleColor color);

    /**
     * Gets the current text color in the console. The next print operation
     * will use the color specified by {@code color}.
     *
     * @return the color of the current text {@link ConsoleColor}
     */
    ConsoleColor getCurrentTextConsoleColor();

    /**
     * Sets the current background color in the console. The next print operation
     * will use the color as background specified by {@code color}.
     *
     * @return the color of the current background {@link ConsoleColor}
     */
    ConsoleColor getCurrentBackgroundConsoleColor();

    /**
     * Reset the console color back to default
     */
    void resetColor();

    /**
     * Read a {@code char[]} from the current console and the .
     * @return The char[] entered by the user.
     */
    char[] readPassword();

    /**
     * Clear the console
     */
    void clearConsole();

    /**
     * Wait for a key press from the current console.
     */
    void waitForKeyPress();

    /**
     * Read a {@code String} from the current console.
     * @return The string entered by the user.
     */
    String readString();

    /**
     * Read a {@code int} from the current console.
     * @return The int entered by the user.
     */
    int readInt();

    /**
     * Read a {@code Double} from the current console.
     * @return The double entered by the user.
     */
    double readDouble();

    /**
     * Print a {@code String} to the current console.
     */
    void print(String s);

    /**
     * Print a {@code String} to the current console with a newline.
     */
    void println(String s);


}
