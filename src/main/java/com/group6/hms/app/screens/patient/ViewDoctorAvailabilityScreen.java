package com.group6.hms.app.screens.patient;

import com.group6.hms.app.managers.auth.LoginManager;
import com.group6.hms.app.managers.auth.LoginManagerHolder;
import com.group6.hms.app.managers.appointment.AppointmentManager;
import com.group6.hms.app.managers.availability.AvailabilityManager;
import com.group6.hms.app.managers.availability.models.Availability;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.framework.screens.calendar.CalendarScreen;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class ViewDoctorAvailabilityScreen extends CalendarScreen<Availability, List<Availability>> {


    /**
     * Constructor to initialize the screen with a title.
     *
     * @param events
     */
    Map<LocalDate, List<Availability>> events;
    AvailabilityManager availabilityManager = new AvailabilityManager();
    AppointmentManager appointmentManager = new AppointmentManager();
    LoginManager loginManager = LoginManagerHolder.getLoginManager();
    Patient patient;

    public ViewDoctorAvailabilityScreen( Map<LocalDate, List<Availability>> events) {
        super("View Doctor Availability", events);
        this.events = events;

        addOption(5, "Schedule an Appointment");
    }
    @Override
    protected void handleOption(int optionId) {
        super.handleOption(optionId);

        this.patient = (Patient) loginManager.getCurrentlyLoggedInUser();
        if(optionId == 5) {
            boolean result = false;
            println("=".repeat(30));
            println("Schedule an Appointment");
            println("=".repeat(30));
            List<Availability> docAvailability = availabilityManager.getAllAvailability();
            print("Enter Scheduled Date:");
            LocalDate date = LocalDate.parse(readString());
            print("Enter preferred appointment timing:");
            LocalTime starttime = LocalTime.parse(readString());
            for(Availability availability : docAvailability) {
                if(availability.getAvailableDate().equals(date)) {
                    if(availability.getAvailableStartTime().equals(starttime)) {
                        appointmentManager.scheduleAppointment(this.patient, availability);
                        result = true;
                        break;
                    }
                }
            }
            if(!result) println("\u001B[31m No availability found.");

        }
    }

    @Override
    public void onDisplay() {
        super.onDisplay();

    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
