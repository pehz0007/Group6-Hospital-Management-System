package com.group6.hms.app.models;

import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.ConsoleInterface;
import com.group6.hms.framework.screens.calendar.EventInterface;
import com.group6.hms.framework.screens.pagination.HeaderField;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class Appointment implements Serializable, EventInterface {
    @HeaderField(show = false)
    private final UUID appointmentId;
    @HeaderField(show = false)
    private final Patient patient;
    private final Doctor doctor;
    private AppointmentStatus status;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private UUID appointmentOutcomeRecordId;

    public Appointment(Patient patient, Doctor doctor, AppointmentStatus status, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.appointmentId = UUID.randomUUID();
        this.patient = patient;
        this.doctor = doctor;
        this.status = status;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public UUID getAppointmentId() {
        return appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public UUID getAppointmentOutcomeRecordId() {
        return appointmentOutcomeRecordId;
    }

    public void setAppointmentOutcomeRecordId(UUID appointmentOutcomeRecordId) {
        this.appointmentOutcomeRecordId = appointmentOutcomeRecordId;
    }

    @Override
    public LocalDate getEventDate() {
        return getDate();
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
        if(getStatus() == AppointmentStatus.REQUESTED){
            consoleInterface.setCurrentTextConsoleColor(ConsoleColor.YELLOW);
            PrintTableUtils.printItemAsVerticalTable(consoleInterface, this);
        }
        if(getStatus() == AppointmentStatus.CONFIRMED || getStatus() == AppointmentStatus.COMPLETED){
            consoleInterface.setCurrentTextConsoleColor(ConsoleColor.GREEN);
            PrintTableUtils.printItemAsVerticalTable(consoleInterface, this);
        }else if(getStatus() == AppointmentStatus.CANCELLED){
            consoleInterface.setCurrentTextConsoleColor(ConsoleColor.RED);
            PrintTableUtils.printItemAsVerticalTable(consoleInterface, this);
        }
    }
}
