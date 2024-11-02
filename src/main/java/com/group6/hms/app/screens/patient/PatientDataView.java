package com.group6.hms.app.screens.patient;

import com.group6.hms.app.models.BloodType;
import com.group6.hms.app.roles.Gender;
import com.group6.hms.app.roles.Patient;

import java.time.LocalDate;

/**
 * The {@code PatientDataView} class provides a representation of a patient's information
 * for display in the user interface.
 */
public class PatientDataView {

    private String patientId;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private Gender gender;
    private BloodType bloodType;

    /**
     * Constructor to initialize the PatientDataView with patient information.
     *
     * @param patient the patient whose data is to be viewed
     */
    public PatientDataView(Patient patient) {
        this.patientId = patient.getUserId();
        this.name = patient.getName();
        this.email = patient.getContactInformation();
        this.gender = patient.getGender();
        this.bloodType = patient.getMedicalRecord().getBloodType();
        this.dateOfBirth = patient.getMedicalRecord().getDateOfBirth();
    }
}