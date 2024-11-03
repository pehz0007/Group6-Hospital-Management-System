package com.group6.hms.app.models;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * The {@code MedicalRecord} class represents a patient's medical record,
 * including details such as date of birth and blood type.
 * It implements {@code Serializable} to allow for storage and retrieval.
 */
public class MedicalRecord implements Serializable {

    private LocalDate dateOfBirth;
    private BloodType bloodType;

    /**
     * Returns the date of birth associated with this medical record.
     *
     * @return the {@code LocalDate} of birth
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth for this medical record.
     *
     * @param dateOfBirth the {@code LocalDate} of birth to set
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Returns the blood type associated with this medical record.
     *
     * @return the {@code BloodType} of the patient
     */
    public BloodType getBloodType() {
        return bloodType;
    }

    /**
     * Sets the blood type for this medical record.
     *
     * @param bloodType the {@code BloodType} to set
     */
    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }
}