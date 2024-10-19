package com.group6.hms.framework.screens.option;

import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.ConsoleInterface;

import java.util.Map;

public class OptionsUtils {

    /**
     * Reads the user's input and return the selected option.
     * @param consoleInterface - The console interface use to display the options
     * @param options - The list of selectable option to be display
     * @return the option id of the selected option given by the user
     */
    public static int askOptions(ConsoleInterface consoleInterface, Map<Integer, Option> options){
        boolean readingOption = true;
        int selectedOption = Integer.MIN_VALUE;
        displayOptions(consoleInterface, options);
        do{
            try{
                selectedOption = readUserOption(consoleInterface, options);
                if(!options.containsKey(selectedOption)) throw new ConsoleInputNotFoundException("Option not found");
                readingOption = false;
            }catch (ConsoleInputFormatException | ConsoleInputNotFoundException e){
                consoleInterface.println(e.getMessage());
            }
        }while(readingOption);
        return selectedOption;
    }

    private static int readUserOption(ConsoleInterface consoleInterface, Map<Integer, Option> options){
        consoleInterface.setCurrentTextConsoleColor(ConsoleColor.YELLOW);
        consoleInterface.print("\nPlease select an option: ");
        int selectedOptionId = consoleInterface.readInt();
        return selectedOptionId;
    }

    /**
     * Display the list of options
     * @param consoleInterface - The console interface use to display the options
     * @param options - The list of selectable option to be display
     */
    public static void displayOptions(ConsoleInterface consoleInterface, Map<Integer, Option> options){
        consoleInterface.setCurrentTextConsoleColor(ConsoleColor.WHITE);
        consoleInterface.println("OPTIONS:\n");
        for (Map.Entry<Integer, Option> option : options.entrySet()) {
            consoleInterface.setCurrentTextConsoleColor(option.getValue().color());
            consoleInterface.println("\t" + option.getKey() + ": <" + option.getValue().optionDescription() + ">");
        }
        consoleInterface.setCurrentBackgroundConsoleColor(null);
    }


}
