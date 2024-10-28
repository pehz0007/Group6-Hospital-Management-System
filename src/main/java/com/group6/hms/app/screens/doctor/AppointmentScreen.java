package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.models.Appointment;
import com.group6.hms.app.models.Availability;
import com.group6.hms.framework.screens.calendar.CalendarScreen;
import com.group6.hms.framework.screens.calendar.EventInterface;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class AppointmentScreen extends CalendarScreen<Appointment, List<Appointment>> {
    private Map<LocalDate, List<Appointment>> events;

    @Override
    public void onDisplay() {
        super.onDisplay();


    }

    /**
     * Constructor to initialize the screen with a title.
     *
     * @param events
     */

    public AppointmentScreen(Map<LocalDate, List<Appointment>> events) {
        super("Appointments", events);
        this.events = events;

        addOption(5,"Accept or Decline Upcoming Appointments");
        addOption(6, "Record Consultation Notes");
    }


}
