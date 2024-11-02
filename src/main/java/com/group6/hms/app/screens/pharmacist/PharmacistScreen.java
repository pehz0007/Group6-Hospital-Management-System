package com.group6.hms.app.screens.pharmacist;

import com.group6.hms.app.auth.LogoutScreen;
import com.group6.hms.app.managers.AppointmentManager;
import com.group6.hms.app.managers.InventoryManager;
import com.group6.hms.app.models.*;
import com.group6.hms.app.models.MedicationStatus;
import com.group6.hms.app.roles.Pharmacist;
import com.group6.hms.app.screens.MainScreen;
import com.group6.hms.framework.screens.pagination.SinglePaginationTableScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PharmacistScreen extends LogoutScreen {
    private final AppointmentManager appointmentManager = new AppointmentManager(); //initialise appointment manager
    private final InventoryManager inventoryManager = new InventoryManager(); // initialise inv manager

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
        println("Welcome, " + getLoginManager().getCurrentlyLoggedInUser().getUserId());
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
            case 4 -> viewMedicationInventory();
            case 5 -> submitReplenishmentRequest();
            default -> println("Invalid option. Please choose a valid option.");
        }
    }

    // case 2: view Appointment Outcome Records
    private void viewAppointmentOutcomeRecords() {
        println("Enter the medication status to filter by (e.g., PENDING, DISPENSED): ");
        String statusInput = readString().toUpperCase();
        try {
            MedicationStatus status = MedicationStatus.valueOf(statusInput);
            List<AppointmentOutcomeRecord> records = appointmentManager.getAppointmentOutcomeRecordsByStatus(status);

            if (records.isEmpty()) {
                println("No records found with the specified medication status.");
                return;
            }

            navigateToScreen(new SinglePaginationTableScreen<>("Records", records) {
                @Override
                public void displaySingleItem(AppointmentOutcomeRecord item) {
                    println("===================================================");
                    println("Appointment Outcome Records:");

                    println("Record ID: " + item.getRecordId());
                    println("Date of Appointment: " + item.getDateOfAppointment());
                    println("Consultation Note: " + item.getConsultationNotes());
                    println("Medication Status: " + item.getMedicationStatus());
                    println("Patient ID: " + item.getPatientId());

                    // display all prescribed medications with their quantities
                    for (PrescribedMedication prescribedMedication : item.getPrescribedMedications()) {
                        println("Medication: " + prescribedMedication.getName());
                        println("Quantity Needed: " + prescribedMedication.getQuantityToPrescribe());
                    }
                    println("===================================================");
                }
            });
        } catch (IllegalArgumentException e) {
            println("Invalid medication status. Please enter a valid status.");
        }
    }

    //case 3: update prescription status and dispense medication
    private void updatePrescriptionStatus() {
        println("Enter the ID of the appointment outcome record you want to update: ");
        String recordId = readString();

        println("Enter the new medication status (e.g., PENDING, DISPENSED): ");
        String statusInput = readString().toUpperCase();
        try {
            MedicationStatus status = MedicationStatus.valueOf(statusInput);
            AppointmentOutcomeRecord record = appointmentManager.getAllAppointmentOutcomeRecords()
                    .stream()
                    .filter(rec -> rec.getRecordId().equals(UUID.fromString(recordId)))
                    .findFirst()
                    .orElse(null);

            if (record != null) {
                appointmentManager.updateAppointmentOutcomeRecordMedicationStatus(record, status);
                println("Successfully updated the prescription status.");

                if (status == MedicationStatus.DISPENSED) {
                    for (PrescribedMedication prescribedMedication : record.getPrescribedMedications()) {
                        int quantity = prescribedMedication.getQuantityToPrescribe();

                        MedicationStock stock = inventoryManager.getMedicationStock(prescribedMedication);

                        if (stock != null && stock.getCurrentStock() >= quantity) {
                            inventoryManager.decreaseMedicationStock(prescribedMedication, quantity);
                            println("Medication " + prescribedMedication.getName() + " dispensed. Stock updated.");
                        } else {
                            println("Insufficient stock or medication not found for " + prescribedMedication.getName());
                        }
                    }
                }
            } else {
                println("No matching appointment outcome record found.");
            }
        } catch (IllegalArgumentException e) {
            println("Invalid medication status. Please enter a valid status.");
        }
    }

    // case 4: view Medication Inventory
    private void viewMedicationInventory() {
        List<MedicationStock> medications = inventoryManager.getAllMedicationStock();

        // check if medications are retrieved successfully
        if (medications.isEmpty()) {
            println("No medications found in the inventory.");
            return; // exit
        }

        // navigate to the ViewAndManageMedicationScreen
        ViewAndManageMedicationScreen screen = new ViewAndManageMedicationScreen("Medication Inventory", medications);
        navigateToScreen(screen);
    }

    // case 5: submit replenishment request
    private void submitReplenishmentRequest() {
        List<MedicationStock> lowStockMedications = inventoryManager.getAllMedicationStock().stream()
                .filter(inventoryManager::isStockLow)
                .toList();

        if (lowStockMedications.isEmpty()) {
            println("All medications have sufficient stock levels.");
            return;
        }

        println("Medications with low stock:");
        for (MedicationStock medicationStock : lowStockMedications) {
            println("Medication: " + medicationStock.getMedication().getName());
            println("Current Stock: " + medicationStock.getCurrentStock());
            println("Low Stock Level Limit: " + medicationStock.getLowStockLevelLimit());
        }

        println("Would you like to submit a replenishment request for all low stock medications? (yes/no)");
        String confirmation = readString().toLowerCase();

        if (confirmation.equals("yes")) {
            List<ReplenishmentRequest> requests = new ArrayList<>(); // i create a list to hold requests
            for (MedicationStock medicationStock : lowStockMedications) {
                // create a new replenishment request for each low stock medication
                ReplenishmentRequest request = new ReplenishmentRequest(
                        UUID.randomUUID(),
                        medicationStock.getMedication(),
                        0, // set a default amount to replenish?? (0 or any other constant)
                        ReplenishmentRequestStatus.PENDING
                );
                requests.add(request); // add the request to the list
            }

            // submit request
            inventoryManager.submitReplenishmentRequest(requests);
            println("Replenishment requests submitted for low stock medications.");
        } else {
            println("Replenishment request canceled.");
        }
    }
}
