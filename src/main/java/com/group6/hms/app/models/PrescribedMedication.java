package com.group6.hms.app.models;

/**
 * The {@code PrescribedMedication} class represents a medication that has been prescribed
 * with a specified quantity. It extends the {@code Medication} class, adding a field
 * for the quantity to be prescribed.
 */
public class PrescribedMedication extends Medication {
    private final int quantityToPrescribe;

    /**
     * Constructs a new {@code PrescribedMedication} with the specified name and quantity.
     *
     * @param name               the name of the prescribed medication
     * @param quantityToPrescribe the quantity of the medication to prescribe
     */
    public PrescribedMedication(String name, int quantityToPrescribe) {
        super(name);
        this.quantityToPrescribe = quantityToPrescribe;
    }

    /**
     * Returns the quantity of the medication to be prescribed.
     *
     * @return the quantity to prescribe
     */
    public int getQuantityToPrescribe() {
        return quantityToPrescribe;
    }

    /**
     * Returns a string representation of the prescribed medication, including
     * the name and quantity to be prescribed.
     *
     * @return a string in the format "Medication: [name], Quantity: [quantity]"
     */
    @Override
    public String toString() {
        return "Medication: " + getName() + ", Quantity: " + quantityToPrescribe; // medication toString()
    }
}
