package com.group6.hms.app.managers.appointment.models;

import com.group6.hms.app.managers.availability.models.Availability;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.app.screens.doctor.AppointmentStatusRenderer;
import com.group6.hms.framework.screens.ConsoleInterface;
import com.group6.hms.framework.screens.calendar.EventInterface;
import com.group6.hms.framework.screens.pagination.HeaderField;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
/**
 * The {@code Appointment} class represents an appointment within the system, detailing
 * information about the patient, doctor, status, date, and time of the appointment.
 * It implements {@code Serializable} for storage purposes and {@code EventInterface}
 * for integration with calendar event displays.
 */

public class Appointment implements Serializable, EventInterface {
    @HeaderField(show = false)
    private final UUID appointmentId;
    @HeaderField(show = false)
    private final Patient patient;
    private final Doctor doctor;
    @HeaderField(renderer = AppointmentStatusRenderer.class)
    private AppointmentStatus status;
    private UUID availabilityId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    @HeaderField(width = 40)
    private UUID appointmentOutcomeRecordId;

    /**
     * Constructs a new {@code Appointment} with specified patient, doctor, status, availability
     *
     * @param patient       the patient associated with the appointment
     * @param doctor        the doctor associated with the appointment
     * @param status        the status of the appointment
     * @param availability  the {@code Availability} associated with this {@code Appointment}
     */
    public Appointment(Patient patient, Doctor doctor, AppointmentStatus status, Availability availability) {
        this.appointmentId = UUID.randomUUID();
        this.patient = patient;
        this.doctor = doctor;
        this.status = status;
        this.availabilityId = availability.getAvailabilityId();
        this.date = availability.getAvailableDate();
        this.startTime = availability.getEventStartTime();
        this.endTime = availability.getEventEndTime();
    }


    /**
     * Returns the unique identifier for the appointment.
     *
     * @return the appointment ID
     */
    public UUID getAppointmentId() {
        return appointmentId;
    }

    /**
     * Returns the patient associated with the appointment.
     *
     * @return the patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Returns the doctor associated with the appointment.
     *
     * @return the doctor
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     * Returns the availability id associated with the appointment.
     *
     * @return the UUID of the associated {@code Availability}
     */
    public UUID getAvailabilityId() {
        return availabilityId;
    }

    /**
     * Sets the availability id of the appointment.
     *
     * @param availabilityId the availability id to set
     */
    public void setAvailabilityId(UUID availabilityId) {
        this.availabilityId = availabilityId;
    }

    /**
     * Returns the current status of the appointment.
     *
     * @return the appointment status
     */
    public AppointmentStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the appointment.
     *
     * @param status the new status to set
     */
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    /**
     * Returns the date of the appointment.
     *
     * @return the appointment date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date of the appointment.
     *
     * @param date the new appointment date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns the start time of the appointment.
     *
     * @return the appointment start time
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the appointment.
     *
     * @param startTime the new appointment start time
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns the end time of the appointment.
     *
     * @return the appointment end time
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the appointment.
     *
     * @param endTime the new appointment end time
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Returns the ID of the outcome record associated with this appointment, if available.
     *
     * @return the appointment outcome record ID
     */
    public UUID getAppointmentOutcomeRecordId() {
        return appointmentOutcomeRecordId;
    }

    /**
     * Sets the outcome record ID for this appointment.
     *
     * @param appointmentOutcomeRecordId the new appointment outcome record ID
     */
    public void setAppointmentOutcomeRecordId(UUID appointmentOutcomeRecordId) {
        this.appointmentOutcomeRecordId = appointmentOutcomeRecordId;
    }
    /**
     * Returns the date of the event for calendar purposes.
     *
     * @return the event date
     */
    @Override
    public LocalDate getEventDate() {
        return getDate();
    }

    /**
     * Returns the start time of the event for calendar purposes.
     *
     * @return the event start time
     */
    @Override
    public LocalTime getEventStartTime() {
        return getStartTime();
    }

    /**
     * Returns the end time of the event for calendar purposes.
     *
     * @return the event end time
     */
    @Override
    public LocalTime getEventEndTime() {
        return getEndTime();
    }

    /**
     * Displays the appointment as a vertical table on the console.
     *
     * @param consoleInterface the console interface for displaying the appointment
     */
    @Override
    public void displayEvent(ConsoleInterface consoleInterface) {
        PrintTableUtils.printItemAsVerticalTable(consoleInterface, this);
    }
}
