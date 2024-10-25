package com.group6.hms.app.screens.pharmacist;

import com.group6.hms.app.auth.LogoutScreen;
import com.group6.hms.app.managers.AppointmentManager;
import com.group6.hms.app.models.AppointmentOutcomeRecord;
import com.group6.hms.app.MedicationStatus;
import com.group6.hms.app.models.Medication;
import com.group6.hms.app.roles.Pharmacist;
import com.group6.hms.app.screens.MainScreen;
import com.group6.hms.framework.screens.pagination.PaginationTableScreen;
import com.group6.hms.framework.screens.pagination.SinglePaginationTableScreen;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class PharmacistScreen extends LogoutScreen {
    private final AppointmentManager appointmentManager = new AppointmentManager();

    /**
     * Constructor to initialize the PharmacistScreen.
     */
    public PharmacistScreen() {
        super("Pharmacist Menu");
        addOption(2, "View Appointment Outcome Record");
        addOption(3, "Update Prescription Status");
        addOption(4, "View Medication Inventory");
        addOption(5, "Submit Replenishment Request");
    }

    @Override
    public void onStart() {
        println("Welcome, " + getLoginManager().getCurrentlyLoggedInUser().getUsername());
        Pharmacist pharmacist = (Pharmacist) getLoginManager().getCurrentlyLoggedInUser();
        super.onStart();
    }

    @Override
    protected void onLogout() {
        newScreen(new MainScreen());
    }

    @Override
    protected void handleOption(int optionId) {
        switch (optionId) {
            case 2 -> viewAppointmentOutcomeRecords();
            case 3 -> updatePrescriptionStatus();
            default -> println("Invalid option. Please choose a valid option.");
        }
    }

    private void viewAppointmentOutcomeRecords() {
        println("Enter the medication status to filter by (e.g., PENDING, DISPENSED): ");
        String statusInput = readString().toUpperCase();
        try {
            MedicationStatus status = MedicationStatus.valueOf(statusInput);
            List<AppointmentOutcomeRecord> records = appointmentManager.getAppointmentOutcomeRecordsByStatus(status);


            navigateToScreen(new SinglePaginationTableScreen<>("Records", records) {
                @Override
                public void displaySingleItem(AppointmentOutcomeRecord item) {
                    println("===================================================");
                    println("Appointment Outcome Records:");

                    println("Record ID: " + item.getRecordId());
                    println("Date of Appointment: " + item.getDateOfAppointment());
                    println("Type of Service: " + item.getServiceType());
                    println("Consultation Note: " + item.getConsultationNotes());
                    println("Medication Status: " + item.getMedicationStatus());
                    println("Patient ID: " + item.getPatientId());

                    for (Medication medication : item.getPrescribedMedication()){
                        println("Medication:" + medication.getName());
                    }
                    println("===================================================");
                }
            });

//            if (records.isEmpty()) {
//                println("No records found with the specified medication status.");
//            } else {
//                println("Appointment Outcome Records:");
//                for (AppointmentOutcomeRecord record : records) {
//                    println("Record ID: " + record.getRecordId());
//                }
//            }
        } catch (IllegalArgumentException e) {
            println("Invalid medication status. Please enter a valid status.");
        }
    }

    private void updatePrescriptionStatus() {
        println("Enter the ID of the appointment outcome record you want to update: ");
        String recordId = readString();

        println("Enter the new medication status (e.g., PENDING, DISPENSED): ");
        String statusInput = readString().toUpperCase();
        try {
            MedicationStatus status = MedicationStatus.valueOf(statusInput);
            // Get all records and find the one matching the given recordId
            AppointmentOutcomeRecord record = appointmentManager.getAllAppointmentOutcomeRecords() // A method to get all records
                    .stream()
                    .filter(rec -> rec.getRecordId().equals(UUID.fromString(recordId)))
                    .findFirst()
                    .orElse(null);

            if (record != null) {
                // Update the medication status
                appointmentManager.updateAppointmentOutcomeRecordMedicationStatus(record, status);
                println("Successfully updated the prescription status.");
            } else {
                println("No matching appointment outcome record found.");
            }
        } catch (IllegalArgumentException e) {
            println("Invalid medication status. Please enter a valid status.");
        }
    }

}
