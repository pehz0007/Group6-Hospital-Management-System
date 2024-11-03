package com.group6.hms.app.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * The {@code AppointmentOutcomeRecord} class represents the outcome of a patient's appointment.
 * It includes information about the doctor, patient, date of appointment, type of service provided,
 * prescribed medications, consultation notes, and the status of medication fulfillment.
 * This class implements {@code Serializable} to allow for storage and retrieval.
 */
public class AppointmentOutcomeRecord implements Serializable {
    private UUID recordId;
    private UUID doctorId;
    private UUID patientId;
    private LocalDate dateOfAppointment;
    private AppointmentService serviceType;
    private List<PrescribedMedication> prescribedMedications;
    private String consultationNotes;
    private MedicationStatus medicationStatus;

    /**
     * Constructs a new {@code AppointmentOutcomeRecord} with the specified details.
     *
     * @param doctorId           the UUID of the doctor who conducted the appointment
     * @param patientId          the UUID of the patient who attended the appointment
     * @param date               the date of the appointment
     * @param serviceType        the type of service provided during the appointment
     * @param prescribedMedications the list of medications prescribed to the patient
     * @param consultationNotes  the notes recorded during the consultation
     * @param medicationStatus   the status of the medication prescribed during the appointment
     */
    public AppointmentOutcomeRecord(UUID doctorId, UUID patientId, LocalDate date, AppointmentService serviceType, List<PrescribedMedication> prescribedMedications, String consultationNotes, MedicationStatus medicationStatus) {
        this.recordId = UUID.randomUUID();
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.dateOfAppointment = date;
        this.serviceType = serviceType;
        this.prescribedMedications = prescribedMedications;
        this.consultationNotes = consultationNotes;
        this.medicationStatus = medicationStatus;
    }

    /**
     * Returns the unique identifier for this outcome record.
     *
     * @return the UUID of the record
     */
    public UUID getRecordId() {
        return recordId;
    }

    /**
     * Returns the UUID of the doctor associated with this appointment.
     *
     * @return the doctor UUID
     */
    public UUID getDoctorId() {
        return doctorId;
    }

    /**
     * Returns the UUID of the patient associated with this appointment.
     *
     * @return the patient UUID
     */
    public UUID getPatientId() {
        return patientId;
    }

    /**
     * Returns the date of the appointment.
     *
     * @return the date of the appointment
     */
    public LocalDate getDateOfAppointment() {
        return dateOfAppointment;
    }

    /**
     * Returns the type of service provided during the appointment.
     *
     * @return the appointment service type
     */
    public AppointmentService getServiceType() {
        return serviceType;
    }

    /**
     * Sets the type of service provided during the appointment.
     *
     * @param serviceType the service type to set
     */
    public void setServiceType(AppointmentService serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * Returns the list of medications prescribed to the patient during the appointment.
     *
     * @return a list of prescribed medications
     */
    public List<PrescribedMedication> getPrescribedMedications() {
        return prescribedMedications;
    }

    /**
     * Sets the list of medications prescribed to the patient during the appointment.
     *
     * @param prescribedMedications the list of prescribed medications to set
     */
    public void setPrescribedMedication(List<PrescribedMedication> prescribedMedications) {
        this.prescribedMedications = prescribedMedications;
    }

    /**
     * Returns the consultation notes recorded during the appointment.
     *
     * @return the consultation notes
     */
    public String getConsultationNotes() {
        return consultationNotes;
    }

    /**
     * Sets the consultation notes for the appointment.
     *
     * @param consultationNotes the consultation notes to set
     */
    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    /**
     * Returns the status of the prescribed medication, indicating whether it is pending or dispensed.
     *
     * @return the medication status
     */
    public MedicationStatus getMedicationStatus() {
        return medicationStatus;
    }

    /**
     * Sets the status of the prescribed medication.
     *
     * @param medicationStatus the medication status to set
     */
    public void setMedicationStatus(MedicationStatus medicationStatus) {
        this.medicationStatus = medicationStatus;
    }
}
