package com.group6.hms.app.roles;

public class Administrator extends Staff{

    public Administrator(String userId, char[] password, String name, Gender gender, int age) {
        super(userId, password, name, gender , age);
    }

    @Override
    public String getRoleName() {
        return "Administrator";
    }
}
