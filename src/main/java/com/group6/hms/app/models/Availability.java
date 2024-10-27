package com.group6.hms.app.models;

import com.group6.hms.app.roles.Doctor;
import com.group6.hms.framework.screens.ConsoleInterface;
import com.group6.hms.framework.screens.calendar.EventInterface;

import java.time.LocalDate;
import java.time.LocalTime;

public class Availability implements EventInterface {
    private Doctor doctor;
    private LocalDate availableDate;
    private LocalTime availableStartTime;
    private LocalTime availableEndTime;

    public Availability(Doctor doctor, LocalDate availableDate, LocalTime startTime, LocalTime endTime) {
        this.doctor = doctor;
        this.availableDate = availableDate;
        this.availableStartTime = startTime;
        this.availableEndTime = endTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDate getAvailableDate() {
        return availableDate;
    }

    public String getAvailabilityString() {
        return doctor.getSystemUserId().toString() + "," + availableDate.toString();
    }

    public void setAvailableDate(LocalDate availableDate) {
        this.availableDate = availableDate;
    }

    public LocalTime getAvailableStartTime() {
        return availableStartTime;
    }

    public void setAvailableStartTime(LocalTime availableStartTime) {
        this.availableStartTime = availableStartTime;
    }

    public LocalTime getAvailableEndTime() {
        return availableEndTime;
    }

    public void setAvailableEndTime(LocalTime availableEndTime) {
        this.availableEndTime = availableEndTime;
    }

    @Override
    public LocalDate getEventDate() {
        return null;
    }

    @Override
    public LocalTime getEventStartTime() {
        return null;
    }

    @Override
    public LocalTime getEventEndTime() {
        return null;
    }

    @Override
    public void displayEvent(ConsoleInterface consoleInterface) {

    }
}
