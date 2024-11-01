package com.group6.hms.app.models;

import com.group6.hms.app.screens.admin.LowStockIndicatorRenderer;
import com.group6.hms.app.screens.admin.MedicationRenderer;
import com.group6.hms.framework.screens.pagination.HeaderField;

import java.io.Serializable;

public class MedicationStock implements Serializable {

    @HeaderField(renderer = MedicationRenderer.class)
    private Medication medication;
    @HeaderField(renderer = LowStockIndicatorRenderer.class)
    private int currentStock;
    @HeaderField(width = 25)
    private int lowStockLevelLimit;

    public MedicationStock(Medication medication, int currentStock, int lowStockLevelLimit) {
        this.medication = medication;
        this.currentStock = currentStock;
        this.lowStockLevelLimit = lowStockLevelLimit;
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

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }
}
