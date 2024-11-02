package com.group6.hms.framework.screens.calendar;

import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.ConsoleInterface;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for date-related operations.
 */
public class DateUtils {

    /**
     * Reads a date from the console input using the specified format.
     * The method will prompt the user to enter a date until a valid date is provided.
     * The color of the console text changes to indicate prompts and errors.
     *
     * @param consoleInterface The interface used to interact with the console for reading input and displaying messages.
     * @return The {@code LocalDate} object representing the date entered by the user.
     */
    public static LocalDate readDate(ConsoleInterface consoleInterface) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = null;

        while (date == null) { // Loop until a valid date is parsed
            consoleInterface.setCurrentTextConsoleColor(ConsoleColor.YELLOW);
            consoleInterface.print("Enter a date (yyyy-MM-dd):");
            String dateString = consoleInterface.readString();

            try {
                date = LocalDate.parse(dateString, formatter);
            } catch (DateTimeParseException e) {
                consoleInterface.setCurrentTextConsoleColor(ConsoleColor.RED);
                consoleInterface.print("Invalid date format. Please try again.");
            }
        }

        return date;
    }
}
