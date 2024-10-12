package com.group6.hms.framework.screens;

import java.util.HashMap;
import java.util.Map;

/**
 * Option screen extends {@code Screen} with selectable options.
 * Example:
 * <pre>{@code
 *     public class Menu extends OptionScreen {
 *
 *         public Menu(){
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
public abstract class OptionScreen extends Screen{

    // A map to store the available options, where the key is the option ID and the value is the option description
    private Map<Integer, Option> options = new HashMap<Integer, Option>();

    // Flag indicating whether the user is allowed to go back to the previous screen
    protected boolean allowBack = true;
    private final static int BACK_ID = 0;


    /**
     * Constructor to initialize the OptionScreen with a header.
     * This constructor passes the header to the parent Screen class.
     *
     * @param header The title of the screen, which is displayed as a header.
     */
    protected OptionScreen(String header) {
        super(header);
    }

    /**
     * Lifecycle method that is called when the screen is started.
     * This method displays the options and reads user input.
     */
    @Override
    public void onStart() {
        super.onStart();
        displayOptions();
        boolean readingOption = true;
        do{
            try{
                readUserOption();
                readingOption = false;
            }catch (ConsoleInputFormatException ignored){
                println("Invalid option!");
            }
        }while(readingOption);
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
        if(allowBack){
            addOption(BACK_ID, "Back", ConsoleColor.WHITE);
        }else{
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
        options.put(optionID, new Option(optionID, optionDescription, consoleColor));
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
     * Reads the user's input and handles the selected option.
     * If the user selects '0' and back navigation is allowed, the screen navigates back.
     * Otherwise, the selected option is passed to handleOption().
     */
    public void readUserOption(){
        setCurrentConsoleColor(ConsoleColor.PURPLE);
        print("\tPlease select an option: ");
        int selectedOptionId = readInt();
        handleOptionOnBack(selectedOptionId);

    }

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
    public void displayOptions() {
        for (Map.Entry<Integer, Option> option : options.entrySet()) {
            setCurrentConsoleColor(option.getValue().color());
            println(option.getKey() + ": <" + option.getValue().optionDescription() + ">");
        }
    }

}
