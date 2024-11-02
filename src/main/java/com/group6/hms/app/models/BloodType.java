package com.group6.hms.app.models;

/**
 * The {@code BloodType} enum represents the different blood types, including
 * positive and negative variations of types A, B, O, and AB.
 */
public enum BloodType {
    A_PLUS("A+"),
    A_MINUS("A-"),
    B_PLUS("B+"),
    B_MINUS("B-"),
    O_PLUS("O+"),
    O_MINUS("O-"),
    AB_PLUS("AB+"),
    AB_MINUS("AB-");

    private String bloodType;

    /**
     * Constructs a {@code BloodType} with the specified string representation.
     *
     * @param bloodType the string representation of the blood type
     */
    BloodType(String bloodType){
        this.bloodType = bloodType;
    }

    /**
     * Returns a {@code BloodType} enum constant that matches the specified string value.
     * This method is case-sensitive and returns {@code null} if no match is found.
     *
     * @param value the string representation of the blood type
     * @return the matching {@code BloodType} constant, or {@code null} if no match is found
     */
    public static BloodType fromString(String value){
        for(BloodType bloodType : BloodType.values()){
            if(bloodType.toString().equals(value)){
                return bloodType;
            }
        }
        return null;
    }


    /**
     * Returns the string representation of the blood type.
     *
     * @return the string representation of this {@code BloodType}
     */
    @Override
    public String toString() {
        return bloodType;
    }
}
