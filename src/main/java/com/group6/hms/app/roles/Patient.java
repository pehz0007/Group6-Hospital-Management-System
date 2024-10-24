package com.group6.hms.app.roles;

import com.group6.hms.app.auth.User;
import java.time.LocalDate;

public class Patient extends User {
    private String patientName;
    private String dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String email;
    private String bloodType;
    private String pastDiagnoses;


    public Patient(String username, char[] password, String patientName,
                   String dateOfBirth, String gender, String phoneNumber,
                   String email, String bloodType) {

        super(username, password);
        this.patientName = patientName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bloodType = bloodType;
        this.pastDiagnoses = pastDiagnoses;
    }

    public String getPatientName() {return patientName;}
    public String getDateOfBirth() {return dateOfBirth;}
    public String getGender() {return gender;}
    public String getPhoneNumber() {return phoneNumber;}
    public String getEmail() {return email;}
    public String getBloodType() {return bloodType;}
    public String getPastDiagnoses() {return pastDiagnoses;}

    //updatable info
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
    public void setEmail(String email) {this.email = email;}

    @Override
    public String getRoleName() {
        return "Patient";
    }
}
