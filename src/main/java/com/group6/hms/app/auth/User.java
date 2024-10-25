package com.group6.hms.app.auth;

import com.group6.hms.app.roles.Gender;

import java.io.Serializable;
import java.util.UUID;

public abstract class User implements Serializable {

    private final UUID userId;
    private final String username;
    private final String name;
    private final Gender gender;
    private byte[] passwordHashed;

    /**
     * Create a new user object with the username and password.
     *
     * This constructor is only called when a new user is created.
     * The password will be hash after creating the user object.
     *
     * @param username - the username of the user
     * @param name - name of the user
     * @param password - the password of the user
     * @param gender - gender of the user
     */
    protected User(String username, char[] password, String name, Gender gender) {
        this.userId = UUID.randomUUID();
        this.username = username;
        this.name = name;
        this.gender = gender;
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

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public byte[] getPasswordHashed() {
        return passwordHashed;
    }

    /**
     * This method is used by the LoginManager to modify the password in the {@code User} object.
     * Instead, use {@link LoginManager#changePassword(User, char[])} to change the user password
     */
    protected void changePassword(char[] newPassword) {
        PasswordUtils.verifyPassword(newPassword);
        this.passwordHashed = PasswordUtils.hashPassword(newPassword);
    }

    public abstract String getRoleName();

}
