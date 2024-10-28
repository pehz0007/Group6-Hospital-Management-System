package com.group6.hms.framework.screens.pagination;

import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.ConsoleInterface;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

public class PrintTableUtils {

    public static void printItemAsVerticalTable(ConsoleInterface consoleInterface, Object obj) {
        ConsoleColor borderColor = ConsoleColor.PURPLE;
        ConsoleColor headerFieldColor = ConsoleColor.WHITE;
        Class<?> objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();
        int width = 50;
        consoleInterface.setCurrentTextConsoleColor(borderColor);
        printSeparator(consoleInterface, width);
        for (Field field : fields) {
            HeaderField headerField = field.getAnnotation(HeaderField.class);
            FieldRenderer renderer = getRenderer(headerField);
            boolean show = (headerField == null || headerField.show()); // Default to true if annotation is missing
            int fieldWidth = (headerField != null) ? headerField.width() : 20; // Default width if no annotation
            String fieldName = (headerField != null && !headerField.name().isEmpty())
                    ? headerField.name() : convertCamelCaseToNormal(field.getName());

            if (show) { // Only print if show is true
                field.setAccessible(true); // Allow access to private fields
                try {
                    Object value = field.get(obj); // Get the value of the field for the given object

                    // Print header (field name) as the row header
                    consoleInterface.setCurrentTextConsoleColor(headerFieldColor);
                    consoleInterface.print(String.format("%-" + fieldWidth + "s", fieldName)); // Print field name (header)
                    consoleInterface.print(": ");

                    // Print value next to the header
                    int remainingFieldWidth = width - fieldWidth;
                    renderer.initRenderObject(value, remainingFieldWidth);
                    int length = renderer.getLines();
                    renderer.render(consoleInterface, 0,remainingFieldWidth);
                    consoleInterface.println("");
                    for (int i = 1; i < length; i++) {
                        consoleInterface.print(" ".repeat(fieldWidth + 2));
                        renderer.render(consoleInterface, i, remainingFieldWidth);
                        consoleInterface.println("");
                    }
//                    String valueToPrint = value != null ? value.toString() : "null";
//                    String[] valuesToPrint = wrapText(valueToPrint, (width - fieldWidth));
//                    consoleInterface.setCurrentTextConsoleColor(valueFieldColor);
//                    consoleInterface.println(valuesToPrint[0]);
//                    for (int i = 1; i < valuesToPrint.length; i++) {
//                        consoleInterface.println(" ".repeat(fieldWidth + 2) + valuesToPrint[i]);
//                    }


                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        consoleInterface.setCurrentTextConsoleColor(borderColor);
        printSeparator(consoleInterface, width);
    }



    private static void printSeparator(ConsoleInterface consoleInterface, int width) {
        for (int j = 0; j < width; j++) {
            consoleInterface.print("-");
        }
        consoleInterface.println("+"); // End with a final "+"
    }

    private static void printSeparator(ConsoleInterface consoleInterface, Field[] fields) {
        for (Field field : fields) {
            HeaderField headerField = field.getAnnotation(HeaderField.class);
            int fieldWidth = (headerField != null) ? headerField.width() : 20; // Use annotation width or default to 20
            if(headerField != null && !headerField.show())continue;
            consoleInterface.print("+");
            for (int j = 0; j < fieldWidth; j++) {
                consoleInterface.print("-");
            }
        }
        consoleInterface.println("+"); // End with a final "+"
    }

    public static void printItemAsTable(ConsoleInterface consoleInterface, Object obj) {
        ConsoleColor borderColor = ConsoleColor.PURPLE;
        ConsoleColor headerFieldColor = ConsoleColor.WHITE;
        ConsoleColor valueFieldColor = ConsoleColor.GREEN;
        Class<?> objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();

        printHeaderFields(consoleInterface, borderColor, fields, headerFieldColor);

        // Print field values with borders
        printItemEntry(consoleInterface, fields, borderColor, valueFieldColor, obj);

        // Print bottom border
        printSeparator(consoleInterface, fields);
    }


    public static <T> void printItemsAsTable(ConsoleInterface consoleInterface, Collection<T> items) {
        if (items == null || items.isEmpty()) {
            consoleInterface.println("No items to display.");
            return;
        }

        // Get the class of the first item to define the fields
        Class<?> objClass = items.iterator().next().getClass();
        Field[] fields = objClass.getDeclaredFields();

        // Print top border
        ConsoleColor borderColor = ConsoleColor.PURPLE;
        ConsoleColor headerFieldColor = ConsoleColor.WHITE;
        ConsoleColor valueFieldColor = ConsoleColor.GREEN;

        printHeaderFields(consoleInterface, borderColor, fields, headerFieldColor);

        // Print each item's field values
        for (Object item : items) {
            printItemEntry(consoleInterface, fields, borderColor, valueFieldColor, item);
            printSeparator(consoleInterface, fields);
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

    private static void printHeaderFields(ConsoleInterface consoleInterface, ConsoleColor borderColor, Field[] fields, ConsoleColor headerFieldColor) {
        // Print top border
        consoleInterface.setCurrentTextConsoleColor(borderColor);
        printSeparator(consoleInterface, fields);

        // Print field names as headers with borders
        for (Field field : fields) {
            HeaderField headerField = field.getAnnotation(HeaderField.class);
            boolean show = (headerField == null || headerField.show()); // Default to true if annotation is missing
            int fieldWidth = (headerField != null) ? headerField.width() : 20; // Default width if no annotation
            String fieldName = (headerField != null && !headerField.name().isEmpty()) ? headerField.name() : convertCamelCaseToNormal(field.getName());

            if (show) { // Only print if show is true
                consoleInterface.setCurrentTextConsoleColor(borderColor);
                consoleInterface.print("|");
                consoleInterface.setCurrentTextConsoleColor(headerFieldColor);
                consoleInterface.print(String.format("%-" + fieldWidth + "s", fieldName)); // Print field name (header)
            }
        }
        consoleInterface.setCurrentTextConsoleColor(borderColor);
        consoleInterface.println("|"); // End the row with a border

        // Print middle border
        printSeparator(consoleInterface, fields);
    }

    private static void printItemEntry(ConsoleInterface consoleInterface, Field[] fields, ConsoleColor borderColor, ConsoleColor valueFieldColor, Object item) {
        FieldRenderer[] renderers = new FieldRenderer[fields.length];
        Field[] filteredFields = Arrays.stream(fields)
                .filter(field -> {
                    HeaderField headerField = field.getAnnotation(HeaderField.class);
                    // Default to true if annotation is missing
                    return (headerField == null || headerField.show());
                }).toArray(Field[]::new);

        int maxLine = 1;

        for (int i = 0; i < filteredFields.length; i++) {
            Field field = filteredFields[i];
            HeaderField headerField = field.getAnnotation(HeaderField.class);
            FieldRenderer renderer = getRenderer(headerField);
            renderers[i] = renderer;
            int fieldWidth = (headerField != null) ? headerField.width() : 20; // Default width if no annotation

            field.setAccessible(true); // Allow access to private fields
            try {
                Object value = field.get(item); // Get the value of the field for the given object
                renderer.initRenderObject(value, fieldWidth);
                int length = renderer.getLines();

                if(length >= maxLine)maxLine = length;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        for (int line = 0; line < maxLine; line++) {
            consoleInterface.setCurrentTextConsoleColor(borderColor);
            consoleInterface.print("|"); // End the row with a border

            for (int i = 0; i < filteredFields.length; i++){
                Field field = filteredFields[i];
                FieldRenderer renderer = renderers[i];
                HeaderField headerField = field.getAnnotation(HeaderField.class);
                int fieldWidth = (headerField != null) ? headerField.width() : 20; // Default width if no annotation
                if(line < renderer.getLines()){
                    renderer.render(consoleInterface, line, fieldWidth);
                }else{
                    consoleInterface.print(" ".repeat(fieldWidth));
                }
                consoleInterface.setCurrentTextConsoleColor(borderColor);
                consoleInterface.print("|");
            }

            consoleInterface.println("");
        }


    }

    private static FieldRenderer getRenderer(HeaderField field) {
        if(field == null) return new StringRenderer();
        try {
            return field.renderer().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
