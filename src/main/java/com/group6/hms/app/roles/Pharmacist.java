package com.group6.hms.app.roles;

import com.group6.hms.app.auth.User;

public class Pharmacist extends User {

    public Pharmacist(String username, char[] password) {
        super(username, password);
    }

    @Override
    public String getRoleName() {
        return "Pharmacist";
    }
}
