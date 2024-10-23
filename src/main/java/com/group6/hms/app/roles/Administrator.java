package com.group6.hms.app.roles;

import com.group6.hms.app.auth.User;

public class Administrator extends User {

    public Administrator(String username, char[] password) {
        super(username, password);
    }

    @Override
    public String getRoleName() {
        return "Administrator";
    }
}
