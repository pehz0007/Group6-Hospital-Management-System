package com.group6.hms.app.models;

import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.roles.Patient;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Appointment implements Serializable {
    private final UUID appointmentId;
    private final Patient patient;
    private final Doctor doctor;
    private AppointmentStatus status;
    private LocalDateTime dateTime;
    private AppointmentOutcomeRecord appointmentOutcomeRecord;

    public Appointment(Patient patient, Doctor doctor, AppointmentStatus status, LocalDateTime dateTime) {
        this(patient, doctor, status, dateTime, null);
    }

    public Appointment(Patient patient, Doctor doctor, AppointmentStatus status, LocalDateTime dateTime, AppointmentOutcomeRecord appointmentOutcomeRecord) {
        this.appointmentId = UUID.randomUUID();
        this.patient = patient;
        this.doctor = doctor;
        this.status = status;
        this.dateTime = dateTime;
        this.appointmentOutcomeRecord = appointmentOutcomeRecord;
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

    public void setStatus(AppointmentStatus newStatus) {
        this.status = newStatus;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
