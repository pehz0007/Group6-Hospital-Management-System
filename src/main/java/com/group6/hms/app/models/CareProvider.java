package com.group6.hms.app.models;

import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.app.storage.MultiStorageProvider;
import com.group6.hms.app.storage.SerializationMapStorageProvider;
import com.group6.hms.app.storage.StorageProvider;

import java.nio.file.Path;
import java.util.Collection;
import java.util.UUID;

/**
 * The {@code CareProvider} class manages the relationships between patients and doctors,
 * allowing for bidirectional mapping between the two. This facilitates tracking which
 * patients are under a doctor's care and which doctors are associated with a patient's care.
 */
public class CareProvider {

    //We store the mapping twice as a bidirectional map
    // From Patient UUID to Doctor UUID
    private MultiStorageProvider<UUID, UUID> storageFromPatientToDoctorID = new SerializationMapStorageProvider<>(Path.of("data", "care_provider"));
    // From Doctor UUID to Patient UUID
    private MultiStorageProvider<UUID, UUID> storageFromDoctorToPatientID = new SerializationMapStorageProvider<>(Path.of("data", "care_provider"));

    /**
     * Retrieves the IDs of all patients under the care of a specified doctor.
     *
     * @param doctor the doctor whose patient IDs are to be retrieved
     * @return a collection of patient UUIDs under the specified doctor's care
     */
    public Collection<UUID> getPatientIDsUnderDoctorCare(Doctor doctor) {
        StorageProvider<UUID> patientUUIDs = storageFromDoctorToPatientID.getStorageProvider(doctor.getSystemUserId());
        return patientUUIDs.getItems();
    }

    /**
     * Retrieves the IDs of all doctors associated with a specified patient's care.
     *
     * @param patient the patient whose doctor IDs are to be retrieved
     * @return a collection of doctor UUIDs associated with the specified patient's care
     */
    public Collection<UUID> getDoctorIDsFromPatientCareProvider(Patient patient) {
        StorageProvider<UUID> doctorUUIDs = storageFromPatientToDoctorID.getStorageProvider(patient.getSystemUserId());
        return doctorUUIDs.getItems();
    }

    /**
     * Adds a patient under the care of a specified doctor. This method updates both
     * the doctor-to-patient and patient-to-doctor mappings and saves the changes.
     *
     * @param patient the patient to be added
     * @param doctor  the doctor under whose care the patient is added
     */
    public void addPatientToDoctorCare(Patient patient, Doctor doctor){
        StorageProvider<UUID> patientUUIDs = storageFromDoctorToPatientID.getStorageProvider(doctor.getSystemUserId());
        StorageProvider<UUID> doctorUUIDs = storageFromPatientToDoctorID.getStorageProvider(patient.getSystemUserId());
        patientUUIDs.addNewItem(patient.getSystemUserId());
        doctorUUIDs.addNewItem(doctor.getSystemUserId());
        saveStorageProviders(patient, doctor);
    }

    /**
     * Removes a patient from the care of a specified doctor. This method updates both
     * the doctor-to-patient and patient-to-doctor mappings and saves the changes.
     *
     * @param patient the patient to be removed
     * @param doctor  the doctor from whose care the patient is removed
     */
    public void removePatientFromDoctorCare(Patient patient, Doctor doctor){
        StorageProvider<UUID> patientUUIDs = storageFromDoctorToPatientID.getStorageProvider(doctor.getSystemUserId());
        StorageProvider<UUID> doctorUUIDs = storageFromPatientToDoctorID.getStorageProvider(patient.getSystemUserId());
        patientUUIDs.removeItem(doctor.getSystemUserId());
        doctorUUIDs.removeItem(patient.getSystemUserId());
        saveStorageProviders(patient, doctor);
    }


    /**
     * Saves the updated mappings for both the patient and doctor to persistent storage.
     *
     * @param patient the patient whose mappings are saved
     * @param doctor  the doctor whose mappings are saved
     */
    private void saveStorageProviders(Patient patient, Doctor doctor) {
        storageFromDoctorToPatientID.saveStorageProvider(doctor.getSystemUserId());
        storageFromPatientToDoctorID.saveStorageProvider(patient.getSystemUserId());
    }


}
