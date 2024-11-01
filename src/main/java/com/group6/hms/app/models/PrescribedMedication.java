package com.group6.hms.app.models;

public class PrescribedMedication extends Medication {
    Medication medication;
    private int quantityToPrescribe;

    public PrescribedMedication(Medication medication, int quantityToPrescribe) {
        super(medication.getName());
        //this.medication = medication;
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
}
