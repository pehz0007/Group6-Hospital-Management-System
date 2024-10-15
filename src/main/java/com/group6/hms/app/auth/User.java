package com.group6.hms.app.auth;

import java.io.Serializable;
import java.util.UUID;

public abstract class User implements Serializable {

    private final UUID userId;
    private final String username;
    private byte[] passwordHashed;

    /**
     * Create a new user object with the username and password.
     *
     * This constructor is only called when a new user is created.
     * The password will be hash after creating the user object.
     *
     * @param username - the username of the user
     * @param password - the password of the user
     */
    protected User(String username, char[] password) {
        this.userId = UUID.randomUUID();
        this.username = username;
        //Skip verify password checking
//        if(!PasswordUtils.verifyPassword(password)) {throw new UserCreationException("Password requirement is not met!");}
        this.passwordHashed = PasswordUtils.hashPassword(password);
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public byte[] getPasswordHashed() {
        return passwordHashed;
    }

    protected void changePassword(char[] newPassword) {
        PasswordUtils.verifyPassword(newPassword);
        this.passwordHashed = PasswordUtils.hashPassword(newPassword);
    }

    public abstract String getRoleName();

}
