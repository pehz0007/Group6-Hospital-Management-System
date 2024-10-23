package com.group6.hms.app;

import com.group6.hms.app.screens.MainScreen;
import com.group6.hms.framework.screens.ApplicationHandle;
import com.group6.hms.framework.screens.JLineConsoleInterface;
import com.group6.hms.framework.screens.ScreenManager;
import com.group6.hms.framework.screens.SimpleConsoleInterface;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import static java.lang.foreign.MemoryLayout.*;
import static java.lang.foreign.ValueLayout.*;

public class HMS {
        // Define constants for terminal modes
        private static final int TCSANOW = 0;
        private static final int ICANON = 2;
        private static final int ECHO = 10;

        // Memory layout for termios structure (platform-specific; usually found in <termios.h>)
        private static final MemoryLayout TERMIOS_LAYOUT = MemoryLayout.structLayout(
                ValueLayout.JAVA_INT.withName("c_iflag"),
                JAVA_INT.withName("c_oflag"),
                JAVA_INT.withName("c_cflag"),
                JAVA_INT.withName("c_lflag"),
                MemoryLayout.sequenceLayout(20, JAVA_BYTE).withName("c_cc"),
                JAVA_INT.withName("c_ispeed"),
                JAVA_INT.withName("c_ospeed")
        );

        private static MemorySegment originalTermios;
        private static MemorySegment rawTermios;

        public static void main(String[] args) throws Throwable {
            Arena globalArena = Arena.global();
            originalTermios = globalArena.allocate(TERMIOS_LAYOUT);
            rawTermios = globalArena.allocate(TERMIOS_LAYOUT);
            var linker = Linker.nativeLinker();

                // Find libc's `tcgetattr`, `tcsetattr` and `read` functions using FFM
                var libc = linker.defaultLookup();
                MethodHandle tcgetattr = linker.downcallHandle(
                        libc.find("tcgetattr").orElseThrow(),
                        FunctionDescriptor.of(JAVA_INT, JAVA_INT, ADDRESS)
                );
                MethodHandle tcsetattr = linker.downcallHandle(
                        libc.find("tcsetattr").orElseThrow(),
                        FunctionDescriptor.of(JAVA_INT, JAVA_INT, JAVA_INT, ADDRESS)
                );
                MethodHandle read = linker.downcallHandle(
                        libc.find("read").orElseThrow(),
                        FunctionDescriptor.of(JAVA_INT, JAVA_INT, ADDRESS, JAVA_LONG)
                );

                // Get current terminal attributes (save to `originalTermios`)
                int stdinFd = 0; // File descriptor for stdin
                tcgetattr.invoke(stdinFd, originalTermios);

                // Copy the original termios to `rawTermios` for modification
                MemorySegment.copy(originalTermios, 0, rawTermios, 0, TERMIOS_LAYOUT.byteSize());

                // Modify `rawTermios` to set raw mode (disabling ICANON and ECHO flags)
                var lflagOffset = TERMIOS_LAYOUT.varHandle(PathElement.groupElement("c_lflag"));
                int lflag = (int) lflagOffset.get(rawTermios);
                lflag &= ~(ICANON | ECHO); // Disable canonical mode and echo
                lflagOffset.set(rawTermios, lflag);

                // Apply the raw terminal settings using `tcsetattr`
                tcsetattr.invoke(stdinFd, TCSANOW, rawTermios);

                System.out.println("Terminal is in raw mode. Press any key to see the raw input, press 'q' to exit.");

                // Reading raw input from terminal
                MemorySegment buffer = globalArena.allocate(1); // Buffer for 1 byte
                while (true) {
                    int bytesRead = (int) read.invoke(stdinFd, buffer, 1); // Read one byte
                    if (bytesRead > 0) {
                        byte input = buffer.get(JAVA_BYTE, 0);
                        char c = (char) input;
                        System.out.println("Raw input: " + c);
                        if (c == 'q') { // Exit on 'q'
                            break;
                        }
                    }
                }

//            // Restore the terminal to its original state
//            MethodHandle tcsetattr = Linker.nativeLinker().downcallHandle(
//                    Linker.systemLookup().find("tcsetattr").orElseThrow(),
//                    FunctionDescriptor.of(JAVA_INT, JAVA_INT, JAVA_INT, ADDRESS)
//            );
//            tcsetattr.invoke(0, TCSANOW, originalTermios);
//            System.out.println("Terminal restored to normal mode.");

            }


//
//    public static void main(String[] args) {
//        ApplicationHandle appHandle = new ApplicationHandle();
//        if(System.console() != null){
//            ScreenManager sm = new ScreenManager(new MainScreen(), new JLineConsoleInterface());
//            appHandle.start(sm);
//        }else{
//            ScreenManager sm = new ScreenManager(new MainScreen(), new SimpleConsoleInterface());
//            appHandle.start(sm);
//        }
//
//    }

}
