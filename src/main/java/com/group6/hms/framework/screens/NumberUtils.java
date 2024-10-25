package com.group6.hms.framework.screens;

public class NumberUtils  {

    /**
     * Tries to parse the given string to an Integer.
     *
     * @param numberStr The string to parse.
     * @return The parsed Integer, or null if parsing fails.
     */
    public static Integer tryParseInt(String numberStr) {
        if (numberStr == null || numberStr.trim().isEmpty()) {
            return null; // Return null if the input is null or empty
        }
        try {
            return Integer.parseInt(numberStr.trim());
        } catch (NumberFormatException e) {
            return null; // Return null if parsing fails
        }
    }


    /**
     * Tries to parse the given string to a Double.
     *
     * @param numberStr The string to parse.
     * @return The parsed Double, or null if parsing fails.
     */
    public static Double tryParseDouble(String numberStr) {
        if (numberStr == null || numberStr.trim().isEmpty()) {
            return null; // Return null if the input is null or empty
        }
        try {
            return Double.parseDouble(numberStr.trim());
        } catch (NumberFormatException e) {
            return null; // Return null if parsing fails
        }
    }

}
