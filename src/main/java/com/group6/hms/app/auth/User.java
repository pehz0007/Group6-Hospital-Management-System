package com.group6.hms.app.auth;

import com.group6.hms.app.auth.roles.Role;
import com.group6.hms.framework.screens.HeaderField;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {

    @HeaderField(width = 40)
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
