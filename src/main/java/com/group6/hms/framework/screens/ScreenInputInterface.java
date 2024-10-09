package com.group6.hms.framework.screens;

public interface ScreenInputInterface {

    boolean isColorSupported();
    void setCurrentConsoleColor(ConsoleColor color);

    String readString();
    int readInt();
    double readDouble();

    void print(String s);
    void println(String s);

}
