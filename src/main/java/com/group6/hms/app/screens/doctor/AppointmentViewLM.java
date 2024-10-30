package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.models.Appointment;
import com.group6.hms.app.models.AppointmentStatus;

import java.util.UUID;

public class AppointmentViewLM {
    private  UUID appointmentId;
    private  String doctorName;
    private  String PatientName;
    private AppointmentStatus appointmentStatus;

    public AppointmentViewLM(Appointment appointment) {
        this.appointmentId = appointment.getAppointmentId();
        this.doctorName = appointment.getDoctor().getName();
        this.PatientName = appointment.getPatient().getName();
        this.appointmentStatus = appointment.getStatus();
    }
}
