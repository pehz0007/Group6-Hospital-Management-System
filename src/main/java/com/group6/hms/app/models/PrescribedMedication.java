package com.group6.hms.app.models;

import java.io.Serializable;

public class PrescribedMedication implements Serializable {
    private Medication medication;
    private final int quantityToPrescribe;

    public PrescribedMedication(Medication medication, int quantityToPrescribe) {
        this.medication = medication;
        this.quantityToPrescribe = quantityToPrescribe;
    }
    public int getQuantityToPrescribe() {
        return quantityToPrescribe;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    @Override
    public String toString() {
        return "Medication: " + medication + ", Quantity: " + quantityToPrescribe; // medication toString()
    }
}
