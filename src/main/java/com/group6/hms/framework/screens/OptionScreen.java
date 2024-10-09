package com.group6.hms.framework.screens;

import java.util.HashMap;
import java.util.Map;

public abstract class OptionScreen extends Screen{

    private Map<Integer, String> options = new HashMap<Integer, String>();

    private boolean allowBack = true;

    protected OptionScreen(String header) {
        super(header);
    }

    @Override
    public void onStart() {
        super.onStart();
        displayOptions();
        readUserOption();
    }

    ///
    /// CONFIGURATIONS
    ///

    protected void setAllowBack(boolean allowBack){
        this.allowBack = false;
    }

    ///
    /// OPTIONS
    ///
    protected void addOption(int optionID, String optionDescription){
        options.put(optionID, optionDescription);
    }

    public void readUserOption(){
        print("\tPlease select an option: ");
        int selectedOptionId = readInt();
        // Go back one screen
        if(allowBack && selectedOptionId == 0) navigateBack();
        handleOption(selectedOptionId);
    }

    protected abstract void handleOption(int optionId);

    ///
    ///  DISPLAY
    ///
    public void displayOptions() {
        if(allowBack){
            screenInputInterface.setCurrentConsoleColor(ConsoleColor.WHITE);
            println(0 + ": <Back>");
        }
        screenInputInterface.setCurrentConsoleColor(ConsoleColor.PURPLE);
        for (Map.Entry<Integer, String> option : options.entrySet()) {
            println(option.getKey() + ": <" + option.getValue() + ">");
        }
    }

}
