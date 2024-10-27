package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.models.Availability;
import com.group6.hms.framework.screens.calendar.CalendarScreen;
import com.group6.hms.framework.screens.pagination.PaginationTableScreen;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DoctorAvailabilityScreen extends CalendarScreen<Availability, List<Availability>> {

    private Map<LocalDate, List<Availability>> events;
    /**
     * Constructor to initialize the screen with a title.
     *
     * @param title  The title of the screen to be displayed as a header.
     * @param events
     */
    public DoctorAvailabilityScreen(Map<LocalDate, List<Availability>> events) {
        super("Availability Screen", events);
        this.events = events;

        addOption(6, "Show Availability");

    }

    @Override
    protected void handleOption(int optionId) {
        if(optionId == 6) {
            navigateToScreen(new PaginationTableScreen<>("Availability", events.get(currentDate)));
        }
    }
}
