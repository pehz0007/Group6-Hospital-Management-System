package com.group6.hms.app.roles;

import com.group6.hms.app.auth.User;
import com.group6.hms.app.models.MedicalRecord;

public class Patient extends User {

    private MedicalRecord medicalRecord;

    public Patient(String username, char[] password, String name, Gender gender) {
        super(username, password, name, gender);
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void updateMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    @Override
    public String getRoleName() {
        return "Patient";
    }
}
