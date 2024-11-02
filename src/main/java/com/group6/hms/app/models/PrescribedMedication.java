package com.group6.hms.app.models;

public class PrescribedMedication extends Medication {
    private final int quantityToPrescribe;

    public PrescribedMedication(String name, int quantityToPrescribe) {
        super(name);
        //this.medication = medication;
        this.quantityToPrescribe = quantityToPrescribe;
    }
    public int getQuantityToPrescribe() {
        return quantityToPrescribe;
    }

    @Override
    public String toString() {
        return "Medication: " + getName() + ", Quantity: " + quantityToPrescribe; // medication toString()
    }
}
