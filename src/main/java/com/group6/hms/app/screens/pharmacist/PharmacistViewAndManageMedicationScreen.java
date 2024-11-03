package com.group6.hms.app.screens.pharmacist;

import com.group6.hms.app.managers.inventory.InventoryManager;
import com.group6.hms.app.managers.inventory.models.MedicationStock;
import com.group6.hms.app.managers.inventory.models.ReplenishmentRequest;
import com.group6.hms.app.managers.inventory.models.ReplenishmentRequestStatus;
import com.group6.hms.framework.screens.pagination.PaginationTableScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PharmacistViewAndManageMedicationScreen extends PaginationTableScreen<MedicationStock> {

    private InventoryManager inventoryManager;

    //PHARMACIST OPTIONS
    private final int SUBMIT_REPLENISHMENT_REQUEST = 4;

    public PharmacistViewAndManageMedicationScreen() {
        super("Medications", null);
        this.inventoryManager = new InventoryManager();
        updateMedicationStocks();

        addOption(SUBMIT_REPLENISHMENT_REQUEST, "Submit Replenishment Request");
    }

    private void updateMedicationStocks() {
        List<MedicationStock> medications = inventoryManager.getAllMedicationStock();
        setList(medications);
    }

    @Override
    protected void handleOption(int optionId) {
        switch (optionId){
            case SUBMIT_REPLENISHMENT_REQUEST -> {
                submitReplenishmentRequest();
            }

        }
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
        waitForKeyPress();
    }

}
