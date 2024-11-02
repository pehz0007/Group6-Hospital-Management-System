package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.auth.LoginManagerHolder;
import com.group6.hms.app.managers.AvailabilityManager;
import com.group6.hms.app.models.Availability;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.calendar.CalendarScreen;
import com.group6.hms.framework.screens.pagination.PaginationTableScreen;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

/**
 * The {@code DoctorAvailabilityScreen} manages a doctor's availability in a calendar-like view.
 * It allows the doctor to view and add availability dates and times.
 *
 * It extends the {@link CalendarScreen} class to display availabilities.
 */
public class DoctorAvailabilityScreen extends CalendarScreen<Availability, List<Availability>> {

    /**
     * Map to store availability events, grouped by date.
     */
    private Map<LocalDate, List<Availability>> events;

    /**
     * The currently logged-in doctor.
     */
    Doctor doc;

    /**
     * Manager to handle doctor availability.
     */
    AvailabilityManager availabilityManager = new AvailabilityManager();

    /**
     * Manager to handle user login and authentication.
     */
    LoginManager loginManager = LoginManagerHolder.getLoginManager();

    /**
     * Starts the screen and loads the doctor's availability data.
     */
    @Override
    public void onStart() {
        super.onStart();
        this.events = availabilityManager.getAvailabilityByDoctor(doc).stream().collect(groupingBy(Availability::getAvailableDate));
    }

    /**
     * Displays the calendar screen with the doctor's availability.
     */
    @Override
    public void onDisplay() {
        super.onDisplay();
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

            Availability avail = new Availability(doc, date, time, time.plusHours(1));
            availabilityManager.addAvailability(avail);
            println("Added successfully!");
            println("=".repeat(30));
        }
    }
}
