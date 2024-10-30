package com.group6.hms.app.roles;

import com.group6.hms.app.auth.User;
import com.group6.hms.app.models.AppointmentOutcomeRecord;
import com.group6.hms.app.models.MedicalRecord;

import java.util.List;
import java.util.ArrayList;

public class Patient extends User {
    private String contactInformation; // Email
    private MedicalRecord medicalRecord;
    private List<AppointmentOutcomeRecord> appointmentOutcomeRecord;

    public Patient(String userId, char[] password, String name, Gender gender, String contactInformation) {
        super(userId, password, name, gender);
        this.contactInformation = contactInformation;
        this.medicalRecord = new MedicalRecord();
        this.appointmentOutcomeRecord = new ArrayList<>();
    }

    public List<AppointmentOutcomeRecord> getPastTreatments(){
        return appointmentOutcomeRecord;
    }

    public List<AppointmentOutcomeRecord> getPastDiagnoses(){
        return appointmentOutcomeRecord;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void updateMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    @Override
    public String getRoleName() {
        return "Patient";
    }
}