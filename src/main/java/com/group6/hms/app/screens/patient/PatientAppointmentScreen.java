package com.group6.hms.app.screens.patient;


import com.group6.hms.app.managers.auth.LoginManager;
import com.group6.hms.app.managers.auth.LoginManagerHolder;
import com.group6.hms.app.managers.appointment.AppointmentManager;
import com.group6.hms.app.managers.availability.AvailabilityManager;
import com.group6.hms.app.managers.appointment.models.Appointment;
import com.group6.hms.app.managers.appointment.models.AppointmentStatus;
import com.group6.hms.app.managers.availability.models.Availability;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.calendar.CalendarScreen;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.groupingBy;


public class PatientAppointmentScreen extends CalendarScreen<Appointment, List<Appointment>> {
    private Map<LocalDate, List<Appointment>> events;
    private AppointmentManager appointmentManager = new AppointmentManager();
    private AvailabilityManager availabilityManager = new AvailabilityManager();
    private LoginManager loginManager = LoginManagerHolder.getLoginManager();
    Patient user = (Patient) loginManager.getCurrentlyLoggedInUser();
    /**
     * Constructor to initialize the screen with a title.
     *

     * @param events
     */
    public PatientAppointmentScreen(Map<LocalDate, List<Appointment>> events) {
        super("Appointment", events);
        this.events = events;
        //Patient user = (Patient) loginManager.getCurrentlyLoggedInUser();

        addOption(5, "View Availability Appointment Slots", ConsoleColor.CYAN); //lermin

        addOption(6, "Reschedule an Appointment", ConsoleColor.CYAN);
        addOption(7, "Cancel an Appointment", ConsoleColor.CYAN);
        addOption(8, "View Past Appointment Records", ConsoleColor.CYAN);
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        this.events = appointmentManager.getAppointmentsByPatient(user).stream().filter(appointment ->
                appointment.getStatus() == AppointmentStatus.CONFIRMED || appointment.getStatus() == AppointmentStatus.REQUESTED).collect(groupingBy(Appointment::getDate));

    }

    @Override
    protected void handleOption(int optionId) {
        super.handleOption(optionId);


        if(optionId == 5) {
            Map<LocalDate, List<Availability>> avail = availabilityManager.getAllAvailability().stream().collect(groupingBy(Availability::getAvailableDate));
            navigateToScreen(new ViewDoctorAvailabilityScreen(avail));
        }else if(optionId == 6) {

        }else if(optionId == 7) {

        }else if(optionId == 8) {

        }else if(optionId == 9) {

        }

    }
}
