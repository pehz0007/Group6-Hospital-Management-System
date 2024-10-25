package com.group6.hms.framework.screens.terminal.unix;

import com.group6.hms.framework.screens.terminal.Terminal;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

public class UnixTerminal implements Terminal {
    private int SYSTEM_IN_FD = 0;
    private int TCSAFLUSH = 2;
    private Arena arena = Arena.global();
    private MemorySegment termiosStruct, originalTermiosStruct;


    public UnixTerminal() {
        termiosStruct = com.group6.hms.framework.screens.terminal.mac.termios.allocate(arena);
        originalTermiosStruct = termios.allocate(arena);
        int rc = com.group6.hms.framework.screens.terminal.mac.termios_h.tcgetattr(SYSTEM_IN_FD, termiosStruct);
        originalTermiosStruct.copyFrom(termiosStruct);
        if(rc != 0){
            System.err.println("error occured");
            MemorySegment err = com.group6.hms.framework.screens.terminal.mac.errno_h.__error();  // This gets the errno value
            System.err.println("Error no: " + err.get(ValueLayout.JAVA_INT, 0));
        }
    }

    @Override
    public void enableRawMode() {
        com.group6.hms.framework.screens.terminal.mac.termios_h.cfmakeraw(termiosStruct);
        int rc = com.group6.hms.framework.screens.terminal.mac.termios_h.tcsetattr(SYSTEM_IN_FD, TCSAFLUSH, termiosStruct);
        if(rc != 0){
            System.err.println("error occured");
        }
    }

    @Override
    public void restoreTerminalMode() {
        int rc = termios_h.tcsetattr(SYSTEM_IN_FD, TCSAFLUSH, originalTermiosStruct);
        if(rc != 0){
            System.err.println("error occured");
        }
    }
}
