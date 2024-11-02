package com.group6.hms.app.screens.pharmacist;

import com.group6.hms.app.auth.LogoutScreen;
import com.group6.hms.app.managers.AppointmentManager;
import com.group6.hms.app.managers.InventoryManager;
import com.group6.hms.app.models.*;
import com.group6.hms.app.models.MedicationStatus;
import com.group6.hms.app.roles.Pharmacist;
import com.group6.hms.app.screens.MainScreen;
import com.group6.hms.app.screens.admin.AdminViewAndManageMedicationScreen;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;
import com.group6.hms.framework.screens.pagination.SinglePaginationTableScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PharmacistScreen extends LogoutScreen {
    private final AppointmentManager appointmentManager = new AppointmentManager(); //initialise appointment manager

    /**
     * Constructor to initialize the PharmacistScreen.
     */
    public PharmacistScreen() {
        super("Pharmacist Menu");
        addOption(2, "View Appointment Outcome Record");
        addOption(3, "View Medication Inventory");
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
            case 3 -> viewMedicationInventory();
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
            navigateToScreen(new PharmacistViewAndManageAppointmentOutcomeRecordScreen(records));
        } catch (IllegalArgumentException e) {
            println("Invalid medication status. Please enter a valid status.");
        }
    }

    // case 4: view Medication Inventory
    private void viewMedicationInventory() {
        navigateToScreen(new PharmacistViewAndManageMedicationScreen());
//        List<MedicationStock> medications = inventoryManager.getAllMedicationStock();
//
//        // check if medications are retrieved successfully
//        if (medications.isEmpty()) {
//            println("No medications found in the inventory.");
//            return; // exit
//        }
//
//        // navigate to the ViewAndManageMedicationScreen
//        ViewAndManageMedicationScreen screen = new ViewAndManageMedicationScreen("Medication Inventory", medications);
//        navigateToScreen(screen);
    }


}
