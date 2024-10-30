package com.group6.hms.app.models;

import java.io.Serializable;
import java.time.LocalDate;

public class MedicalRecord implements Serializable {

    private LocalDate dateOfBirth;
    private BloodType bloodType;

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }
}