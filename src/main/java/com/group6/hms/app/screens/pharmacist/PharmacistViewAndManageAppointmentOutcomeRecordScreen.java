package com.group6.hms.app.screens.pharmacist;

import com.group6.hms.app.managers.AppointmentManager;
import com.group6.hms.app.managers.InventoryManager;
import com.group6.hms.app.models.AppointmentOutcomeRecord;
import com.group6.hms.app.models.MedicationStatus;
import com.group6.hms.app.models.MedicationStock;
import com.group6.hms.app.models.PrescribedMedication;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;
import com.group6.hms.framework.screens.pagination.SinglePaginationTableScreen;

import java.util.List;
import java.util.UUID;

/**
 * The {@code PharmacistViewandManageAppointmentOutcomeRecordScreen} allows pharmacists to
 * view appointment outcome records.
 * It allows pharmacists to dispense update the status of prescribed medications.
 */
public class PharmacistViewAndManageAppointmentOutcomeRecordScreen extends SinglePaginationTableScreen<AppointmentOutcomeRecord> {

    private final int UPDATE_PRESCRIPTION_STATUS = 4;

    private final AppointmentManager appointmentManager = new AppointmentManager(); //initialise appointment manager
    private final InventoryManager inventoryManager = new InventoryManager(); // initialise inv manager

    /**
     * Constructor to initialize the PharmacistViewAndManageAppointmentOutcomeRecordScreen.
     *
     * @param items The list of appointment outcome records to display.
     */
    public PharmacistViewAndManageAppointmentOutcomeRecordScreen(List<AppointmentOutcomeRecord> items) {
        super("Appointment Outcome Record", items);

        addOption(UPDATE_PRESCRIPTION_STATUS, "Update Prescription Status");
    }

    @Override
    public void displaySingleItem(AppointmentOutcomeRecord item) {
        PrintTableUtils.printItemAsVerticalTable(consoleInterface, new AppointmentOutcomeRecordView(item));
//                    println("===================================================");
//                    println("Appointment Outcome Records:");
//
//                    println("Record ID: " + item.getRecordId());
//                    println("Date of Appointment: " + item.getDateOfAppointment());
//                    println("Consultation Note: " + item.getConsultationNotes());
//                    println("Medication Status: " + item.getMedicationStatus());
//                    println("Patient ID: " + item.getPatientId());
//
//                    // display all prescribed medications with their quantities
//                    for (PrescribedMedication prescribedMedication : item.getPrescribedMedications()) {
//                        println("Medication: " + prescribedMedication.getName());
//                        println("Quantity Needed: " + prescribedMedication.getQuantityToPrescribe());
//                    }
//                    println("===================================================");
    }

    @Override
    protected void handleOption(int optionId) {
        super.handleOption(optionId);
        if(optionId == UPDATE_PRESCRIPTION_STATUS) {
            updatePrescriptionStatus();
        }
    }

    //case 3: update prescription status and dispense medication
    /**
     * Prompts the pharmacist to update the medication status of a specific appointment outcome record.
     * If the status is updated to DISPENSED, the corresponding medication stock is decreased accordingly.
     */
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
        waitForKeyPress();
    }

}
