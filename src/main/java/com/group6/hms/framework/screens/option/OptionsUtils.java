package com.group6.hms.framework.screens.option;

import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.ConsoleInterface;
import com.group6.hms.framework.screens.InteractiveConsoleInterface;
import com.group6.hms.framework.screens.Operation;

import java.util.Map;
import java.util.NavigableMap;

public class OptionsUtils {

    /**
     * Prompts the user to select an option from the given list of options.
     *
     * If the {@code consoleInterface} implements the {@link InteractiveConsoleInterface},
     * it will invoke {@link OptionsUtils#askOptionsInteractive(InteractiveConsoleInterface, NavigableMap)}
     * to handle the option selection interactively.
     *
     * Otherwise, it will call {@link OptionsUtils#askOptionsNonInteractive(ConsoleInterface, Map)}
     * to process the options non-interactively.
     *
     * @param consoleInterface The console interface used to display and interact with the list of options.
     * @param options The map of selectable options, where the key is the option ID and the value is the option description.
     * @return The ID of the option selected by the user.
     */

    public static int askOptions(ConsoleInterface consoleInterface, NavigableMap<Integer, Option> options){
        int selectedOptionId;
        if(consoleInterface.isConsoleInteractive()){
            selectedOptionId = OptionsUtils.askOptionsInteractive((InteractiveConsoleInterface) consoleInterface, options);
        }else{
            selectedOptionId = OptionsUtils.askOptionsNonInteractive(consoleInterface, options);
        }
        return selectedOptionId;
    }

    /**
     * Reads the user's input and return the selected option.
     * @param consoleInterface - The console interface use to display the options
     * @param options - The list of selectable option to be display
     * @return the option id of the selected option given by the user
     */
    public static int askOptionsInteractive(InteractiveConsoleInterface consoleInterface, NavigableMap<Integer, Option> options){
        boolean readingOption = true;
        int selectedOption = options.firstKey();

        do{
            try{
                consoleInterface.resetColor();
                consoleInterface.saveCurrentScreen();
                displayOptionsInteractive(consoleInterface, options, selectedOption);
                consoleInterface.resetColor();
                Operation operation = readUserOperation(consoleInterface);
                switch (operation){
                    case UP -> {
                        Integer newSelectedOption = options.lowerKey(selectedOption);
                        if(newSelectedOption!=null)selectedOption = newSelectedOption;
                    }
                    case DOWN -> {
                        Integer newSelectedOption = options.higherKey(selectedOption);
                        if(newSelectedOption!=null)selectedOption = newSelectedOption;
                    }
                    case ENTER -> {
                        readingOption = false;
                    }
                }
                consoleInterface.restoreCurrentScreen();
            }catch (ConsoleInputFormatException | ConsoleInputNotFoundException e){
                consoleInterface.println(e.getMessage());
            }
        }while(readingOption);
        return selectedOption;
    }


    /**
     * Display the list of options
     * @param consoleInterface - The console interface used to display the options
     * @param options - The list of selectable options to be displayed
     * @param selectedOption - The option number that is currently selected (highlighted)
     */
    public static void displayOptionsInteractive(InteractiveConsoleInterface consoleInterface, Map<Integer, Option> options, int selectedOption) {
        consoleInterface.setCurrentTextConsoleColor(ConsoleColor.WHITE);
        consoleInterface.println("OPTIONS:\n");

        for (Map.Entry<Integer, Option> option : options.entrySet()) {
            int optionKey = option.getKey();
            Option optionValue = option.getValue();

            // Highlight the selected option
            if (optionKey == selectedOption) {
                consoleInterface.setCurrentTextConsoleColor(ConsoleColor.WHITE); // You can use any color for highlighting
                consoleInterface.setCurrentBackgroundConsoleColor(ConsoleColor.BLUE); // Background for highlight
                consoleInterface.println("\t> " + optionKey + ": <" + optionValue.optionDescription() + ">");
            } else {
                // Normal display for non-selected options
                consoleInterface.setCurrentTextConsoleColor(optionValue.color());
                consoleInterface.setCurrentBackgroundConsoleColor(null);
                consoleInterface.println("\t  " + optionKey + ": <" + optionValue.optionDescription() + ">");
            }
        }
    }

    /**
     * Reads the user's operation from the console using the provided interactive console interface.
     *
     * @param consoleInterface The interactive console interface used to display the prompt and read the user's input.
     * @return The operation performed by the user, as determined by the {@link InteractiveConsoleInterface#readUserKey()} method.
     */
    private static Operation readUserOperation(InteractiveConsoleInterface consoleInterface){
        consoleInterface.setCurrentTextConsoleColor(ConsoleColor.YELLOW);
        consoleInterface.print("\nPlease select an option by using the arrow key and <enter>: ");
        return consoleInterface.readUserKey();
    }


    /**
     * Reads the user's input and return the selected option.
     * @param consoleInterface - The console interface use to display the options
     * @param options - The list of selectable option to be display
     * @return the option id of the selected option given by the user
     */
    public static int askOptionsNonInteractive(ConsoleInterface consoleInterface, Map<Integer, Option> options){
        boolean readingOption = true;
        int selectedOption = Integer.MIN_VALUE;
        displayOptionsNonInteractive(consoleInterface, options);
        do{
            try{
                selectedOption = readUserOptionNonInteractive(consoleInterface, options);
                if(!options.containsKey(selectedOption)) throw new ConsoleInputNotFoundException("Option not found");
                readingOption = false;
            }catch (ConsoleInputFormatException | ConsoleInputNotFoundException e){
                consoleInterface.println(e.getMessage());
            }
        }while(readingOption);
        return selectedOption;
    }

    /**
     * Reads a user-selected option non-interactively and reads the user's selection as an integer input.
     *
     * @param consoleInterface The console interface used for displaying the prompt and reading the user's input.
     * @param options A map of selectable options where the key is the option ID and the value is the option itself.
     * @return The ID of the option selected by the user.
     */
    private static int readUserOptionNonInteractive(ConsoleInterface consoleInterface, Map<Integer, Option> options){
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
    public static void displayOptionsNonInteractive(ConsoleInterface consoleInterface, Map<Integer, Option> options){
        consoleInterface.setCurrentTextConsoleColor(ConsoleColor.WHITE);
        consoleInterface.println("OPTIONS:\n");
        for (Map.Entry<Integer, Option> option : options.entrySet()) {
            consoleInterface.setCurrentTextConsoleColor(option.getValue().color());
            consoleInterface.println("\t" + option.getKey() + ": <" + option.getValue().optionDescription() + ">");
        }
        consoleInterface.setCurrentBackgroundConsoleColor(null);
    }


}
