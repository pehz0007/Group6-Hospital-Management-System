package com.group6.hms.framework.auth;

import java.util.UUID;

public class User {

    private final UUID userId;
    private final String username;
    private final byte[] passwordHashed;
    private final Role role;

    protected User(UUID userId, String username, byte[] passwordHashed, Role role) {
        this.userId = userId;
        this.username = username;
        this.passwordHashed = passwordHashed;
        this.role = role;
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

    public Role getRole() {
        return role;
    }
}
