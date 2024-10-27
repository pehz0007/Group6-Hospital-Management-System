package com.group6.hms.app.roles;

import com.group6.hms.app.auth.User;
import com.group6.hms.app.models.MedicalRecord;

public class Patient extends User {
    public String patientId;
    private String name;
    private String dateOfBirth;
    private Gender gender;
    private String phoneNumber;
    private String email;
    private String bloodType;
    private MedicalRecord medicalRecord;

    public Patient(String username, char[] password, String name, Gender gender, String patientId,
                   String dateOfBirth, String phoneNumber, String email, String bloodType, MedicalRecord medicalRecord) {
        super(username, password, name, gender);
        this.patientId = patientId;
        this.dateOfBirth = dateOfBirth;
        this.patientId = patientId;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bloodType = bloodType;
        this.medicalRecord = new MedicalRecord();
    }

    public String getPatientId() {return patientId;}
    public String name() {return name;}
    public String getDateOfBirth() {return dateOfBirth;}
    public Gender gender() {return gender;}
    public String getPhoneNumber() {return phoneNumber;}
    public String getEmail() {return email;}
    public String getBloodType() {return bloodType;}

    //updatable info
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
    public void setEmail(String email) {this.email = email;}

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

    @Override
    public String toString() {
        return "Patient{" +
                "Patient ID:'" + patientId + '\'' +
                ", Patient Name:'" + name + '\'' +
                ", Date Of Birth:'" + dateOfBirth + '\'' +
                ", Gender:'" + gender + '\'' +
                ", Phone Number::'" + phoneNumber + '\'' +
                ", Email:'" + email + '\'' +
                ", Blood Type:'" + bloodType + '\'' +
                ", Medical Record:'" + medicalRecord +
                '}';

    }
}
