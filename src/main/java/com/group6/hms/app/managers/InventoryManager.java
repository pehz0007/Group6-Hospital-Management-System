package com.group6.hms.app.managers;

import com.group6.hms.app.models.Medication;
import com.group6.hms.app.models.MedicationStock;
import com.group6.hms.app.models.ReplenishmentRequest;
import com.group6.hms.app.models.ReplenishmentRequestStatus;
import com.group6.hms.app.storage.SerializationStorageProvider;
import com.group6.hms.app.storage.StorageProvider;

import java.io.File;
import java.util.List;


/**
 * The {@code InventoryManager} class manages the medication inventory for the system,
 * including medication stock levels and replenishment requests. It provides methods
 * for pharmacists and administrators to manage stock levels, submit and approve
 * replenishment requests, and monitor low stock levels.
 */
public class InventoryManager {
    private static final File medicationStockFile = new File("data/medications.ser");
    private static final File replenishmentRequestFile = new File("data/replenishments.ser");
    private final StorageProvider<MedicationStock> medicationStockStorageProvider = new SerializationStorageProvider<>();
    private final StorageProvider<ReplenishmentRequest> replenishmentStorageProvider = new SerializationStorageProvider<>();

    /**
     * Initializes the {@code InventoryManager}, loading existing medication stock
     * and replenishment requests from storage files. If the medication stock file
     * does not exist, an error message is displayed.
     */
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

    /**
     * Retrieves all medication stock records.
     *
     * @return a {@code List} of all {@code MedicationStock} objects in the inventory
     */
    public List<MedicationStock> getAllMedicationStock() {
        return medicationStockStorageProvider.getItems().stream().toList();
    }

    /**
     * Retrieves the stock information for a specific medication.
     *
     * @param medication the {@code Medication} to search for
     * @return the {@code MedicationStock} object for the specified medication, or {@code null} if not found
     */
    public MedicationStock getMedicationStock(Medication medication) {
        return medicationStockStorageProvider.getItems().stream()
                .filter(stock -> stock.getMedication().getName().equals(medication.getName()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves a list of all medications in the system, excluding their stock quantities.
     * This method is intended for doctors to select medications for prescriptions.
     *
     * @return a {@code List} of {@code Medication} objects without stock information
     */
    public List<Medication> getAllMedication() {
        return medicationStockStorageProvider.getItems().stream().map(MedicationStock::getMedication).toList();
    }

    /**
     * Retrieves a specific replenishment request by its ID.
     *
     * @param requestId the ID of the replenishment request
     * @return the {@code ReplenishmentRequest} object with the specified ID, or {@code null} if not found
     */
    public ReplenishmentRequest getReplenishmentRequestById(String requestId) {
        return replenishmentStorageProvider.getItems().stream().filter(req -> req.getRequestId().toString().equals(requestId)).findFirst().orElse(null);
    }

    /**
     * Submits a list of replenishment requests for administrator approval.
     * This method is intended for pharmacists to request stock replenishment.
     *
     * @param requests a {@code List} of {@code ReplenishmentRequest} objects to submit
     */
    public void submitReplenishmentRequest(List<ReplenishmentRequest> requests) {
        for (ReplenishmentRequest request : requests) {
            replenishmentStorageProvider.addNewItem(request);
        }
        replenishmentStorageProvider.saveToFile(replenishmentRequestFile);
    }

    /**
     * Decreases the stock level of a specified medication by a given amount.
     * This method is used by pharmacists when dispensing medication.
     *
     * @param med        the {@code Medication} to decrease stock for
     * @param amountUsed the amount to decrease the stock by
     */
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

    /**
     * Approves a replenishment request submitted by a pharmacist and updates the medication stock.
     * This method is intended for administrators to fulfill replenishment requests.
     *
     * @param request the {@code ReplenishmentRequest} to approve
     */
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

    /**
     * Retrieves all replenishment requests with a specific status.
     * This method is intended for administrators to view requests by status.
     *
     * @param status the {@code ReplenishmentRequestStatus} to filter requests by
     * @return a {@code List} of {@code ReplenishmentRequest} objects with the specified status
     */
    public List<ReplenishmentRequest> getReplenishmentRequestByStatus(ReplenishmentRequestStatus status) {
        return replenishmentStorageProvider.getItems().stream().filter(req -> req.getReplenishmentRequestStatus().equals(status)).toList();
    }

    /**
     * Retrieves all replenishment requests in the system.
     * This method is intended for administrators to view all replenishment requests.
     *
     * @return a {@code List} of all {@code ReplenishmentRequest} objects
     */
    public List<ReplenishmentRequest> getAllReplenishmentRequest() {
        return replenishmentStorageProvider.getItems().stream().toList();
    }

    /**
     * Checks if the stock level of a specific medication is below the defined low stock limit.
     *
     * @param medicationStock the {@code MedicationStock} to check
     * @return {@code true} if the stock level is below the low stock limit, {@code false} otherwise
     */
    public boolean isStockLow(MedicationStock medicationStock) {
        return medicationStock.getCurrentStock() < medicationStock.getLowStockLevelLimit();
    }

}
