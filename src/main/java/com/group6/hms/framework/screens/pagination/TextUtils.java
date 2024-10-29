package com.group6.hms.framework.screens.pagination;

public class TextUtils {
    // Method to wrap long text into multiple lines based on a width limit without breaking only at spaces
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


    // Method to wrap long text into multiple lines based on a width limit
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

}
