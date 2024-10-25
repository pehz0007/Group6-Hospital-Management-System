package com.group6.hms.app.roles;

import com.group6.hms.app.auth.User;

public class Pharmacist extends Staff {

    public Pharmacist(String username, char[] password, String name, Gender gender, String staffId, int age) {
        super(username, password, name, gender, staffId, age);
    }

    @Override
    public String getRoleName() {
        return "Pharmacist";
    }
}
