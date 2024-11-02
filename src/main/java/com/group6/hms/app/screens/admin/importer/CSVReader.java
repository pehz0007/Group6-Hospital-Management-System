package com.group6.hms.app.screens.admin.importer;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.Scanner;

/**
 * The {@code CSVReader} class extends {@link BufferedReader} and provides functionality
 * to read CSV (Comma-Separated Values) files line by line.
 */
public class CSVReader extends BufferedReader {

    /** Scanner used to read lines from the input. */
    public Scanner scanner;

    /**
     * Constructs a {@code CSVReader} with the specified input reader and buffer size.
     *
     * @param in the {@link Reader} to read data from
     * @param sz the size of the buffer
     */
    public CSVReader(Reader in, int sz) {
        super(in, sz);
        scanner = new Scanner(in);
    }

    /**
     * Constructs a {@code CSVReader} with the specified input reader.
     *
     * @param in the {@link Reader} to read data from
     */
    public CSVReader(Reader in) {
        super(in);
        scanner = new Scanner(in);
    }

    /**
     * Checks if there is another line of input to read.
     *
     * @return {@code true} if there is another line, {@code false} otherwise
     */
    public boolean hasNext(){
        return scanner.hasNext();
    }

    /**
     * Reads the next line from the CSV input and splits it into an array of strings.
     *
     * @return an array of {@code String} containing the values from the CSV line
     */
    public String[] readCSVLine(){
        return scanner.nextLine().split(",");
    }
}
