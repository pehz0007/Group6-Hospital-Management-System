package com.group6.hms.app.screens.patient;

import com.group6.hms.app.models.BloodType;
import com.group6.hms.app.roles.Gender;
import com.group6.hms.app.roles.Patient;

import java.time.LocalDate;

/**
 * The {@code PatientDataView} class represents the storage of a patient's personal information data for display purpose.
 * It extracts the relevant information from {@link Patient} object, such as
 * user ID, name, email, phone numebr, date of birth, gender and blood type.
 *
 * <p> This class is used to represent patient's personal information data in a structured format, making
 * the data easier to display on user interfaces.</p>
 */
public class PatientDataView {
    private String systemUserId;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Gender gender;
    private BloodType bloodType;

    /**
     * Constructs a new {@code PatientDataView} instance by extracting relevant information from the given {@link Patient} object
     *
     * @param patient The patient object who information to be displayed.
     */
    public PatientDataView(Patient patient) {
        this.systemUserId = patient.getUserId();
        this.name = patient.getName();
        this.email = patient.getContactInformation();
        this.phoneNumber = patient.getPhoneNumber();
        this.gender = patient.getGender();
        this.bloodType = patient.getMedicalRecord().getBloodType();
        this.dateOfBirth = patient.getMedicalRecord().getDateOfBirth();
    }
}