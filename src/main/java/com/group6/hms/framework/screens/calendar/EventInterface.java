package com.group6.hms.framework.screens.calendar;

import com.group6.hms.framework.screens.ConsoleInterface;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * EventInterface defines methods for accessing and displaying event details which will be used by the {@code CalendarScreen}.
 */
public interface EventInterface {


    /**
     * Retrieves the date of the event.
     *
     * @return the LocalDate representing the date of the event.
     */
    LocalDate getEventDate();
    /**
     * Retrieves the start time of the event.
     *
     * @return the LocalTime representing the start time of the event.
     */
    LocalTime getEventStartTime();
    /**
     * Retrieves the end time of the event.
     *
     * @return the LocalTime representing the end time of the event.
     */
    LocalTime getEventEndTime();
    /**
     * Displays the event details on the specified console.
     *
     * @param consoleInterface the console interface used to display the event details
     */
    void displayEvent(ConsoleInterface consoleInterface);

}
