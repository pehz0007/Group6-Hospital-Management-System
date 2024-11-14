package com.group6.hms.app.screens.pharmacist;

import com.group6.hms.app.managers.appointment.AppointmentManager;
import com.group6.hms.app.managers.appointment.AppointmentManagerHolder;
import com.group6.hms.app.managers.appointment.models.AppointmentOutcomeRecord;
import com.group6.hms.app.managers.auth.LogoutScreen;
import com.group6.hms.app.managers.inventory.models.MedicationStatus;
import com.group6.hms.app.roles.Pharmacist;
import com.group6.hms.app.screens.MainScreen;

import java.util.List;

public class PharmacistScreen extends LogoutScreen {
    private AppointmentManager appointmentManager = AppointmentManagerHolder.getAppointmentManager();

    /**
     * Constructor to initialize the PharmacistScreen.
     */
    public PharmacistScreen() {
        super("Pharmacist Menu");
        addOption(2, "View Appointment Outcome Record");
        addOption(3, "View Medication Inventory");
        addOption(4, "Edit Profile");
    }

    /**
     * Displays a welcome message and initializes the currently logged-in pharmacist.
     */
    @Override
    public void onStart() {
        println("Welcome, " + getLoginManager().getCurrentlyLoggedInUser().getUserId());
        super.onStart();
    }

    /**
     * Navigates to the main screen upon logout.
     */
    @Override
    protected void onLogout() {
        newScreen(new MainScreen());
    }


    /**
     * Handles the user's option selection from the menu.
     *
     * @param optionId The ID of the selected menu option.
     */
    @Override
    protected void handleOption(int optionId) {
        switch (optionId) {
            case 2 -> viewAppointmentOutcomeRecords();
            case 3 -> viewMedicationInventory();
            case 4 -> navigateToScreen(new PharmacistConfigurationScreen((Pharmacist) getLoginManager().getCurrentlyLoggedInUser()));
            default -> println("Invalid option. Please choose a valid option.");
        }
    }

    // case 2: view Appointment Outcome Records
    /**
     * Prompts the pharmacist to enter a medication status to filter the appointment outcome records.
     * Retrieves and navigates to the screen displaying the filtered records.
     */
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
    /**
     * Navigates to the screen for viewing and managing the medication inventory.
     */
    private void viewMedicationInventory() {
        navigateToScreen(new PharmacistViewAndManageMedicationScreen());
    }
}
