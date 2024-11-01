package com.group6.hms.app.models;

import com.group6.hms.app.screens.admin.MedicationRenderer;
import com.group6.hms.framework.screens.pagination.HeaderField;

import java.util.UUID;

public class ReplenishmentRequest {
    private final UUID requestId;
    @HeaderField(renderer = MedicationRenderer.class)
    private Medication medication;
    private final int amountToReplenish;
    private ReplenishmentRequestStatus replenishmentRequestStatus;

    public ReplenishmentRequest(UUID requestId, Medication medication, int amountToReplenish, ReplenishmentRequestStatus replenishmentRequestStatus) {
        this.requestId = requestId;
        this.medication = medication;
        this.amountToReplenish = amountToReplenish;
        this.replenishmentRequestStatus = replenishmentRequestStatus;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public int getAmountToReplenish() {
        return amountToReplenish;
    }

    public ReplenishmentRequestStatus getReplenishmentRequestStatus() {
        return replenishmentRequestStatus;
    }

    public void setReplenishmentRequestStatus(ReplenishmentRequestStatus replenishmentRequestStatus) {
        this.replenishmentRequestStatus = replenishmentRequestStatus;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }
}
