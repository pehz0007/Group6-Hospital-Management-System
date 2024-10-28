package com.group6.hms.app.models;

import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.app.storage.MultiStorageProvider;
import com.group6.hms.app.storage.SerializationMapStorageProvider;
import com.group6.hms.app.storage.StorageProvider;

import java.nio.file.Path;
import java.util.Collection;
import java.util.UUID;

public class CareProvider {

    //We store the mapping twice as a bidirectional map
    // From Patient UUID to Doctor UUID
    private MultiStorageProvider<UUID, UUID> storageFromPatientToDoctorID = new SerializationMapStorageProvider<>(Path.of("data", "care_provider"));
    // From Doctor UUID to Patient UUID
    private MultiStorageProvider<UUID, UUID> storageFromDoctorToPatientID = new SerializationMapStorageProvider<>(Path.of("data", "care_provider"));

    public Collection<UUID> getPatientIDsUnderDoctorCare(Doctor doctor) {
        StorageProvider<UUID> patientUUIDs = storageFromDoctorToPatientID.getStorageProvider(doctor.getSystemUserId());
        return patientUUIDs.getItems();
    }

    public Collection<UUID> getDoctorIDsFromPatientCareProvider(Patient patient) {
        StorageProvider<UUID> doctorUUIDs = storageFromPatientToDoctorID.getStorageProvider(patient.getSystemUserId());
        return doctorUUIDs.getItems();
    }

    public void addPatientToDoctorCare(Patient patient, Doctor doctor){
        StorageProvider<UUID> patientUUIDs = storageFromDoctorToPatientID.getStorageProvider(doctor.getSystemUserId());
        StorageProvider<UUID> doctorUUIDs = storageFromPatientToDoctorID.getStorageProvider(patient.getSystemUserId());
        patientUUIDs.addNewItem(patient.getSystemUserId());
        doctorUUIDs.addNewItem(doctor.getSystemUserId());
        saveStorageProviders(patient, doctor);
    }

    public void removePatientFromDoctorCare(Patient patient, Doctor doctor){
        StorageProvider<UUID> patientUUIDs = storageFromDoctorToPatientID.getStorageProvider(doctor.getSystemUserId());
        StorageProvider<UUID> doctorUUIDs = storageFromPatientToDoctorID.getStorageProvider(patient.getSystemUserId());
        patientUUIDs.removeItem(doctor.getSystemUserId());
        doctorUUIDs.removeItem(patient.getSystemUserId());
        saveStorageProviders(patient, doctor);
    }

    private void saveStorageProviders(Patient patient, Doctor doctor) {
        storageFromDoctorToPatientID.saveStorageProvider(doctor.getSystemUserId());
        storageFromPatientToDoctorID.saveStorageProvider(patient.getSystemUserId());
    }


}
