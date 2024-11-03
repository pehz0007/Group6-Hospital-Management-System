package com.group6.hms.app.managers.availability.models;

import com.group6.hms.app.managers.availability.AvailabilityManager;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.framework.screens.ConsoleInterface;
import com.group6.hms.framework.screens.calendar.DateRenderer;
import com.group6.hms.framework.screens.calendar.EventInterface;
import com.group6.hms.framework.screens.calendar.TimeRenderer;
import com.group6.hms.framework.screens.pagination.HeaderField;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * The {@code Availability} class represents a doctor's available appointment slot,
 * including the date and start and end times. It implements {@code EventInterface}
 * for calendar integration and {@code Serializable} for storage.
 */
public class Availability implements EventInterface, Serializable {

    private UUID availabilityId;

    private Doctor doctor;
    @HeaderField(renderer = DateRenderer.class)
    private LocalDate availableDate;
    @HeaderField(renderer = TimeRenderer.class)
    private LocalTime availableStartTime;
    @HeaderField(renderer = TimeRenderer.class)
    private LocalTime availableEndTime;
    private AvailabilityStatus availabilityStatus;

    /**
     * Constructs a new {@code Availability} instance with the specified doctor,
     * available date, start time, and end time.
     *
     * @param doctor       the doctor associated with this availability
     * @param availableDate the date when the doctor is available
     * @param availabilityStatus the availability's status either Available or Booked
     * @param startTime    the start time of availability
     * @param endTime      the end time of availability
     */
    public Availability(Doctor doctor, LocalDate availableDate, AvailabilityStatus availabilityStatus, LocalTime startTime, LocalTime endTime) {
        this.doctor = doctor;
        this.availableDate = availableDate;
        this.availableStartTime = startTime;
        this.availableEndTime = endTime;
        this.availabilityStatus = availabilityStatus;
    }

    public UUID getAvailabilityId() {
        return availabilityId;
    }

    /**
     * This method is used by the AvailabilityManager to modify the {@code AvailabilityStatus}.
     * Instead, use {@link AvailabilityManager#updateAvailability(Availability, AvailabilityStatus)}to change the {@code AvailabilityStatus}
     */
    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    /**
     * Returns the {@code AvailabilityStatus} associated with this availability.
     *
     * @return the {@code AvailabilityStatus}
     */
    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    /**
     * Returns the doctor associated with this availability.
     *
     * @return the doctor
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     * Sets the doctor for this availability.
     *
     * @param doctor the doctor to set
     */
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    /**
     * Returns the available date for this slot.
     *
     * @return the available date
     */
    public LocalDate getAvailableDate() {
        return availableDate;
    }

    /**
     * Returns a string representation of this availability, including the doctor's ID and available date.
     *
     * @return a string representing this availability
     */
    public String getAvailabilityString() {
        return doctor.getSystemUserId().toString() + "," + availableDate.toString();
    }

    /**
     * Sets the available date for this slot.
     *
     * @param availableDate the date to set as available
     */
    public void setAvailableDate(LocalDate availableDate) {
        this.availableDate = availableDate;
    }

    /**
     * Returns the start time of availability.
     *
     * @return the available start time
     */
    public LocalTime getAvailableStartTime() {
        return availableStartTime;
    }

    /**
     * Sets the start time of availability.
     *
     * @param availableStartTime the start time to set
     */
    public void setAvailableStartTime(LocalTime availableStartTime) {
        this.availableStartTime = availableStartTime;
    }

    /**
     * Returns the end time of availability.
     *
     * @return the available end time
     */
    public LocalTime getAvailableEndTime() {
        return availableEndTime;
    }


    /**
     * Sets the end time of availability.
     *
     * @param availableEndTime the end time to set
     */
    public void setAvailableEndTime(LocalTime availableEndTime) {
        this.availableEndTime = availableEndTime;
    }

    /**
     * Returns the event date for calendar integration.
     *
     * @return the available date
     */
    public LocalDate getEventDate(){
        return getAvailableDate();
    }

    /**
     * Returns the event start time for calendar integration.
     *
     * @return the available start time
     */
    public LocalTime getEventStartTime(){
        return getAvailableStartTime();
    }

    /**
     * Returns the event end time for calendar integration.
     *
     * @return the available end time
     */
    public LocalTime getEventEndTime(){
        return getAvailableEndTime();
    }

    /**
     * Displays the availability information as a vertical table on the console.
     *
     * @param consoleInterface the console interface for displaying the availability
     */
    public void displayEvent(ConsoleInterface consoleInterface){
        PrintTableUtils.printItemAsVerticalTable(consoleInterface, this);
    }

}
