package com.group6.hms.app.managers.inventory.models;

import com.group6.hms.app.screens.admin.LowStockIndicatorRenderer;
import com.group6.hms.app.screens.admin.MedicationRenderer;
import com.group6.hms.framework.screens.pagination.HeaderField;

import java.io.Serializable;

/**
 * The {@code MedicationStock} class represents the stock details of a medication,
 * including the current stock level and the minimum stock level before restocking is needed.
 * It implements {@code Serializable} to support storage and retrieval.
 */
public class MedicationStock implements Serializable {

    @HeaderField(renderer = MedicationRenderer.class)
    private Medication medication;
    @HeaderField(renderer = LowStockIndicatorRenderer.class)
    private int currentStock;
    @HeaderField(width = 25)
    private int lowStockLevelLimit;

    /**
     * Constructs a new {@code MedicationStock} with the specified medication, current stock level,
     * and low stock level limit.
     *
     * @param medication         the {@code Medication} associated with this stock
     * @param currentStock       the current stock level of the medication
     * @param lowStockLevelLimit the stock level at which a restock is recommended
     */
    public MedicationStock(Medication medication, int currentStock, int lowStockLevelLimit) {
        this.medication = medication;
        this.currentStock = currentStock;
        this.lowStockLevelLimit = lowStockLevelLimit;
    }

    /**
     * Returns the current stock level of the medication.
     *
     * @return the current stock level
     */
    public int getCurrentStock() {
        return currentStock;
    }

    /**
     * Sets the current stock level of the medication.
     *
     * @param currentStock the new stock level
     */
    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    /**
     * Increases the stock level by a specified amount.
     *
     * @param amountToAdd the amount to add to the current stock
     */
    public void addStock(int amountToAdd) {
        currentStock += amountToAdd;
    }

    /**
     * Decreases the stock level by a specified amount.
     *
     * @param amountToMinus the amount to subtract from the current stock
     */
    public void minusStock(int amountToMinus) {
        currentStock -= amountToMinus;
    }

    /**
     * Returns the minimum stock level limit at which restocking is needed.
     *
     * @return the low stock level limit
     */
    public int getLowStockLevelLimit() {
        return lowStockLevelLimit;
    }

    /**
     * Sets the minimum stock level limit for restocking.
     *
     * @param lowStockLevelLimit the new low stock level limit
     */
    public void setLowStockLevelLimit(int lowStockLevelLimit) {
        this.lowStockLevelLimit = lowStockLevelLimit;
    }

    /**
     * Returns the medication associated with this stock entry.
     *
     * @return the {@code Medication} object
     */
    public Medication getMedication() {
        return medication;
    }

    /**
     * Sets the medication for this stock entry.
     *
     * @param medication the {@code Medication} to associate with this stock entry
     */
    public void setMedication(Medication medication) {
        this.medication = medication;
    }
}
