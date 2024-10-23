package com.group6.hms.app.screens.admin;

import com.group6.hms.app.auth.User;
import com.group6.hms.framework.screens.HeaderField;

import java.util.UUID;

public class UserView {

    @HeaderField(width = 40)
    private final UUID userId;
    private final String username;
    private byte[] passwordHashed;
    private String role;

    public UserView(User user){
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.passwordHashed = user.getPasswordHashed();
        role = user.getRoleName();
    }

}
