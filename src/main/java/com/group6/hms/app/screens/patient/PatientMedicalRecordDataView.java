package com.group6.hms.app.screens.patient;

import com.group6.hms.app.models.AppointmentService;
import com.group6.hms.app.models.BloodType;
import com.group6.hms.app.models.PrescribedMedication;
import com.group6.hms.app.roles.Gender;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.app.models.AppointmentOutcomeRecord;

import java.time.LocalDate;
import java.util.List;

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
