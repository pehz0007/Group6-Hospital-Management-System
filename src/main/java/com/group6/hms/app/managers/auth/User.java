package com.group6.hms.app.managers.auth;

import com.group6.hms.app.roles.Gender;

import java.io.Serializable;
import java.util.UUID;

/**
 * {@code User} is an abstract class for users in the system.
 * It contains basic information about the user, including their unique ID, username, name,
 * gender, and hashed password. Subclasses of {@code User} represent specific user roles
 * within the system.
 */
public abstract class User implements Serializable {

    private final UUID systemUserId;
    private final String userId;
    private final String name;
    private final Gender gender;
    private byte[] passwordHashed;

    private boolean firstTimeLogin = true;

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

    /**
     * Returns the unique system ID for this user.
     *
     * @return the system user ID as a {@code UUID}
     */
    public UUID getSystemUserId() {
        return systemUserId;
    }

    /**
     * Returns the user ID of this user.
     *
     * @return the user ID as a {@code String}
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Returns the name of this user.
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the gender of this user.
     *
     * @return the {@code Gender} of the user
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Returns the hashed password of this user.
     *
     * @return a byte array containing the hashed password
     */
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

    /**
     * Returns the role name of the user.
     * This method must be implemented by subclasses to define the specific role of the user.
     *
     * @return the role name as a {@code String}
     */
    public abstract String getRoleName();

    /**
     * Sets the first-time login status for the user.
     *
     * @param firstTimeLogin a boolean indicating if this is the user's first time logging in.
     */
    public void setFirstTimeLogin(boolean firstTimeLogin) {
        this.firstTimeLogin = firstTimeLogin;
    }

    /**
     * Checks if this is the user's first time logging in.
     *
     * @return {@code true} if this is the first time the user is logging in, {@code false} otherwise.
     */
    public boolean isFirstTimeLogin() {
        return firstTimeLogin;
    }
}
