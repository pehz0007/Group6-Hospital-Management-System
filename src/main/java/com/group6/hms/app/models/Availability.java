package com.group6.hms.app.models;

import com.group6.hms.app.roles.Doctor;
import com.group6.hms.framework.screens.ConsoleInterface;
import com.group6.hms.framework.screens.calendar.DateRenderer;
import com.group6.hms.framework.screens.calendar.EventInterface;
import com.group6.hms.framework.screens.calendar.TimeRenderer;
import com.group6.hms.framework.screens.pagination.HeaderField;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;
import com.group6.hms.framework.screens.ConsoleInterface;
import com.group6.hms.framework.screens.calendar.EventInterface;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Availability implements EventInterface, Serializable {
    private Doctor doctor;
    @HeaderField(renderer = DateRenderer.class)
    private LocalDate availableDate;
    @HeaderField(renderer = TimeRenderer.class)
    private LocalTime availableStartTime;
    @HeaderField(renderer = TimeRenderer.class)
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

    public LocalDate getEventDate(){
        return getAvailableDate();
    }

    public LocalTime getEventStartTime(){
        return getAvailableStartTime();
    }
    public LocalTime getEventEndTime(){
        return getAvailableEndTime();
    }
    public void displayEvent(ConsoleInterface consoleInterface){
        PrintTableUtils.printItemAsVerticalTable(consoleInterface, this);
    }

}
