package com.group6.hms.app.models;

import java.io.Serializable;

/**
 * The {@code Medication} class represents a medication with a specified name.
 * It implements {@code Serializable} to allow for storage and retrieval.
 */
public class Medication implements Serializable {
    private String name;

    /**
     * Constructs a new {@code Medication} with the specified name.
     *
     * @param name the name of the medication
     */
    public Medication(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the medication.
     *
     * @return the name of the medication
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the medication.
     *
     * @param name the new name of the medication
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the string representation of the medication, which is its name.
     *
     * @return the name of the medication as a string
     */
    @Override
    public String toString() {
        return name;
    }



}


