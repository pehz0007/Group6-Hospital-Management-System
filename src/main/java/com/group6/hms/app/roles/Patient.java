package com.group6.hms.app.roles;

import com.group6.hms.app.auth.User;
import com.group6.hms.app.models.MedicalRecord;

public class Patient extends User {

    private String contextInformation; // Email
    private MedicalRecord medicalRecord;

    public Patient(String userId, char[] password, String name, Gender gender) {
        super(userId, password, name, gender);
        this.medicalRecord = new MedicalRecord();
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void updateMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public String getContextInformation() {
        return contextInformation;
    }

    public void setContextInformation(String contextInformation) {
        this.contextInformation = contextInformation;
    }

    @Override
    public String getRoleName() {
        return "Patient";
    }
}
