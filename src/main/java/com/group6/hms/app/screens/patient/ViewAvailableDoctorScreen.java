package com.group6.hms.app.screens.patient;

import com.group6.hms.app.managers.AvailabilityManager;
import com.group6.hms.app.models.Availability;
import com.group6.hms.framework.screens.calendar.CalendarScreen;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

/**
 *The {@code ViewAvailableDoctorScreen} screen that displays available doctors for booking appointments.
 */
public class ViewAvailableDoctorScreen extends CalendarScreen<Availability, List<Availability>> {

    private final int BOOK_APPOINTMENT = 4;

    /**
     * Constructor to initialize the screen with a title.
     */
    public ViewAvailableDoctorScreen() {
        super("Available Doctor", null);

        AvailabilityManager availabilityManager = new AvailabilityManager();

        updateAvailableDoctorsScreen(availabilityManager);
        addOption(BOOK_APPOINTMENT, "Book Appointment");
    }

    /**
     * Updates the available doctors' screen with the availability data.
     *
     * @param availabilityManager the manager responsible for handling availability data
     */
    private void updateAvailableDoctorsScreen(AvailabilityManager availabilityManager) {
        Map<LocalDate, List<Availability>> events = availabilityManager.getAllAvailability().stream().collect(groupingBy(Availability::getAvailableDate));
        setEvents(events);
    }

    @Override
    protected void handleOption(int optionId) {
        super.handleOption(optionId);

        if(optionId == BOOK_APPOINTMENT) {
            LocalDate selectedDate = selectedDate();
        }

    }
}
