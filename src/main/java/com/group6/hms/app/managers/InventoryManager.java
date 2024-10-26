package com.group6.hms.app.managers;

import com.group6.hms.app.models.Medication;
import com.group6.hms.app.models.ReplenishmentRequest;
import com.group6.hms.app.models.ReplenishmentRequestStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


// TODO: load medication list
public class InventoryManager {
    private List<Medication> medicationList;
    private List<ReplenishmentRequest> replenishmentRequestList;

    public InventoryManager() {
        medicationList = new ArrayList<>();
        replenishmentRequestList = new ArrayList<>();
    }

    public List<Medication> getAllMedications() {
        return medicationList;
    }

    // translate the Medication names to the actual Medication objects
    // for Pharmacist to translate the medication names in AppointmentOutcomeRecord to a list of Medication
    public List<Medication> getMedicationByNames(List<String> medicationNameList) {
        return medicationList.stream().filter(med -> medicationNameList.contains(med.getName())).toList();
    }

    // for Pharmacist to submit request to Administrator
    public void submitReplenishmentRequest(List<ReplenishmentRequest> requests) {
        replenishmentRequestList.addAll(requests);
    }

    // for Pharmacist to call after dispensing medication
    public void decreaseMedicationStock(UUID medicationId, int amountUsed) {
        Medication medicationToDecrease = medicationList.stream().filter(med-> med.getMedicationId().equals(medicationId)).findFirst().orElse(null);
        if (medicationToDecrease == null) {
            System.err.println("Medication does not exist.");
            return;
        }
        int previousStockLevel = medicationToDecrease.getCurrentStock();
        medicationToDecrease.minusStock(amountUsed);
        int newStockLevel = medicationToDecrease.getCurrentStock();
        System.out.printf("Stock for %s decreased from %d to %d\n", medicationToDecrease.getName(), previousStockLevel, newStockLevel);
    }

    // for Administrator to approve the Pharmacist's requests
    public void approveReplenishmentRequest(ReplenishmentRequest request) {
        request.setReplenishmentRequestStatus(ReplenishmentRequestStatus.APPROVED);
        Medication medicationToReplenish = medicationList.stream().filter(med -> med.getMedicationId().equals(request.getMedicationId())).findFirst().orElse(null);
        if (medicationToReplenish == null) {
            System.err.println("Medication does not exist.");
            return;
        }
        int previousStockLevel = medicationToReplenish.getCurrentStock();
        medicationToReplenish.addStock(request.getAmountToReplenish());
        int newStockLevel = medicationToReplenish.getCurrentStock();
        System.out.printf("Stock for %s increased from %d to %d\n", medicationToReplenish.getName(), previousStockLevel, newStockLevel);
    }

    // for Administrator to get requests
    public List<ReplenishmentRequest> getReplenishmentRequestByStatus(ReplenishmentRequestStatus status) {
        return replenishmentRequestList.stream().filter(req -> req.getReplenishmentRequestStatus().equals(status)).toList();
    }

    // for Administrator to get requests
    public List<ReplenishmentRequest> getAllReplenishmentRequest() {
        return replenishmentRequestList;
    }

    // for Administrator to determine whether to refill medication
    public boolean isStockLow(Medication medication) {
        return medication.getCurrentStock() < medication.getLowStockLevelLimit();
    }

}
