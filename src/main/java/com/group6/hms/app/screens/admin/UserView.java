package com.group6.hms.app.screens.admin;

import com.group6.hms.app.auth.User;
import com.group6.hms.app.roles.Gender;
import com.group6.hms.framework.screens.pagination.HeaderField;

import java.util.UUID;

public class UserView {

    @HeaderField(width = 40)
    private final UUID userId;
    private final String username;
    private final String name;
    private final Gender gender;
    private byte[] passwordHashed;
    private String role;

    @HeaderField(show = false)
    private User user;

    public UserView(User user){
        this.user = user;
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.gender = user.getGender();
        this.passwordHashed = user.getPasswordHashed();
        role = user.getRoleName();
    }

    public User getUser() {
        return user;
    }
}
