package com.group6.hms.framework.screens;

import com.github.lalyos.jfiglet.FigletFont;

import java.util.Objects;

/**
 * This class represent the {@code Screen} which can be display on to the {@link ConsoleInterface}
 */
public abstract class Screen implements ScreenLifeCycle {

    //Reference to screen manager which is managing this screen
    //Invariant: this is set when screen is added to the screen manager
    protected ScreenManager screenManager;
    //Reference to the console interface use for displaying this screen
    //Invariant: this is set when screen is added to the screen manager
    protected ConsoleInterface consoleInterface;
    //Title of this screen which is used to display the screen header
    private final String title;

    //Whether to print the screen header, set this to false if you want a headless screen by calling setPrintHeader(false)
    private boolean printHeader = true;

    /**
     * Constructor to initialize the screen with a title.
     *
     * @param title The title of the screen to be displayed as a header.
     */
    protected Screen(String title) {
        this.title = title;
    }

    /**
     * Sets the reference to the screen manager.
     *
     * @param screenManager The screen manager responsible for this screen.
     */
    protected void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    /**
     * Sets the reference to the console interface.
     *
     * @param consoleInterface The console interface used to display this screen.
     */
    protected void setConsoleInterface(ConsoleInterface consoleInterface) {
        this.consoleInterface = consoleInterface;
    }

    /// CONFIGURATIONS

    /**
     * Configures whether the screen should print the header.
     *
     * @param printHeader true to print the header, false to disable it.
     */
    protected void setPrintHeader(boolean printHeader) {
        this.printHeader = printHeader;
    }


    /**
     * Start a new {@code Screen} with a new navigation stack
     * @param newScreen the initial screen to display
     */
    protected void newScreen(Screen newScreen) {
        screenManager.newScreen(newScreen);
    }

    /// NAVIGATIONS

    /**
     * Navigates to the specified screen.
     *
     * @param screen The next screen to navigate to.
     */
    protected void navigateToScreen(Screen screen) {
        if(Objects.isNull(screenManager)) throw new ScreenManagerNotCreatedException("Screen manager is null, try adding the screen to a screen manager");
        screenManager.navigateToScreen(screen);
    }

    /**
     * Navigates back to the previous screen.
     */
    protected void navigateBack() {
        if(Objects.isNull(screenManager)) throw new ScreenManagerNotCreatedException("Screen manager is null, try adding the screen to a screen manager");
        screenManager.navigateBack();
    }

    /**
     * Do not display the screen again and will instead end the application once the screen exit
     */
    protected void doNotLoopScreen(){
        screenManager.doNotLoopScreen();
    }

    /// SCREEN LIFECYCLE
    @Override
    public void onStart() {
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onDisplay() {

    }

    @Override
    public void onFinish() {
    }

    @Override
    public void onNextScreen(Screen nextScreen) {
    }

    @Override
    public void onBack(Screen backFromScreen) {
    }


    /// READING & PRINTING

    /**
     * Sets the current text color in the console. The next print operation
     * will use the color specified by {@code color}.
     *
     * @param color The {@link ConsoleColor} to set the text to.
     */
    public void setCurrentTextConsoleColor(ConsoleColor color) {
        consoleInterface.setCurrentTextConsoleColor(color);
    }

    /**
     * Sets the current background color in the console. The next print operation
     * will use the color as background specified by {@code color}.
     *
     * @param color The {@link ConsoleColor} to set the text to.
     */
    public void setCurrentBackgroundConsoleColor(ConsoleColor color) {
        consoleInterface.setCurrentBackgroundConsoleColor(color);
    }

    /**
     * Wait for a key press from the current console.
     */
    protected void waitForKeyPress() {
        consoleInterface.waitForKeyPress();
    }

    /**
     * Read a {@code String} from the current console.
     * @return The string entered by the user.
     */
    protected String readString() {
        return consoleInterface.readString();
    }

    /**
     * Read a {@code Double} from the current console.
     * @return The double entered by the user.
     */
    protected double readDouble() {
        return consoleInterface.readDouble();
    }

    /**
     * Read a {@code int} from the current console.
     * @return The int entered by the user.
     */
    protected int readInt() {
        return consoleInterface.readInt();
    }

    /**
     * Print a {@code String} to the current console.
     */
    protected void print(String s) {
        consoleInterface.print(s);
    }

    /**
     * Print a {@code String} to the current console with a newline.
     */
    protected void println(String s) {
        consoleInterface.println(s);
    }

    ///  DISPLAY

    /**
     * Displays the screen header. This can be disabled using {@link #setPrintHeader(boolean)}.
     * The header includes a title centered and wrapped with borders.
     */
    protected void displayHeader(int totalWidth) {
        if (!printHeader) return;
        consoleInterface.setCurrentTextConsoleColor(ConsoleColor.CYAN);

        // Generate the ASCII art for the title
        String asciiTitle = FigletFont.convertOneLine(title);

        // Split the generated ASCII art into lines
        String[] asciiTitleLines = asciiTitle.split("\n");

        // Determine the longest line in the ASCII art for proper border width
        int maxLength = 0;
        for (String line : asciiTitleLines) {
            if (line.length() > maxLength) {
                maxLength = line.length();
            }
        }

        // If totalWidth is less than the longest line, use maxLength + 4 as totalWidth
        totalWidth = Math.max(totalWidth, maxLength + 4);

        // Print top border
        println("=".repeat(totalWidth));

        // Print each line of ASCII art with left and right borders
        for (String line : asciiTitleLines) {
            // Center the line within the total width
            int padding = (totalWidth - 2 - line.length()) / 2;
            String paddedLine = " ".repeat(padding) + line + " ".repeat(padding);

            // Ensure the line fits in case of odd totalWidth
            paddedLine = paddedLine.length() > totalWidth - 2
                    ? paddedLine.substring(0, totalWidth - 2)
                    : paddedLine;

            println("|" + paddedLine + "|");
        }

        // Print bottom border
        println("=".repeat(totalWidth));

    }




}