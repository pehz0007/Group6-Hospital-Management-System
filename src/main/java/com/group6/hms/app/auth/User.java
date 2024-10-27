package com.group6.hms.app.auth;

import com.group6.hms.app.roles.Gender;

import java.io.Serializable;
import java.util.UUID;

public abstract class User implements Serializable {

    private final UUID systemUserId;
    private final String userId;
    private final String name;
    private final Gender gender;
    private byte[] passwordHashed;

    /**
     * Create a new user object with the user id and password.
     *
     * This constructor is only called when a new user is created.
     * The password will be hash after creating the user object.
     *
     * @param userId - the user id of the user
     * @param name - name of the user
     * @param password - the password of the user
     * @param gender - gender of the user
     */
    protected User(String userId, char[] password, String name, Gender gender) {
        this.systemUserId = UUID.randomUUID();
        this.userId = userId;
        this.name = name;
        this.gender = gender;
        //Skip verify password checking
//        if(!PasswordUtils.verifyPassword(password)) {throw new UserCreationException("Password requirement is not met!");}
        this.passwordHashed = PasswordUtils.hashPassword(password);
    }

    public UUID getSystemUserId() {
        return systemUserId;
    }

    public String getUserId() {
        return userId;
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
