package com.group6.hms.app.models;

public class PrescribedMedication {
    Medication medication;
    private int quantityToPrescribe;

    public PrescribedMedication(Medication medication, int quantityToPrescribe) {
        this.medication = medication;
        this.quantityToPrescribe = quantityToPrescribe;
    }
    public int getQuantityToPrescribe() {
        return quantityToPrescribe;
    }

}
