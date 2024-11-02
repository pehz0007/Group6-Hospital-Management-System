package com.group6.hms.app.managers;

import com.group6.hms.app.models.Medication;
import com.group6.hms.app.models.MedicationStock;
import com.group6.hms.app.models.ReplenishmentRequest;
import com.group6.hms.app.models.ReplenishmentRequestStatus;
import com.group6.hms.app.storage.SerializationStorageProvider;
import com.group6.hms.app.storage.StorageProvider;

import java.io.File;
import java.util.List;


public class InventoryManager {
    private static final File medicationStockFile = new File("data/medications.ser");
    private static final File replenishmentRequestFile = new File("data/replenishments.ser");
    private final StorageProvider<MedicationStock> medicationStockStorageProvider = new SerializationStorageProvider<>();
    private final StorageProvider<ReplenishmentRequest> replenishmentStorageProvider = new SerializationStorageProvider<>();

    public InventoryManager() {
       if (!medicationStockFile.exists()) {
           System.err.println("Administrator must import Medication Stock first!");
           return;
       }
       if (!replenishmentRequestFile.exists()) {
           replenishmentStorageProvider.saveToFile(replenishmentRequestFile);
       }
       medicationStockStorageProvider.loadFromFile(medicationStockFile);
       replenishmentStorageProvider.loadFromFile(replenishmentRequestFile);
    }

    // to see what Medications are in our system + how much of each left
    public List<MedicationStock> getAllMedicationStock() {
        return medicationStockStorageProvider.getItems().stream().toList();
    }

    // retrieve the MedicationStock object for a specific medication
    public MedicationStock getMedicationStock(Medication medication) {
        return medicationStockStorageProvider.getItems().stream()
                .filter(stock -> stock.getMedication().getName().equals(medication.getName()))
                .findFirst()
                .orElse(null);
    }

    // to get Medication without the quantity
    // for Doctor to select what Medications can be prescribed
    public List<Medication> getAllMedication() {
        return medicationStockStorageProvider.getItems().stream().map(MedicationStock::getMedication).toList();
    }

    public ReplenishmentRequest getReplenishmentRequestById(String requestId) {
        return replenishmentStorageProvider.getItems().stream().filter(req -> req.getRequestId().toString().equals(requestId)).findFirst().orElse(null);
    }

    // for Pharmacist to submit request to Administrator
    public void submitReplenishmentRequest(List<ReplenishmentRequest> requests) {
        for (ReplenishmentRequest request : requests) {
            replenishmentStorageProvider.addNewItem(request);
        }
        replenishmentStorageProvider.saveToFile(replenishmentRequestFile);
    }

    // for Pharmacist to call when dispensing medication
    public void decreaseMedicationStock(Medication med, int amountUsed) {
        MedicationStock medicationToDecrease = medicationStockStorageProvider.getItems().stream().filter(stock-> stock.getMedication().getName().equals(med.getName())).findFirst().orElse(null);
        if (medicationToDecrease == null) {
            System.err.println("Medication does not exist.");
            return;
        }

        int previousStockLevel = medicationToDecrease.getCurrentStock();
        medicationToDecrease.minusStock(amountUsed);
        int newStockLevel = medicationToDecrease.getCurrentStock();

        medicationStockStorageProvider.saveToFile(medicationStockFile);
        System.out.printf("Stock for %s decreased from %d to %d\n", medicationToDecrease.getMedication().getName(), previousStockLevel, newStockLevel);
    }

    // for Administrator to approve the Pharmacist's requests
    public void approveReplenishmentRequest(ReplenishmentRequest request) {
        replenishmentStorageProvider.getItems().stream().filter(rep -> rep.getRequestId().equals(request.getRequestId())).findFirst().ifPresent(r -> {
            r.setReplenishmentRequestStatus(ReplenishmentRequestStatus.APPROVED);
        });
        MedicationStock medicationToReplenish = medicationStockStorageProvider.getItems().stream().filter(stock -> stock.getMedication().getName().equals(request.getMedication().getName())).findFirst().orElse(null);
        if (medicationToReplenish == null) {
            System.err.println("Medication does not exist.");
            return;
        }
        int previousStockLevel = medicationToReplenish.getCurrentStock();
        medicationToReplenish.addStock(request.getAmountToReplenish());
        int newStockLevel = medicationToReplenish.getCurrentStock();

        replenishmentStorageProvider.saveToFile(replenishmentRequestFile);
        medicationStockStorageProvider.saveToFile(medicationStockFile);
        System.out.printf("Stock for %s increased from %d to %d\n", medicationToReplenish.getMedication().getName(), previousStockLevel, newStockLevel);
    }

    // for Administrator to get requests
    public List<ReplenishmentRequest> getReplenishmentRequestByStatus(ReplenishmentRequestStatus status) {
        return replenishmentStorageProvider.getItems().stream().filter(req -> req.getReplenishmentRequestStatus().equals(status)).toList();
    }

    // for Administrator to get requests
    public List<ReplenishmentRequest> getAllReplenishmentRequest() {
        return replenishmentStorageProvider.getItems().stream().toList();
    }

    // for Administrator to determine whether to refill medication
    public boolean isStockLow(MedicationStock medicationStock) {
        return medicationStock.getCurrentStock() < medicationStock.getLowStockLevelLimit();
    }

}
