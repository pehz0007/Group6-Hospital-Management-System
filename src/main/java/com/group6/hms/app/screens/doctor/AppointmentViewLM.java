package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.managers.appointment.models.Appointment;
import com.group6.hms.app.managers.appointment.models.AppointmentStatus;

import java.util.UUID;

/**
 * The {@code AppointmentViewLM} class provides a lightweight view of an appointment,
 * displaying essential information such as the appointment ID, doctor name, patient name, and appointment status.
 */
public class AppointmentViewLM {

    /** The unique identifier for the appointment. */
    private  UUID appointmentId;

    /** The name of the doctor associated with the appointment. */
    private  String doctorName;

    /** The name of the patient associated with the appointment. */
    private  String PatientName;

    /** The status of the appointment (e.g., requested, confirmed, completed). */
    private AppointmentStatus appointmentStatus;

    /**
     * Constructs an {@code AppointmentViewLM} based on the provided {@link Appointment} object.
     * Initializes fields to display key details about the appointment.
     *
     * @param appointment the {@code Appointment} object containing details for the view
     */
    public AppointmentViewLM(Appointment appointment) {
        this.appointmentId = appointment.getAppointmentId();
        this.doctorName = appointment.getDoctor().getName();
        this.PatientName = appointment.getPatient().getName();
        this.appointmentStatus = appointment.getStatus();
    }
}