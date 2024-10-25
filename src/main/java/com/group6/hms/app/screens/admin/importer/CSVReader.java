package com.group6.hms.app.screens.admin.importer;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.Scanner;

public class CSVReader extends BufferedReader {

    public Scanner scanner;

    public CSVReader(Reader in, int sz) {
        super(in, sz);
        scanner = new Scanner(in);
    }

    public CSVReader(Reader in) {
        super(in);
        scanner = new Scanner(in);
    }

    public boolean hasNext(){
        return scanner.hasNext();
    }

    public String[] readCSVLine(){
        return scanner.nextLine().split(",");
    }


}
