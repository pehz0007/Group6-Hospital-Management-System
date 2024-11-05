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

public class DoctorAvailabilityScreen extends CalendarScreen<Availability, List<Availability>> {

    private Map<LocalDate, List<Availability>> events;
    private Doctor doc;
    private AvailabilityManager availabilityManager = AvailabilityManagerHolder.getAvailabilityManager();
    private LoginManager loginManager = LoginManagerHolder.getLoginManager();

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        Map<LocalDate, List<Availability>> items = availabilityManager.getAvailabilityByDoctor(doc).stream().collect(groupingBy(Availability::getAvailableDate));
        setEvents(items);
    }

    /**
     * Constructor to initialize the screen with a title.
     *
     *   The title of the screen to be displayed as a header.
     * @param events
     */
    public DoctorAvailabilityScreen(Map<LocalDate, List<Availability>> events) {
        super("Availability", events);
        this.events = events;
        doc = DoctorScreen.getDoctor();

        addOption(5, "Add Availability", ConsoleColor.CYAN);

    }

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
