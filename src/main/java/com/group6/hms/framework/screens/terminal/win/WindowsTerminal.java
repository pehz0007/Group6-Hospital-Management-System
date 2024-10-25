package com.group6.hms.framework.screens.terminal.win;

import com.group6.hms.framework.screens.terminal.Terminal;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

public class WindowsTerminal implements Terminal {

    public static final int ENABLE_VIRTUAL_TERMINAL_PROCESSING = 0x0004, ENABLE_PROCESSED_OUTPUT = 0x0001;

    private int STD_OUTPUT_HANDLE = -11;
    private int STD_INPUT_HANDLE = -10;

    private static int ENABLE_LINE_INPUT = 0x0002;
    private static int ENABLE_PROCESSED_INPUT = 0x0001;
    private static int ENABLE_ECHO_INPUT = 0x0004;
    private static int ENABLE_MOUSE_INPUT = 0x0010;
    private static int ENABLE_WINDOW_INPUT = 0x0008;

    private static int ENABLE_VIRTUAL_TERMINAL_INPUT = 0x0200;

    private Arena a = Arena.global();

    private MemorySegment inHandle, outHandle;
    private MemorySegment inMode, outMode;

    static {
        String systemRoot = System.getenv("SystemRoot");
        System.load(systemRoot + "\\System32\\kernel32.dll");
    }

    @Override
    public void enableRawMode(){
        inHandle = processenv_h.GetStdHandle(STD_INPUT_HANDLE);
        inMode = a.allocate(ValueLayout.JAVA_INT);

        consoleapi_h.GetConsoleMode(inHandle, inMode);

        int rawInModeValue = enableRawModeForInput(inMode);
        consoleapi_h.SetConsoleMode(inHandle, rawInModeValue);

        outHandle = processenv_h.GetStdHandle(STD_OUTPUT_HANDLE);
        outMode = a.allocate(ValueLayout.JAVA_INT);

        consoleapi_h.GetConsoleMode(outHandle, outMode);

        int rawOutModeValue = enableRawModeForOutput(outMode);
        consoleapi_h.SetConsoleMode(outHandle, rawOutModeValue);
    }

    private int enableRawModeForInput(MemorySegment inMode){
        int inModeValue = inMode.get(ValueLayout.JAVA_INT, 0);
        inModeValue = inModeValue & ~(ENABLE_ECHO_INPUT | ENABLE_LINE_INPUT | ENABLE_MOUSE_INPUT | ENABLE_WINDOW_INPUT | ENABLE_PROCESSED_INPUT);
        inModeValue |= ENABLE_VIRTUAL_TERMINAL_INPUT;
        return inModeValue;
    }

    private int enableRawModeForOutput(MemorySegment outMode){
        int outModeValue = outMode.get(ValueLayout.JAVA_INT, 0);
        outModeValue |= ENABLE_VIRTUAL_TERMINAL_PROCESSING;
        outModeValue |= ENABLE_PROCESSED_OUTPUT;
        return outModeValue;
    }

    public void restoreTerminalMode(){
        //Restore console
        consoleapi_h.SetConsoleMode(inHandle, inMode.get(ValueLayout.JAVA_INT, 0));
        consoleapi_h.SetConsoleMode(outHandle, outMode.get(ValueLayout.JAVA_INT, 0));
//        a.close();
    }

}
