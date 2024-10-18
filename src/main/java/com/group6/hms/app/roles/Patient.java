package com.group6.hms.app.roles;

import com.group6.hms.app.auth.User;

public class Patient extends User {
    public Patient(String username, char[] password) {
        super(username, password);
    }

    @Override
    public String getRoleName() {
        return "Patient";
    }
}
