package com.group6.hms.app.models;

import com.group6.hms.app.MedicationStatus;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.roles.Patient;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class AppointmentOutcomeRecord implements Serializable {
    private UUID recordId;
    private UUID doctorId;
    private UUID patientId;
    private LocalDate dateOfAppointment;
    private AppointmentService serviceType;
    private Medication[] prescribedMedication;
    private String consultationNotes;
    private MedicationStatus medicationStatus;
    public AppointmentOutcomeRecord(UUID doctorId, UUID patientId, LocalDate date, AppointmentService serviceType, Medication[] prescribedMedication, String consultationNotes, MedicationStatus medicationStatus) {
        this.recordId = UUID.randomUUID();
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.dateOfAppointment = date;
        this.serviceType = serviceType;
        this.prescribedMedication = prescribedMedication;
        this.consultationNotes = consultationNotes;
        this.medicationStatus = medicationStatus;
    }


    public UUID getRecordId() {
        return recordId;
    }

    public void setRecordId(UUID recordId) {
        this.recordId = recordId;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public LocalDate getDateOfAppointment() {
        return dateOfAppointment;
    }

    public void setDateOfAppointment(LocalDate dateOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
    }

    public AppointmentService getServiceType() {
        return serviceType;
    }

    public void setServiceType(AppointmentService serviceType) {
        this.serviceType = serviceType;
    }

    public Medication[] getPrescribedMedication() {
        return prescribedMedication;
    }

    public void setPrescribedMedication(Medication[] prescribedMedication) {
        this.prescribedMedication = prescribedMedication;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    public MedicationStatus getMedicationStatus() {
        return medicationStatus;
    }

    public void setMedicationStatus(MedicationStatus medicationStatus) {
        this.medicationStatus = medicationStatus;
    }
}
