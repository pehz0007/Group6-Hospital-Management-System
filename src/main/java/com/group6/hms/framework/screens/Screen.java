package com.group6.hms.framework.screens;

public abstract class Screen implements ScreenLifeCycle {

    protected ScreenManager screenManager;
    protected ScreenInputInterface screenInputInterface;
    private String header;

    private boolean printHeader = true;


    protected Screen(String header){
        this.screenInputInterface = new SimpleScreenInputInterface();
        this.header = header;
    }

    protected void setScreenManager(ScreenManager screenManager){
        this.screenManager = screenManager;
    }

    ///
    /// CONFIGURATIONS
    ///
    protected void setPrintHeader(boolean printHeader){
        this.printHeader = printHeader;
    }

    ///
    /// NAVIGATIONS
    ///
    protected void navigateToScreen(Screen screen){
        screenManager.navigateToScreen(screen);
    }

    protected void navigateBack(){
        screenManager.navigateBack();
    }

    ///
    /// SCREEN LIFECYCLE
    ///
    @Override
    public void onStart() {}

    @Override
    public void onFinish() {}

    @Override
    public void onNextScreen(Screen nextScreen) {}

    @Override
    public void onBack(Screen backFromScreen) {}




    ///
    /// READING & PRINTING
    ///
    protected String readString(){
        return screenInputInterface.readString();
    }

    protected int readInt(){
        return screenInputInterface.readInt();
    }

    protected void print(String s){
        screenInputInterface.print(s);
    }

    protected void println(String s){
        screenInputInterface.println(s);
    }

    ///
    ///  DISPLAY
    ///
    public void displayHeader(){
        if(!printHeader)return;
        screenInputInterface.setCurrentConsoleColor(ConsoleColor.CYAN);
        int headerLength = header.length();
        int totalWidth = headerLength + 20;

        // Top border
        println("=".repeat(totalWidth));

        // Format the title centered within the total width
        String title = String.format("%" + (totalWidth / 2 + headerLength / 2) + "s", header);
        println(title);

        // Bottom border
        println("=".repeat(totalWidth));
    }


}