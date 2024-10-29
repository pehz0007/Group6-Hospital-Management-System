package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.models.BloodType;
import com.group6.hms.app.models.MedicalRecord;
import com.group6.hms.app.roles.Gender;
import com.group6.hms.app.roles.Patient;

import java.time.LocalDate;
import java.util.UUID;

public class PatientView {

    private String name;
    private MedicalRecord medicalRecord;
    private UUID systemUserID;
    private Gender gender;
    private LocalDate birthDate;
    private BloodType bloodType;

    public PatientView(Patient patient) {
        this.name = patient.getName();
        this.medicalRecord = patient.getMedicalRecord();
        this.systemUserID = patient.getSystemUserId();
        this.gender = patient.getGender();
        this.birthDate = this.medicalRecord.getDateOfBirth();
        this.bloodType = this.medicalRecord.getBloodType();
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

}
