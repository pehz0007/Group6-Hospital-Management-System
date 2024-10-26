package com.group6.hms.app.models;

import java.io.Serializable;
import java.util.UUID;

public class Medication implements Serializable {
    private UUID medicationId;
    private String name;
    private int currentStock;
    private int lowStockLevelLimit;

    public Medication(UUID id, String name, int currentStock, int lowStockLevelLimit) {
        this.medicationId = id;
        this.name = name;
        this.currentStock = currentStock;
        this.lowStockLevelLimit = lowStockLevelLimit;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(UUID medicationId) {
        this.medicationId = medicationId;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    public void addStock(int amountToAdd) {
        currentStock += amountToAdd;
    }

    public void minusStock(int amountToMinus) {
        currentStock -= amountToMinus;
    }
    public int getLowStockLevelLimit() {
        return lowStockLevelLimit;
    }

    public void setLowStockLevelLimit(int lowStockLevelLimit) {
        this.lowStockLevelLimit = lowStockLevelLimit;
    }
}


