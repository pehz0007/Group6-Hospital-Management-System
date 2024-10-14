package com.group6.hms.app.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Appointment implements Serializable {
    private UUID appointmentId;
    private UUID patientId;
    private UUID doctorId;
    private AppointmentStatus status;
    private LocalDateTime dateTime;
    private AppointmentOutcomeRecord appointmentOutcomeRecord;

    public Appointment(UUID patientId, UUID doctorId, AppointmentStatus status, LocalDateTime dateTime) {
        this(patientId, doctorId, status, dateTime, null);
    }

    public Appointment(UUID patientId, UUID doctorId, AppointmentStatus status, LocalDateTime dateTime, AppointmentOutcomeRecord appointmentOutcomeRecord) {
        this.appointmentId = UUID.randomUUID();
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.status = status;
        this.dateTime = dateTime;
        this.appointmentOutcomeRecord = appointmentOutcomeRecord;
    }

    public UUID getAppointmentId() {
        return appointmentId;
    }
}
