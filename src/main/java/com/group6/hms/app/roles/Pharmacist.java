package com.group6.hms.app.roles;

/**
 * The {@code Pharmacist} class represents a pharmacist role in the hospital management system.
 * It extends the {@link Staff} class and defines specific behaviors and attributes
 * associated with pharmacists.
 */
public class Pharmacist extends Staff {

    /**
     * Constructs a {@code Pharmacist} with the specified user details.
     *
     * @param userId   the unique user ID of the pharmacist
     * @param password the password of the pharmacist, represented as a character array
     * @param name     the name of the pharmacist
     * @param gender   the gender of the pharmacist, represented as a {@link Gender} enum
     * @param age      the age of the pharmacist
     */
    public Pharmacist(String userId, char[] password, String name, Gender gender, int age) {
        super(userId, password, name, gender, age);
    }

    /**
     * Returns the role name of this pharmacist.
     *
     * @return the role name as a {@code String}, specifically "Pharmacist"
     */
    @Override
    public String getRoleName() {
        return "Pharmacist";
    }
}