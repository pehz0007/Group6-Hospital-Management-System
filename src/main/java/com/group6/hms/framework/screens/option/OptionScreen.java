package com.group6.hms.framework.screens.option;

import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.Screen;

import java.util.HashMap;
import java.util.Map;

/**
 * Option screen extends {@code Screen} with selectable options.
 * Example:
 * <pre>{@code
 *     public class Menu extends OptionScreen {
 *
 *         public Menu(){
 *              super("Menu");
 *              addOption(1, "Option 1");
 *              addOption(2, "Option 2");
 *         }
 *
 *      @literal @Override
 *      protected void handleOption(int optionId) {
 *         switch(optionId) {
 *             case 1 -> {
 *                 //Option 1 is selected
 *             };
 *             case 2 -> {
 *                 //Option 2 is selected
 *             }
 *         }
 *      }
 *     }
 * }</pre>
 *
 */
public abstract class OptionScreen extends Screen {

    // A map to store the available options, where the key is the option ID and the value is the option description
    private Map<Integer, Option> options = new HashMap<Integer, Option>();

    // Flag indicating whether the user is allowed to go back to the previous screen
    private boolean allowBack = true;
    private final static int BACK_ID = 0;


    /**
     * Constructor to initialize the OptionScreen with a header.
     * This constructor passes the header to the parent Screen class.
     *
     * @param title The title of the screen, which is displayed as a header.
     */
    protected OptionScreen(String title) {
        super(title);
    }

    /**
     * Lifecycle method that is called when the screen is started.
     * This method displays the options and reads user input.
     */
    @Override
    public void onStart() {
        super.onStart();
        int selectedOptionId = OptionsUtils.askOptions(consoleInterface, options);
        handleOptionOnBack(selectedOptionId);
    }

    ///
    /// CONFIGURATIONS
    ///

    /**
     * Set whether the user is allowed to go back to the previous screen.
     *
     * @param allowBack If true, the back option will be available; otherwise, it will not display the back option
     */
    protected void setAllowBack(boolean allowBack){
        this.allowBack = allowBack;
        if(allowBack && !containsOption(BACK_ID)){
            addOption(BACK_ID, "Back", ConsoleColor.WHITE);
        }else if(!allowBack && containsOption(BACK_ID)){
            removeOption(BACK_ID);
        }
    }

    ///
    /// OPTIONS
    ///

    /**
     * Adds an option to the screen that the user can select.
     *
     * @param optionID The unique ID for the option.
     * @param optionDescription A description of what the option represents.
     */
    protected void addOption(int optionID, String optionDescription){
        addOption(optionID, optionDescription, ConsoleColor.PURPLE);
    }

    /**
     * Adds an option to the screen that the user can select.
     *
     * @param optionID The unique ID for the option.
     * @param optionDescription A description of what the option represents.
     * @param consoleColor Set the color of the option.
     */
    protected void addOption(int optionID, String optionDescription, ConsoleColor consoleColor){
        if(options.containsKey(optionID))throw new OptionAlreadyExistsException("Option already exists for option, " + optionID + ": <" + options.get(optionID).optionDescription() + ">");
        options.put(optionID, new Option(optionID, optionDescription, consoleColor));
    }

    /**
     * Check if the given {@code Option} exist
     * @param optionID - the option id of the {@code Option}
     * @return true if the option exist, false otherwise
     */
    protected boolean containsOption(int optionID){
        return options.containsKey(optionID);
    }

    /**
     * Removes an option from the screen that the user can select.
     *
     * @param optionID The unique ID for the option to be removed.
     */
    protected void removeOption(int optionID){
        if(options.containsKey(optionID)){
            options.remove(optionID);
        }else{
            throw new OptionNotAvailableToBeRemoveException("Option is not available to be remove!");
        }
    }


    /**
     * Handle the selected option.
     * If the user selects '0' and back navigation is allowed, the screen navigates back.
     * Otherwise, the selected option is passed to handleOption().
     */
    protected void handleOptionOnBack(int optionId){
        if (optionId == BACK_ID && allowBack) {
            // Go back one screen
            navigateBack();
        } else {
            handleOption(optionId);
        }
    }

    /**
     * Abstract method that subclasses must implement to handle user-selected options.
     *
     * @param optionId The ID of the option selected by the user.
     */
    protected abstract void handleOption(int optionId);

    ///
    ///  DISPLAY
    ///

    /**
     * Displays the list of available options to the user.
     * If back navigation is allowed, a '0: <Back>' option is displayed as well.
     */
    protected void displayOptions() {
        setCurrentTextConsoleColor(ConsoleColor.WHITE);
        println("OPTIONS:\n");
        for (Map.Entry<Integer, Option> option : options.entrySet()) {
            setCurrentTextConsoleColor(option.getValue().color());
            println("\t" + option.getKey() + ": <" + option.getValue().optionDescription() + ">");
        }
        setCurrentBackgroundConsoleColor(null);
    }

}
