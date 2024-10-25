package com.group6.hms.app.screens.admin;

import com.group6.hms.app.models.Appointment;
import com.group6.hms.app.models.AppointmentStatus;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.framework.screens.ConsoleInterface;
import com.group6.hms.framework.screens.calendar.EventInterface;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentView extends Appointment implements EventInterface {

    public AppointmentView(Patient patient, Doctor doctor, AppointmentStatus status, LocalDate date, LocalTime startTime, LocalTime endTime ) {
        super(patient, doctor, status, date, startTime, endTime);
    }

    @Override
    public LocalDate getEventDate() {
        return this.getDate();
    }

    @Override
    public LocalTime getEventStartTime() {
        return getStartTime();
    }

    @Override
    public LocalTime getEventEndTime() {
        return getEndTime();
    }


    @Override
    public void displayEvent(ConsoleInterface consoleInterface) {
        consoleInterface.println("Appointment Date: " + this.getEventDate());
        consoleInterface.println("Appointment Start Time: " + this.getStartTime());
        consoleInterface.println("Appointment End Time: " + this.getEventEndTime());
        consoleInterface.println("Status: " + this.getStatus());
    }
}