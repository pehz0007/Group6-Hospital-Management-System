package com.group6.hms.app.screens.patient;

import com.group6.hms.app.models.BloodType;
import com.group6.hms.app.roles.Gender;
import com.group6.hms.app.roles.Patient;

import java.time.LocalDate;

public class PatientDataView {

    private String patientId;
    private String name;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String email;
    private String phoneNumber;
    private BloodType bloodType;

    public PatientDataView(Patient patient) {
        this.patientId = patient.getUserId();
        this.name = patient.getName();
        this.dateOfBirth = patient.getMedicalRecord().getDateOfBirth();
        this.gender = patient.getGender();
        this.email = patient.getEmail();
        this.phoneNumber = patient.getPhoneNumber();
        this.bloodType = patient.getMedicalRecord().getBloodType();
    }

    public String getPatientId() {return patientId;}
    public String getName() {return name;}
    public LocalDate getDateOfBirth() {return dateOfBirth;}
    public Gender getGender() {return gender;}
    public String getEmail() {return email;}
    public String getPhoneNumber() {return phoneNumber;}
    public BloodType getBloodType() {return bloodType;}
}
