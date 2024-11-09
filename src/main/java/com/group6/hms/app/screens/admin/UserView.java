package com.group6.hms.app.screens.admin;

import com.group6.hms.app.managers.auth.User;
import com.group6.hms.app.roles.Gender;
import com.group6.hms.framework.screens.pagination.HeaderField;

import java.util.UUID;

/**
 * The {@code UserView} class represents a view model for a user in the Hospital
 * Management System (HMS). It encapsulates user details, including the user's
 * ID, name, gender, hashed password, and role. This class is primarily used
 * for displaying user information in the administrative interface.
 */
public class UserView {

    @HeaderField(width = 40)
    private final UUID systemUserId;
    private final String userId;
    private final String name;
    private final Gender gender;
    private byte[] passwordHashed;
    private String role;

    @HeaderField(show = false)
    private User user;

    /**
     * Constructs a {@code UserView} instance from a {@code User} object.
     *
     * @param user the user object containing user details to be displayed
     */
    public UserView(User user){
        this.user = user;
        this.systemUserId = user.getSystemUserId();
        this.userId = user.getUserId();
        this.name = user.getName();
        this.gender = user.getGender();
        this.passwordHashed = user.getPasswordHashed();
        role = user.getRoleName();
    }

    /**
     * Retrieves the underlying {@code User} object associated with this view.
     *
     * @return the user object
     */
    public User getUser() {
        return user;
    }
}
