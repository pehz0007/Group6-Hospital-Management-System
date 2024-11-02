package com.group6.hms.app.models;

public class PrescribedMedication extends Medication {
    private int quantityToPrescribe;

    public PrescribedMedication(Medication medication, int quantityToPrescribe) {
        super(medication.getName());
        //this.medication = medication;
        this.quantityToPrescribe = quantityToPrescribe;
    }
    public int getQuantityToPrescribe() {
        return quantityToPrescribe;
    }

    @Override
    public String toString() {
        return "Medication: " + medication.getName() + ", Quantity: " + quantityToPrescribe; // medication toString()
    }
}
