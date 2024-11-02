package com.group6.hms.app.roles;

import com.group6.hms.app.auth.User;

/**
 * The {@code Staff} class is an abstract representation of staff members in the hospital management system.
 * It extends the {@link User} class and contains common attributes and methods shared by all staff roles.
 */
public abstract class Staff extends User {

    /** The age of the staff member. */
    private final int age;

    /**
     * Constructs a {@code Staff} with the specified user details.
     *
     * @param userId   the unique user ID of the staff member
     * @param password the password of the staff member, represented as a character array
     * @param name     the name of the staff member
     * @param gender   the gender of the staff member, represented as a {@link Gender} enum
     * @param age      the age of the staff member
     */
    protected Staff(String userId, char[] password, String name, Gender gender, int age) {
        super(userId, password, name, gender);
        this.age = age;
    }

}
