package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.models.*;
import com.group6.hms.app.roles.Gender;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.framework.screens.pagination.HeaderField;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * The {@code PatientView} class represents a view model for displaying patient information.
 * It encapsulates the details of a patient, including their name, medical record,
 * user ID, gender, birthdate, and blood type.
 */
public class PatientView {

    private String name;
    @HeaderField (show=false)
    private MedicalRecord medicalRecord;
    private String systemUserID;
    private Gender gender;
    private LocalDate birthDate;
    private BloodType bloodType;
//    private AppointmentOutcomeRecord appointmentRecord;
//    private String consultationNotes;
//    private List<PrescribedMedication> prescribedMedications;
//    private AppointmentService serviceType;


    /**
     * Constructs a PatientView object from a given Patient.
     *
     * @param patient The Patient object from which to extract data.
     */
    public PatientView(Patient patient) {
        this.name = patient.getName();
        this.medicalRecord = patient.getMedicalRecord();
        this.systemUserID = patient.getUserId();
        this.gender = patient.getGender();
        this.birthDate = this.medicalRecord.getDateOfBirth();
        this.bloodType = this.medicalRecord.getBloodType();
    }

    /**
     * Retrieves the medical record associated with this patient view.
     *
     * @return The MedicalRecord of the patient.
     */
    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

}
