package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.auth.LoginManagerHolder;
import com.group6.hms.app.auth.User;
import com.group6.hms.app.managers.AppointmentManager;
import com.group6.hms.app.managers.AvailabilityManager;
import com.group6.hms.app.models.BloodType;
import com.group6.hms.app.models.CareProvider;
import com.group6.hms.app.models.MedicalRecord;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.storage.SerializationStorageProvider;
import com.group6.hms.framework.screens.Screen;

import java.io.File;
import java.time.LocalDate;

/**
 * The {@code UpdatePatientMedicalScreen} class represents the screen for updating a patient's medical records.
 */
public class UpdatePatientMedicalScreen extends Screen {
    private final AppointmentManager appointmentManager = new AppointmentManager();
    private final LoginManager loginManager = LoginManagerHolder.getLoginManager();
    private final AvailabilityManager availabilityManager = new AvailabilityManager();
    private final SerializationStorageProvider<User> userStorageProvider = new SerializationStorageProvider<>();
    private final Doctor doc;
    private Patient patient;
    private final File userFile = new File("data/users.ser");

    @Override
    public void onStart() {
        super.onStart();
        Doctor doc = (Doctor) loginManager.getCurrentlyLoggedInUser (); // Assuming current user is a doctor

    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        updatePatientMedicalRecords(patient);
    }

    /**
     * Initializes the UpdatePatientMedicalScreen.
     *
     * @param patient the Patient whose medical records are to be updated
     */
    public UpdatePatientMedicalScreen(Patient patient) {
        super("Update " + patient.getName() + " Medical Records");
        this.doc = DoctorScreen.getDoctor();
        this.patient = patient;// Assuming current user is a doctor
        //updatePatientMedicalRecords(patient);
    }

    /**
     * Updates the medical records of the specified patient.
     *
     * @param patient the Patient whose medical records are to be updated
     */
    protected void updatePatientMedicalRecords(Patient patient) {
        userStorageProvider.loadFromFile(userFile);
        try {
            // Display current medical record details
            println("Current Blood Type: " + patient.getMedicalRecord().getBloodType());
            println("Enter New Blood Type: ");
            String newBloodType = readString();

            // Validate blood type input
            BloodType bloodtype = BloodType.fromString(newBloodType);
            if (bloodtype == null) {
                println("Invalid blood type entered. Please try again.");
                return;
            }

            println("Current Date of Birth: " + patient.getMedicalRecord().getDateOfBirth());
            println("Enter New Date of Birth (yyyy-mm-dd): ");
            String newDateString = readString();

            // Parse new date of birth
            LocalDate newDate;
            try {
                newDate = LocalDate.parse(newDateString);
            } catch (Exception e) {
                println("Invalid date format. Please enter a valid date.");
                return;
            }

            // Create and update medical record
            MedicalRecord medicalRecord = new MedicalRecord();
            medicalRecord.setDateOfBirth(newDate);
            medicalRecord.setBloodType(bloodtype);
            patient.updateMedicalRecord(medicalRecord);

            // Save updated users to file
            loginManager.saveUsersToFile();
            println("Medical records updated successfully for " + patient.getName() + ".");
            navigateBack();
        } finally {
            // Close the scanner to prevent resource leaks
        }
    }
}