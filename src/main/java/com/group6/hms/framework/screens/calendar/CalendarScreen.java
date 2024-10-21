package com.group6.hms.framework.screens.calendar;

import com.google.common.collect.Multimap;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.InteractiveConsoleInterface;
import com.group6.hms.framework.screens.Operation;
import com.group6.hms.framework.screens.Screen;
import com.group6.hms.framework.screens.option.Option;
import com.group6.hms.framework.screens.option.OptionScreen;
import com.group6.hms.framework.screens.option.OptionsUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CalendarScreen<Event extends EventInterface> extends OptionScreen {

    // Store events
    private final Multimap<LocalDate, Event> events;

    // Current day navigation state
    private LocalDate currentDate = LocalDate.now();

    private static final int UP = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;
    private static final int RIGHT = 4;

    private boolean stopDisplayingCalendar;

    /**
     * Constructor to initialize the screen with a title.
     *
     * @param title The title of the screen to be displayed as a header.
     */
    public CalendarScreen(String title, Multimap<LocalDate, Event> events) {
        super(title);
        this.events = events;
        setAllowBack(true);
        addOption(UP, "UP");
        addOption(DOWN, "DOWN");
        addOption(LEFT, "LEFT");
        addOption(RIGHT, "RIGHT");
    }

    @Override
    public void onDisplay() {
        // Event loop to read key presses and navigate calendar
        stopDisplayingCalendar = false;
        while (!stopDisplayingCalendar) {
            // Wait for a key press and retrieve the binding
            if (consoleInterface.isConsoleInteractive()) {
                InteractiveConsoleInterface interactiveConsoleInterface = (InteractiveConsoleInterface) consoleInterface;
                interactiveConsoleInterface.saveCurrentScreen();

                // Display the current calendar state
                displayCalendar();

                Operation key = interactiveConsoleInterface.readUserKey();

                // Handle key press
                switch (key) {
                    case UP: // Up Arrow (previous week)
                        currentDate = currentDate.minusWeeks(1);
                        break;
                    case DOWN: // Down Arrow (next week)
                        currentDate = currentDate.plusWeeks(1);
                        break;
                    case RIGHT: // Right Arrow (next day)
                        currentDate = currentDate.plusDays(1);
                        break;
                    case LEFT: // Left Arrow (previous day)
                        currentDate = currentDate.minusDays(1);
                        break;
                    case EXIT:
                        stopDisplayingCalendar = true;
                        break;
                }
                interactiveConsoleInterface.restoreCurrentScreen();
            }else{
                // Display the current calendar state
                displayCalendar();
                super.onDisplay();
            }

        }
    }

    @Override
    public void onNextScreen(Screen nextScreen) {
        super.onNextScreen(nextScreen);
        stopDisplayingCalendar = true;
    }

    @Override
    protected void handleOption(int optionId) {
        switch (optionId){
            case UP -> currentDate = currentDate.minusWeeks(1);
            case DOWN -> currentDate = currentDate.plusWeeks(1);
            case RIGHT -> currentDate = currentDate.plusDays(1);
            case LEFT -> currentDate = currentDate.minusDays(1);
        }
    }

    // Display the calendar for the current date and highlight the selected day
    private void displayCalendar() {
        setCurrentTextConsoleColor(ConsoleColor.WHITE);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd");

        println("Calendar: " + currentDate.format(dateFormatter));

        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
        int dayOfWeekOffset = firstDayOfMonth.getDayOfWeek().getValue() % 7;

        // Print day headers
        println(" Su  Mo  Tu  We  Th  Fr  Sa");

        // Print leading spaces
        for (int i = 0; i < dayOfWeekOffset; i++) {
            print("    ");
        }

        // Print all days of the month
        LocalDate day = firstDayOfMonth;
        while (day.getMonth() == currentDate.getMonth()) {
            String dayStr = day.format(dayFormatter);

            // Highlight the current day
            if (day.equals(currentDate)) {
                print("[");
                print(dayStr);
                print("]");
            } else {
                print(" " + dayStr + " ");
            }

            // Newline after Sunday (7th day of the week)
            if ((dayOfWeekOffset + day.getDayOfMonth()) % 7 == 0) {
                println("");
            }
            day = day.plusDays(1);
        }
        println("");

        // Display event if available for the selected day
        Collection<Event> eventsOnSelectedDay = events.get(currentDate);
        if (!eventsOnSelectedDay.isEmpty()) {
            for(Event event : eventsOnSelectedDay) {
                println("=".repeat(30));
                event.displayEvent(consoleInterface);
            }
        } else {
            println("No events for this day.");
        }
    }
}
