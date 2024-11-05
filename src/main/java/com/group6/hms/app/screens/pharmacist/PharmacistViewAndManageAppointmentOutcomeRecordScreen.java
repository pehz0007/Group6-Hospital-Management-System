package com.group6.hms.app.screens.pharmacist;

import com.group6.hms.app.managers.appointment.AppointmentManager;
import com.group6.hms.app.managers.appointment.AppointmentManagerHolder;
import com.group6.hms.app.managers.inventory.InventoryManager;
import com.group6.hms.app.managers.appointment.models.AppointmentOutcomeRecord;
import com.group6.hms.app.managers.inventory.InventoryManagerHolder;
import com.group6.hms.app.managers.inventory.models.MedicationStatus;
import com.group6.hms.app.managers.inventory.models.MedicationStock;
import com.group6.hms.app.managers.inventory.models.PrescribedMedication;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;
import com.group6.hms.framework.screens.pagination.SinglePaginationTableScreen;

import java.util.List;
import java.util.UUID;

public class PharmacistViewAndManageAppointmentOutcomeRecordScreen extends SinglePaginationTableScreen<AppointmentOutcomeRecord> {

    private final int UPDATE_PRESCRIPTION_STATUS = 4;

    private final AppointmentManager appointmentManager = AppointmentManagerHolder.getAppointmentManager();
    private final InventoryManager inventoryManager = InventoryManagerHolder.getInventoryManager();

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
