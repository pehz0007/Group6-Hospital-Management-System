package com.group6.hms.framework.screens.pagination;

public class TextUtils {
    /**
     * Method to wrap long text into multiple lines based on a width limit without breaking only at spaces
     *
     * @param text The input text to be wrapped.
     * @param width The maximum width of each line.
     * @return An array of strings, where each string is a line of wrapped text.
     */
    public static String[] wrapTextWithoutBreakingAtSpaces(String text, int width) {
        int textLength = text.length();
        int lineCount = (int) Math.ceil((double) textLength / width);
        String[] wrappedLines = new String[lineCount];

        for (int i = 0; i < lineCount; i++) {
            int start = i * width;
            int end = Math.min(start + width, textLength); // Make sure we don't go out of bounds
            wrappedLines[i] = text.substring(start, end);  // Extract substring for each line
        }

        return wrappedLines; // Return the wrapped lines as an array
    }


    /**
     * Method to wrap long text into multiple lines based on a width limit
     *
     * @param text The input text to be wrapped.
     * @param width The maximum width of each line.
     * @return An array of strings, where each string is a line of wrapped text.
     */
    public static String[] wrapTextWithBreakingAtSpaces(String text, int width) {
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (line.length() + word.length() + 1 > width) { // Wrap the text
                result.append(line.toString().trim()).append("\n");
                line = new StringBuilder();
            }
            line.append(word).append(" ");
        }
        result.append(line.toString().trim()); // Add the remaining line

        return result.toString().split("\n"); // Return the wrapped lines
    }

    /**
     * Removes all newline characters ('\r' and '\n') from the given string.
     *
     * @param prefix The input string from which newline characters need to be removed.
     * @return A new string where all newline characters are removed from the input string.
     */
    public static String skipNewLines(String prefix) {
        // Use a StringBuilder to construct the result
        StringBuilder result = new StringBuilder();

        // Iterate over each character in the prefix
        for (char ch : prefix.toCharArray()) {
            // If the character is neither '\r' nor '\n', append it to the result
            if (ch != '\r' && ch != '\n') {
                result.append(ch);
            }
        }

        // Return the result as a string
        return result.toString();
    }

}
