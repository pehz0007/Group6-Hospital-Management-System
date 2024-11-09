package com.group6.hms.app.roles;

/**
 * The {@code Administrator} class represents an administrator role in the hospital
 * management system. It extends the {@link Staff} class and defines specific
 * behavior for administrator roles.
 */
public class Administrator extends Staff {

    /**
     * Constructs an {@code Administrator} with the specified user details.
     *
     * @param userId   the unique user ID of the administrator
     * @param password the password of the administrator, represented as a character array
     * @param name     the name of the administrator
     * @param gender   the gender of the administrator, represented as a {@link Gender} enum
     * @param age      the age of the administrator
     */
    public Administrator(String userId, char[] password, String name, Gender gender, int age) {
        super(userId, password, name, gender, age);
    }

    /**
     * Returns the role name of this administrator.
     *
     * @return the role name as a {@code String}, specifically "Administrator"
     */
    @Override
    public String getRoleName() {
        return "Administrator";
    }
}