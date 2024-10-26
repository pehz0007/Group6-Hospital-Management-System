package com.group6.hms.app.models;

import java.util.UUID;

public class ReplenishmentRequest {
    private UUID requestId;
    private UUID medicationId;
    private UUID medicationName;
    private int amountToReplenish;
    private ReplenishmentRequestStatus replenishmentRequestStatus;

    public ReplenishmentRequest(UUID requestId, UUID medicationId, UUID medicationName, int amountToReplenish, ReplenishmentRequestStatus replenishmentRequestStatus) {
        this.requestId = requestId;
        this.medicationId = medicationId;
        this.medicationName = medicationName;
        this.amountToReplenish = amountToReplenish;
        this.replenishmentRequestStatus = replenishmentRequestStatus;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public UUID getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(UUID medicationId) {
        this.medicationId = medicationId;
    }

    public UUID getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(UUID medicationName) {
        this.medicationName = medicationName;
    }

    public int getAmountToReplenish() {
        return amountToReplenish;
    }

    public void setAmountToReplenish(int amountToReplenish) {
        this.amountToReplenish = amountToReplenish;
    }

    public ReplenishmentRequestStatus getReplenishmentRequestStatus() {
        return replenishmentRequestStatus;
    }

    public void setReplenishmentRequestStatus(ReplenishmentRequestStatus replenishmentRequestStatus) {
        this.replenishmentRequestStatus = replenishmentRequestStatus;
    }
}
