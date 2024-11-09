package com.group6.hms.app.screens.patient;

import com.group6.hms.app.managers.appointment.models.AppointmentOutcomeRecord;
import com.group6.hms.app.managers.appointment.models.AppointmentService;
import com.group6.hms.app.managers.inventory.models.PrescribedMedication;
import com.group6.hms.app.models.BloodType;
import com.group6.hms.app.roles.Gender;
import com.group6.hms.app.roles.Patient;

import java.time.LocalDate;
import java.util.List;

/**
 * The {@code PatientMedicalRecordDataView} class consolidate patient's personal information from {@link Patient}
 * and past medical records from the {@link AppointmentOutcomeRecord},
 * providing a structured format for east access.
 *
 * <p>This class is used to present a patient's personal and medical record information together.</p>
 */
public class PatientMedicalRecordDataView {
    private String systemUserId;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Gender gender;
    private BloodType bloodType;
    private AppointmentService serviceType;
    private List<PrescribedMedication> prescribedMedications;
    private String consultationNotes;

    /**
     * Constructs a {@code PatientMedicalRecordDataView} instance by consolidating personal and medical record data
     * from the given {@link Patient} and {@link AppointmentOutcomeRecord} objects.
     *
     * @param patient The {@code Patient} object containing patient's personal detials
     * @param appointmentOutcomeRecord The {@code AppointmentOutcomeRecord} object
     *                                 containing details of the patient's past appointment,
     *                                 including service type, medications and consultation notes.
     */
    public PatientMedicalRecordDataView(Patient patient, AppointmentOutcomeRecord appointmentOutcomeRecord) {
        this.systemUserId = patient.getUserId();
        this.name = patient.getName();
        this.email = patient.getContactInformation();
        this.phoneNumber = patient.getPhoneNumber();
        this.gender = patient.getGender();
        this.bloodType = patient.getMedicalRecord().getBloodType();
        this.dateOfBirth = patient.getMedicalRecord().getDateOfBirth();
        this.serviceType = appointmentOutcomeRecord.getServiceType();
        this.prescribedMedications = appointmentOutcomeRecord.getPrescribedMedications();
        this.consultationNotes = appointmentOutcomeRecord.getConsultationNotes();
    }
}
