package com.group6.hms.app.screens;

import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.HeaderField;
import com.group6.hms.framework.screens.Screen;

import java.lang.reflect.Field;
import java.util.Collection;

public class PrintTableUtils {
    private static void printSeparator(Screen screen, Field[] fields) {
        for (Field field : fields) {
            HeaderField headerField = field.getAnnotation(HeaderField.class);
            int fieldWidth = (headerField != null) ? headerField.width() : 20; // Use annotation width or default to 20
            screen.print("+");
            for (int j = 0; j < fieldWidth; j++) {
                screen.print("-");
            }
        }
        screen.println("+"); // End with a final "+"
    }

    public static void printItemAsTable(Screen screen, Object obj) {
        ConsoleColor borderColor = ConsoleColor.PURPLE;
        ConsoleColor headerFieldColor = ConsoleColor.WHITE;
        ConsoleColor valueFieldColor = ConsoleColor.GREEN;
        Class<?> objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();

        printHeaderFields(screen, borderColor, fields, headerFieldColor);

        // Print field values with borders
        printItemEntry(screen, fields, borderColor, valueFieldColor, obj);

        // Print bottom border
        printSeparator(screen, fields);
    }


    public static void printItemsAsTable(Screen screen, Collection<?> items) {
        if (items == null || items.isEmpty()) {
            screen.println("No items to display.");
            return;
        }

        // Get the class of the first item to define the fields
        Class<?> objClass = items.iterator().next().getClass();
        Field[] fields = objClass.getDeclaredFields();

        // Print top border
        ConsoleColor borderColor = ConsoleColor.PURPLE;
        ConsoleColor headerFieldColor = ConsoleColor.WHITE;
        ConsoleColor valueFieldColor = ConsoleColor.GREEN;

        printHeaderFields(screen, borderColor, fields, headerFieldColor);

        // Print each item's field values
        for (Object item : items) {
            printItemEntry(screen, fields, borderColor, valueFieldColor, item);
            printSeparator(screen, fields);
        }

//        // Print bottom border
//        printSeparator(screen, fields);
    }

    private static String convertCamelCaseToNormal(String camelCase){
        if (camelCase == null || camelCase.isEmpty()) {
            return camelCase; // Return null or empty string as is
        }

        // Use regex to insert a space before each uppercase letter (except the first one)
        String normalString = camelCase.replaceAll("([a-z])([A-Z])", "$1 $2");

        // Split the string into words, capitalize each word, and join them back together
        String[] words = normalString.split(" ");
        StringBuilder capitalizedString = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                capitalizedString.append(Character.toUpperCase(word.charAt(0))); // Capitalize the first letter
                capitalizedString.append(word.substring(1).toLowerCase()); // Append the rest in lowercase
                capitalizedString.append(" "); // Add a space after each word
            }
        }

        // Remove the trailing space and return the result
        return capitalizedString.toString().trim();
    }

    private static void printHeaderFields(Screen screen, ConsoleColor borderColor, Field[] fields, ConsoleColor headerFieldColor) {
        // Print top border
        screen.setCurrentTextConsoleColor(borderColor);
        printSeparator(screen, fields);

        // Print field names as headers with borders
        for (Field field : fields) {
            HeaderField headerField = field.getAnnotation(HeaderField.class);
            boolean show = (headerField == null || headerField.show()); // Default to true if annotation is missing
            int fieldWidth = (headerField != null) ? headerField.width() : 20; // Default width if no annotation
            String fieldName = (headerField != null && !headerField.name().isEmpty()) ? headerField.name() : convertCamelCaseToNormal(field.getName());

            if (show) { // Only print if show is true
                screen.setCurrentTextConsoleColor(borderColor);
                screen.print("|");
                screen.setCurrentTextConsoleColor(headerFieldColor);
                screen.print(String.format("%-" + fieldWidth + "s", fieldName)); // Print field name (header)
            }
        }
        screen.setCurrentTextConsoleColor(borderColor);
        screen.println("|"); // End the row with a border

        // Print middle border
        printSeparator(screen, fields);
    }

    private static void printItemEntry(Screen screen, Field[] fields, ConsoleColor borderColor, ConsoleColor valueFieldColor, Object item) {
        for (Field field : fields) {
            HeaderField headerField = field.getAnnotation(HeaderField.class);
            boolean show = (headerField == null || headerField.show()); // Default to true if annotation is missing
            int fieldWidth = (headerField != null) ? headerField.width() : 20; // Default width if no annotation

            if (show) { // Only print if show is true
                field.setAccessible(true); // Allow access to private fields
                try {
                    Object value = field.get(item); // Get the value of the field for the given object

                    screen.setCurrentTextConsoleColor(borderColor);
                    screen.print("|");
                    screen.setCurrentTextConsoleColor(valueFieldColor);
                    screen.print(String.format("%-" + fieldWidth + "s", value != null ? value.toString() : "null")); // Print value
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        screen.setCurrentTextConsoleColor(borderColor);
        screen.println("|"); // End the row with a border
    }
}
