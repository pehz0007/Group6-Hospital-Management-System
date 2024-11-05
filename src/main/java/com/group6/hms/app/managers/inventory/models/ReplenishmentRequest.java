package com.group6.hms.app.managers.inventory.models;

import com.group6.hms.app.screens.admin.MedicationRenderer;
import com.group6.hms.framework.screens.pagination.HeaderField;

import java.io.Serializable;
import java.util.UUID;

/**
 * The {@code ReplenishmentRequest} class represents a request to replenish a specific amount
 * of medication stock. It includes details such as the request ID, medication, quantity to
 * replenish, and the current status of the request. This class implements {@code Serializable}
 * to allow for storage and retrieval.
 */
public class ReplenishmentRequest implements Serializable {
    private final UUID requestId;
    @HeaderField(renderer = MedicationRenderer.class)
    private Medication medication;
    private final int amountToReplenish;
    private ReplenishmentRequestStatus replenishmentRequestStatus;

    /**
     * Constructs a new {@code ReplenishmentRequest} with the specified details.
     *
     * @param requestId                 the unique identifier for this request
     * @param medication                the {@code Medication} to replenish
     * @param amountToReplenish         the quantity of medication to replenish
     * @param replenishmentRequestStatus the current status of the replenishment request
     */
    public ReplenishmentRequest(UUID requestId, Medication medication, int amountToReplenish, ReplenishmentRequestStatus replenishmentRequestStatus) {
        this.requestId = requestId;
        this.medication = medication;
        this.amountToReplenish = amountToReplenish;
        this.replenishmentRequestStatus = replenishmentRequestStatus;
    }

    /**
     * Returns the unique identifier of this replenishment request.
     *
     * @return the {@code UUID} of the request
     */
    public UUID getRequestId() {
        return requestId;
    }

    /**
     * Returns the quantity of medication to be replenished.
     *
     * @return the quantity to replenish
     */
    public int getAmountToReplenish() {
        return amountToReplenish;
    }

    /**
     * Returns the current status of the replenishment request.
     *
     * @return the {@code ReplenishmentRequestStatus} of the request
     */
    public ReplenishmentRequestStatus getReplenishmentRequestStatus() {
        return replenishmentRequestStatus;
    }

    /**
     * Sets the status of the replenishment request.
     *
     * @param replenishmentRequestStatus the new status to set
     */
    public void setReplenishmentRequestStatus(ReplenishmentRequestStatus replenishmentRequestStatus) {
        this.replenishmentRequestStatus = replenishmentRequestStatus;
    }

    /**
     * Returns the medication associated with this replenishment request.
     *
     * @return the {@code Medication} to be replenished
     */
    public Medication getMedication() {
        return medication;
    }

    /**
     * Sets the medication associated with this replenishment request.
     *
     * @param medication the {@code Medication} to set
     */
    public void setMedication(Medication medication) {
        this.medication = medication;
    }
}
