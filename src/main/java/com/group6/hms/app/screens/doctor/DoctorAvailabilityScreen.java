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
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class DoctorAvailabilityScreen extends CalendarScreen<Availability, List<Availability>> {

    private Map<LocalDate, List<Availability>> events;
    Doctor doc;
    AvailabilityManager availabilityManager = new AvailabilityManager();
    LoginManager loginManager = LoginManagerHolder.getLoginManager();

    @Override
    public void onStart() {
        super.onStart();
        Map<LocalDate, List<Availability>> avail_appointments = availabilityManager.getAvailabilityByDoctor(doc).stream().collect(groupingBy(Availability::getAvailableDate));
        this.events = avail_appointments;
    }

    /**
     * Constructor to initialize the screen with a title.
     *
     *   The title of the screen to be displayed as a header.
     * @param events
     */
    public DoctorAvailabilityScreen(Map<LocalDate, List<Availability>> events) {
        super("Availability Screen", events);
        this.events = events;
        doc = (Doctor) loginManager.getCurrentlyLoggedInUser ();

        addOption(5, "Add Availability", ConsoleColor.CYAN);

    }

    @Override
    protected void handleOption(int optionId) {
        if(optionId == 5) {
//            navigateToScreen(new PaginationTableScreen<>("Availability", events.get(currentDate)));
            println("=".repeat(30));
            print("Enter Your Availability Date: ");
            LocalDate date = LocalDate.parse(readString());
            print("Enter Start Time:");
            LocalTime starttime = LocalTime.parse(readString());
            Availability avail = new Availability(doc, date, starttime, starttime.plusHours(1));
            availabilityManager.addAvailability(avail);
            println("Added successfully!");
            println("=".repeat(30));
        }
    }
}
