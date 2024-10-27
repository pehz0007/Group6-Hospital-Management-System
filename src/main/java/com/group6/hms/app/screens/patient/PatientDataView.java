package com.group6.hms.app.screens.patient;

import com.group6.hms.app.models.BloodType;
import com.group6.hms.app.roles.Gender;
import com.group6.hms.app.roles.Patient;

import java.time.LocalDate;

public class PatientDataView {

    private String patientId;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private Gender gender;
    private BloodType bloodType;

    public PatientDataView(Patient patient) {
        this.patientId = patient.getUserId();
        this.name = patient.getName();
        this.email = patient.getContextInformation();
        this.bloodType = patient.getMedicalRecord().getBloodType();
        this.dateOfBirth = patient.getMedicalRecord().getDateOfBirth();
    }

}
