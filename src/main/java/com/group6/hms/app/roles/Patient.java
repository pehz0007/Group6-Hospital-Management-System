package com.group6.hms.app.roles;

import com.group6.hms.app.managers.auth.User;
import com.group6.hms.app.models.MedicalRecord;

/**
 * The {@code Patient} class represents a patient in the hospital management system.
 * It extends the {@link User} class and contains attributes and methods specific to a patient such
 * as contact information, phone number, and medical record.
 */
public class Patient extends User {
    private String contactInformation; // Email
    private String phoneNumber;
    private MedicalRecord medicalRecord;

    /**
     * Constructs a Patient object with the specified details.
     *
     * @param userId             the unique ID for the user
     * @param password           the password for the user
     * @param name               the name of the patient
     * @param gender             the gender of the patient
     * @param contactInformation the email contact information for the patient
     * @param phoneNumber        the patient's phone number
     */
    public Patient(String userId, char[] password, String name, Gender gender, String contactInformation, String phoneNumber) {
        super(userId, password, name, gender);
        this.contactInformation = contactInformation;
        this.phoneNumber = phoneNumber;
        this.medicalRecord = new MedicalRecord();
    }

    /**
     * Updates the patient's medical record with a new MedicalRecord object.
     *
     * @param medicalRecord the new medical record for the patient
     */
    public void updateMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    /**
     * Retrieves the patient's current medical record.
     *
     * @return the current medical record of the patient
     */
    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    /**
     * Retrieves the patient's contact information (email).
     *
     * @return the email contact information of the patient
     */
    public String getContactInformation() {
        return contactInformation;
    }

    /**
     * Updates the patient's contact information (email).
     *
     * @param contactInformation the new contact information for the patient
     */
    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    /**
     * Retrieves the patient's phone number.
     *
     * @return the phone number of the patient
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Updates the patient's phone number.
     *
     * @param phoneNumber the new phone number for the patient
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the role name for this user, which is "Patient".
     *
     * @return the role name "Patient"
     */
    @Override
    public String getRoleName() {
        return "Patient";
    }

    /**
     * Returns a string representation of the patient, which is the patient's name.
     *
     * @return the name of the patient
     */
    @Override
    public String toString() {
        return getName();
    }
}
