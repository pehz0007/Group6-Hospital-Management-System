package com.group6.hms.app.screens.patient;

import com.group6.hms.app.managers.appointment.AppointmentManagerHolder;
import com.group6.hms.app.managers.availability.AvailabilityManager;
import com.group6.hms.app.managers.availability.AvailabilityManagerHolder;
import com.group6.hms.app.managers.availability.models.Availability;
import com.group6.hms.app.managers.auth.LoginManager;
import com.group6.hms.app.managers.auth.LoginManagerHolder;
import com.group6.hms.app.managers.availability.AvailabilityManager;
import com.group6.hms.app.managers.appointment.models.Appointment;
import com.group6.hms.app.managers.availability.models.Availability;
import com.group6.hms.app.managers.AppointmentManager;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.calendar.CalendarScreen;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class ViewAvailableDoctorScreen extends CalendarScreen<Availability, List<Availability>> {

    private final int SCHEDULE_APPOINTMENT = 2;
    private final int RESCHEDULE_APPOINTMENT = 3;
    private final int CANCEL_APPOINTMENT = 4;
    private final int VIEW_SCHEDULED_APPOINTMENTS = 5;
    private AvailabilityManager availabilityManager = AvailabilityManagerHolder.getAvailabilityManager();
    private AppointmentManager appointmentManager = AppointmentManagerHolder.getAppointmentManager();
    private LoginManager loginManager = LoginManagerHolder.getLoginManager();
    private Patient patient;

    /**
     * Constructor to initialize the screen with a title.
     */
    public ViewAvailableDoctorScreen() {
        super("Available Appointments", null);

        updateAvailableDoctorsScreen();
        addOption(SCHEDULE_APPOINTMENT, "Schedule Appointment", ConsoleColor.CYAN);
        addOption(RESCHEDULE_APPOINTMENT, "Reschedule Appointment", ConsoleColor.CYAN);
        addOption(CANCEL_APPOINTMENT, "Cancel Appointment", ConsoleColor.CYAN);
        addOption(VIEW_SCHEDULED_APPOINTMENTS, "View Scheduled Appointments", ConsoleColor.CYAN);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        updateAvailableDoctorsScreen();
    }

    private void updateAvailableDoctorsScreen() {
        Map<LocalDate, List<Availability>> events = availabilityManager.getAllAvailableAvailability().stream().collect(groupingBy(Availability::getAvailableDate));
        setEvents(events);
    }

    @Override
    protected void handleOption(int optionId) {
        super.handleOption(optionId);

        if(optionId == SCHEDULE_APPOINTMENT) {
            print("Enter the appointment ID that you would like to book: ");
            String id = readString();
            Availability availability = availabilityManager.getAvailabilityById(id);

            if(availability == null){
                setCurrentTextConsoleColor(ConsoleColor.RED);
                println("No available appointment slots found.");
            }else{
                appointmentManager.scheduleAppointment(this.patient, availability);
                setCurrentTextConsoleColor(ConsoleColor.GREEN);
                println("Successfully requested for an appointment. To view your appointment status, please go to 'View Schedules Appointments' screen.");
                updateAvailableDoctorsScreen();
            }
            waitForKeyPress();
        }else if(optionId == RESCHEDULE_APPOINTMENT) {
            List<Appointment> patientAppointments = appointmentManager.getAppointmentsByPatient(this.patient);

            print("Enter the appointment ID that you would like to book: ");
            String id = readString();
            Availability availability = availabilityManager.getAvailabilityById(id);

            if(availability == null){
                setCurrentTextConsoleColor(ConsoleColor.RED);
                println("No available slots to reschedule the appointment.");
            }else{
                navigateToScreen(new RescheduleAppointmentScreen(patientAppointments, availability, this.patient));
            }
        }else if(optionId == CANCEL_APPOINTMENT) {
//            List<Appointment> patientAppointments = appointmentManager.getAppointmentsByPatient(this.patient);
            navigateToScreen(new CancelAppointmentScreen(this.patient));
        }else if(optionId == VIEW_SCHEDULED_APPOINTMENTS) {
            navigateToScreen(new ViewScheduledAppointmentsScreen(this.patient));
        }
    }

    @Override
    public void onDisplay() {
        super.onDisplay();

    }

    @Override
    public void onStart() {
        this.patient = (Patient) loginManager.getCurrentlyLoggedInUser();
        super.onStart();
    }
}