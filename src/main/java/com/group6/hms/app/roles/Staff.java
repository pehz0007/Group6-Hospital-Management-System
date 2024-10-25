package com.group6.hms.app.roles;

import com.group6.hms.app.auth.User;

public abstract class Staff extends User {

    private final String staffId;
    private final int age;

    protected Staff(String username, char[] password, String name, Gender gender, String staffId, int age) {
        super(username, password, name, gender);
        this.staffId = staffId;
        this.age = age;
    }

}
