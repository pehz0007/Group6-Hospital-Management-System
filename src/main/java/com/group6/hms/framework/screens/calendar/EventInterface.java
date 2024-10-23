package com.group6.hms.framework.screens.calendar;

import com.group6.hms.framework.screens.ConsoleInterface;

import java.time.LocalDate;
import java.time.LocalTime;

public interface EventInterface {

    LocalDate getEventDate();
    LocalTime getEventStartTime();
    LocalTime getEventEndTime();
    void displayEvent(ConsoleInterface consoleInterface);

}
