package com.group6.hms.app.roles;

import com.group6.hms.app.managers.auth.models.User;

public abstract class Staff extends User {

    private final int age;

    protected Staff(String userId, char[] password, String name, Gender gender, int age) {
        super(userId, password, name, gender);
        this.age = age;
    }

}
