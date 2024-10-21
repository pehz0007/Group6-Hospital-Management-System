package com.group6.hms.framework.screens.calendar;

import com.group6.hms.framework.screens.ConsoleInterface;

import java.time.LocalDateTime;

public interface EventInterface {

    LocalDateTime getEventDateTime();
    void displayEvent(ConsoleInterface consoleInterface);

}
