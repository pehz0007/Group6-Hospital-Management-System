package com.group6.hms.app.screens.admin;

import com.group6.hms.app.managers.appointment.models.Appointment;
import com.group6.hms.app.managers.appointment.models.AppointmentStatus;
import com.group6.hms.app.managers.availability.models.Availability;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.framework.screens.ConsoleInterface;
import com.group6.hms.framework.screens.calendar.EventInterface;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The {@code AppointmentView} class represents a view of an appointment
 *
 * It extends the {@link Appointment} class and implements the {@link EventInterface}.
 * It is responsible for encapsulating appointment details and providing methods to access event-related
 * information.
 */
public class AppointmentView extends Appointment implements EventInterface {

    /**
     * Constructs an {@code AppointmentView} with the specified patient, doctor,
     * availability, and appointment status.
     *
     * @param patient      the patient associated with the appointment
     * @param doctor       the doctor associated with the appointment
     * @param availability the availability schedule for the appointment
     * @param status       the current status of the appointment
     */
    public AppointmentView(Patient patient, Doctor doctor, Availability availability, AppointmentStatus status ) {
        super(patient, doctor, status, availability);
    }

    /**
     * Gets the date of the event.
     *
     * @return the date of the appointment
     */
    @Override
    public LocalDate getEventDate() {
        return this.getDate();
    }

    /**
     * Gets the start time of the event.
     *
     * @return the start time of the appointment
     */
    @Override
    public LocalTime getEventStartTime() {
        return getStartTime();
    }

    /**
     * Gets the end time of the event.
     *
     * @return the end time of the appointment
     */
    @Override
    public LocalTime getEventEndTime() {
        return getEndTime();
    }

    /**
     * Displays the appointment details in the provided {@link ConsoleInterface}.
     *
     * @param consoleInterface the interface through which the appointment information is displayed
     */
    @Override
    public void displayEvent(ConsoleInterface consoleInterface) {
        consoleInterface.println("Appointment Date: " + this.getEventDate());
        consoleInterface.println("Appointment Start Time: " + this.getStartTime());
        consoleInterface.println("Appointment End Time: " + this.getEventEndTime());
        consoleInterface.println("Status: " + this.getStatus());
    }
}
