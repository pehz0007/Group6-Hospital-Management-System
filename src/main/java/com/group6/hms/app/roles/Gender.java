package com.group6.hms.app.roles;

/**
 * The {@code Gender} enum represents the gender of a user within the hospital management system.
 * It defines two possible values: {@link #Female} and {@link #Male}.
 */
public enum Gender {

    /** Represents the female gender. */
    Female,

    /** Represents the male gender. */
    Male;

    /**
     * Converts a {@code String} value to its corresponding {@code Gender} enum.
     *
     * @param value the string representation of the gender (e.g., "Female", "Male")
     * @return the corresponding {@code Gender} enum value, or {@code null} if no match is found
     */
    public static Gender fromString(String value){
        for (Gender gender : Gender.values()) {
            if(gender.toString().equals(value))return gender;
        }
        return null;
    }
}