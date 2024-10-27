package com.group6.hms.framework.screens.terminal.mac;

import com.group6.hms.framework.screens.terminal.Terminal;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

public class MacOsTerminal implements Terminal {
    private int SYSTEM_IN_FD = 0;
    private int TCSAFLUSH = 2;
    private Arena arena = Arena.global();
    private MemorySegment termiosStruct, originalTermiosStruct;


    public MacOsTerminal() {
        termiosStruct = termios.allocate(arena);
        originalTermiosStruct = termios.allocate(arena);
        int rc = termios_h.tcgetattr(SYSTEM_IN_FD, termiosStruct);
        originalTermiosStruct.copyFrom(termiosStruct);
        if(rc != 0){
            System.err.println("error occured");
            MemorySegment err = errno_h.__error();  // This gets the errno value
            System.err.println("Error no: " + err.get(ValueLayout.JAVA_INT, 0));
        }
    }

    @Override
    public void enableRawMode() {
        termios_h.cfmakeraw(termiosStruct);
        int rc = termios_h.tcsetattr(SYSTEM_IN_FD, TCSAFLUSH, termiosStruct);
        if(rc != 0){
            System.err.println("error occured");
            MemorySegment err = errno_h.__error();  // This gets the errno value
            System.err.println("Error no: " + err.get(ValueLayout.JAVA_INT, 0));
        }
    }

    @Override
    public void restoreTerminalMode() {
        int rc = termios_h.tcsetattr(SYSTEM_IN_FD, TCSAFLUSH, originalTermiosStruct);
        if(rc != 0){
            System.err.println("error occured");
            MemorySegment err = errno_h.__error();  // This gets the errno value
            System.err.println("Error no: " + err.get(ValueLayout.JAVA_INT, 0));
        }
    }
}
