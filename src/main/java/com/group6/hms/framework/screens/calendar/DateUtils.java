package com.group6.hms.framework.screens.calendar;

import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.ConsoleInterface;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {

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
