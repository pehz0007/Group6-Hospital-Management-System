package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.managers.auth.LoginManager;
import com.group6.hms.app.managers.auth.LoginManagerHolder;
import com.group6.hms.app.managers.availability.AvailabilityManager;
import com.group6.hms.app.managers.availability.AvailabilityManagerHolder;
import com.group6.hms.app.managers.availability.models.Availability;
import com.group6.hms.app.managers.availability.models.AvailabilityStatus;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.calendar.CalendarScreen;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

/**
 * The {@code DoctorAvailabilityScreen} manages a doctor's availability in a calendar-like view.
 * It fetches the doctor's availability data from {@code AvailabilityManager}
 *
 * It extends the {@link CalendarScreen} class to display availabilities
 * and groups them by date, setting these as the calendar's events.
 */
public class DoctorAvailabilityScreen extends CalendarScreen<Availability, List<Availability>> {

    /**
     * Map to store availability events, grouped by date.
     */
    private Map<LocalDate, List<Availability>> events;


    /**
     * The currently logged-in doctor.
     */
    private Doctor doc;

    /**
     * Manager to handle doctor availability.
     */
    private AvailabilityManager availabilityManager = AvailabilityManagerHolder.getAvailabilityManager();

    /**
     * Manager to handle user login and authentication.
     */
    private LoginManager loginManager = LoginManagerHolder.getLoginManager();

    /**
     * Starts the screen and loads the doctor's availability data.
     */
    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * Displays the calendar screen with the doctor's availability.
     */
    @Override
    public void onDisplay() {
        super.onDisplay();
    }

    /**
     * Refreshes the availability data and updates the calendar view.
     */
    @Override
    public void onRefresh() {
        super.onRefresh();
        Map<LocalDate, List<Availability>> items = availabilityManager.getAvailabilityByDoctor(doc).stream().collect(groupingBy(Availability::getAvailableDate));
        setEvents(items);
    }

    /**
     * Constructor to initialize the DoctorAvailabilityScreen with the doctor's availability.
     *
     * @param events A map of availability events grouped by date.
     */
    public DoctorAvailabilityScreen(Map<LocalDate, List<Availability>> events) {
        super("Availability", events);
        this.events = events;
        doc = DoctorScreen.getDoctor();

        addOption(5, "Add Availability", ConsoleColor.CYAN);

    }

    /**
     * Handles options selected by the user, such as adding new availability.
     *
     * @param optionId The ID of the selected menu option.
     */
    @Override
    protected void handleOption(int optionId) {
        super.handleOption(optionId);
        if(optionId == 5) {
            LocalDate date = null;
            LocalTime time = null;
            println("=".repeat(30));
            try{
                print("Enter Your Availability Date (YYYY-MM-DD): ");
                date = LocalDate.parse(readString());
            } catch (Exception e) {
                println("\u001B[31m Invalid date format. Please try again.");
                return;
            }
            try{
                print("Enter Start Time (XX:XX) :");
                time = LocalTime.parse(readString());
            }catch (Exception e) {
                println("\u001B[31m Invalid start time format. Please try again.");
                return;
            }

            Availability avail = new Availability(doc, date, AvailabilityStatus.AVAILABLE, time, time.plusHours(1));
            availabilityManager.addAvailability(avail);
            println("Added successfully!");
            println("=".repeat(30));
        }
    }
}
