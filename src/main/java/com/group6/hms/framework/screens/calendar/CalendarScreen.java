package com.group6.hms.framework.screens.calendar;

import com.group6.hms.app.screens.patient.ViewAvailableDoctorScreen;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.InteractiveConsoleInterface;
import com.group6.hms.framework.screens.Operation;
import com.group6.hms.framework.screens.Screen;
import com.group6.hms.framework.screens.option.Option;
import com.group6.hms.framework.screens.option.OptionScreen;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * The {@code CalendarScreen} display a navigable calendar on to the console.
 *
 * It also allows the user to view calendar events on specific dates
 *
 * @param <Event> Type parameter representing events implementing EventInterface.
 * @param <Collection> Collection of events for a specific date.
 */
public class CalendarScreen<Event extends EventInterface, Collection extends java.util.Collection<Event>> extends OptionScreen {

    // Store events
    private Map<LocalDate, Collection> events;

    // Current day navigation state
    protected LocalDate currentDate = LocalDate.now();

    private static final int BACK = 0;
    private static final int UP = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;
    private static final int RIGHT = 4;

    private boolean stopDisplayingCalendar;
    private boolean selectDate = false;

    private static final int NAVIGATE_CALENDAR = 1;

    private static final NavigableMap<Integer, Option> navigationOptions = new TreeMap<>(Map.of(
            BACK, new Option(BACK, "Exit Navigation", ConsoleColor.PURPLE),
            UP, new Option(UP, "Up", ConsoleColor.PURPLE),
            DOWN, new Option(DOWN, "Down", ConsoleColor.PURPLE),
            LEFT, new Option(LEFT, "Left", ConsoleColor.PURPLE),
            RIGHT, new Option(RIGHT, "Right", ConsoleColor.PURPLE)
    ));

    private int[] cursor;


    /**
     * Constructor to initialize the screen with a title.
     *
     * @param title The title of the screen to be displayed as a header.
     */
    public CalendarScreen(String title, Map<LocalDate, Collection> events) {
        super(title);
        this.events = events;
        setAllowBack(true);
        addOption(NAVIGATE_CALENDAR, "Navigate Calendar", ConsoleColor.PURPLE);
    }

    /**
     * Display the Calendar
     */
    @Override
    public void onDisplay() {
        if(consoleInterface instanceof InteractiveConsoleInterface interactiveConsoleInterface){
            cursor = interactiveConsoleInterface.getCursorPosition();
        }

        // Display the current calendar state
        displayCalendar();
        // Fall back to option key
        super.onDisplay();
    }

    /**
     * Change the data used for storing the events
     * @param events A {@code Map<LocalDate, Collection>} type which stores the key as the {@code LocalDate} and value as a {@code List<Event>}
     */
    protected void setEvents(Map<LocalDate, Collection> events) {
        this.events = events;
    }

    @Override
    protected void navigateBack() {
        super.navigateBack();
        stopDisplayingCalendar = true;
    }

    @Override
    public void onNextScreen(Screen nextScreen) {
        super.onNextScreen(nextScreen);
        stopDisplayingCalendar = true;
    }

    /**
     * When the Navigate Calendar is selected, we enable the user to navigate the screen either by arrow key or manual date input (non-interactive mode)
     * @param optionId The ID of the option selected by the user.
     */
    @Override
    protected void handleOption(int optionId) {
        if (optionId == NAVIGATE_CALENDAR) {
            if (consoleInterface instanceof InteractiveConsoleInterface interactiveConsoleInterface) {
                navigateCalendarByInteraction(interactiveConsoleInterface);
            } else {
                navigateCalendarByNonInteraction();
            }
        }
    }

    /**
     * Allows the user to select a date in the calendar.
     *
     * @return The currently selected date.
     */
    protected LocalDate selectedDate(){
        selectDate = true;
        if (consoleInterface instanceof InteractiveConsoleInterface interactiveConsoleInterface) {
            navigateCalendarByInteraction(interactiveConsoleInterface);
            //displayPatientAppointmentOptions();
        } else {
            navigateCalendarByNonInteraction();
        }
        return currentDate;
    }

//    private void displayPatientAppointmentOptions(){
//        navigateToScreen(new ViewAvailableDoctorScreen());
//    }

    /**
     * Non-interactive navigation for calendar (used in non-interactive interfaces).
     */
    private void navigateCalendarByNonInteraction() {
        LocalDate calendarDate = DateUtils.readDate(consoleInterface);
        currentDate = calendarDate;
    }

    /**
     * Interactive calendar navigation for consoles with interactive capabilities.
     *
     * @param interactiveConsoleInterface The interface instance supporting interaction.
     */
    private void navigateCalendarByInteraction(InteractiveConsoleInterface interactiveConsoleInterface) {
        //Event loop to read key presses and navigate calendar
        stopDisplayingCalendar = false;
        while (!stopDisplayingCalendar) {
            // Reset the screen
            interactiveConsoleInterface.moveCursor(cursor[0], cursor[1]);
            interactiveConsoleInterface.clearPartialScreen();

            // Display the current calendar state
            displayCalendar();

            setCurrentTextConsoleColor(ConsoleColor.YELLOW);
            println("\nUse the arrow key to navigate and <Q> button to exit the navigation.");

            // Wait for a key press and retrieve the binding
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
                case ENTER:
                    if(selectDate){
                        stopDisplayingCalendar = true;
                        break;
                    }
                case EXIT:
                    stopDisplayingCalendar = true;
                    break;
            }
        }
    }

    /**
     * Display the calendar for the current date and highlight the selected day
     */
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

            if (events.get(day) != null && !events.get(day).isEmpty())
                setCurrentTextConsoleColor(ConsoleColor.YELLOW);
            else setCurrentTextConsoleColor(ConsoleColor.WHITE);

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
        Collection eventsOnSelectedDay = events.get(currentDate);
        if (eventsOnSelectedDay != null && !eventsOnSelectedDay.isEmpty()) {
            for (Event event : eventsOnSelectedDay) {
//                println("=".repeat(30));
                event.displayEvent(consoleInterface);
            }
        } else {
            println("No events for this day.");
        }
    }
}
