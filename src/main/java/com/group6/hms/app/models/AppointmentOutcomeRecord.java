package com.group6.hms.app.models;

import java.io.Serializable;
import java.time.LocalDate;

public class AppointmentOutcomeRecord implements Serializable {
    private LocalDate dateOfAppointment;
    private AppointmentService serviceType;
    private Medication[] prescribedMedication;
    private String consultationNotes;
}
