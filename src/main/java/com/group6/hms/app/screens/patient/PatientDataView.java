package com.group6.hms.app.screens.patient;

import com.group6.hms.app.models.BloodType;
import com.group6.hms.app.roles.Gender;
import com.group6.hms.app.roles.Patient;

import java.time.LocalDate;

public class PatientDataView {
    private String systemUserId;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Gender gender;
    private BloodType bloodType;

    public PatientDataView(Patient patient) {
        this.systemUserId = patient.getUserId();
        this.name = patient.getName();
        this.email = patient.getContactInformation();
        this.phoneNumber = patient.getPhoneNumber();
        this.gender = patient.getGender();
        this.bloodType = patient.getMedicalRecord().getBloodType();
        this.dateOfBirth = patient.getMedicalRecord().getDateOfBirth();
    }
}