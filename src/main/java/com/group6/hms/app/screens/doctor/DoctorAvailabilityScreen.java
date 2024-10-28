package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.auth.LoginManagerHolder;
import com.group6.hms.app.managers.AvailabilityManager;
import com.group6.hms.app.models.Availability;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.framework.screens.calendar.CalendarScreen;
import com.group6.hms.framework.screens.pagination.PaginationTableScreen;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class DoctorAvailabilityScreen extends CalendarScreen<Availability, List<Availability>> {

    private Map<LocalDate, List<Availability>> events;
    private LocalDate currentDate = LocalDate.now();
    LoginManager loginManager = LoginManagerHolder.getLoginManager();
    AvailabilityManager availabilityManager = new AvailabilityManager();

    /**
     * Constructor to initialize the screen with a title.
     *
     *   The title of the screen to be displayed as a header.
     * @param events
     */
    public DoctorAvailabilityScreen(Map<LocalDate, List<Availability>> events) {
        super("Availability Screen", events);
        this.events = events;

        addOption(6, "Add Availability");


    }

    @Override
    protected void handleOption(int optionId) {
        super.handleOption(optionId);
        if(optionId == 6) {
            Doctor doc = (Doctor) loginManager.getCurrentlyLoggedInUser();
            println("Set Availability");
            Scanner sc = new Scanner(System.in);
            println("Enter availability date (yyyy-mm-dd): ");
            String date = sc.nextLine();


            LocalDate date1 = LocalDate.parse(date);
            println("Enter availability start time: ");
            String startTime = sc.nextLine();
            LocalTime startTime1 = LocalTime.parse(startTime);
            Availability avail = new Availability(doc,date1, startTime1, startTime1.plusHours(1 ));
            availabilityManager.addAvailability(avail);
            events.computeIfAbsent(date1, k -> new ArrayList<>()).add(avail);
            events.get(date1).sort(Comparator.comparing(Availability::getAvailableStartTime));

            // Optionally, you can print a confirmation message
            println("\033[35m"+"Availability added for " + date1 + " from " + startTime1 + " to " + startTime1.plusHours(1));

        }
    }
}
