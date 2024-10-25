package com.group6.hms.app.roles;

public class Administrator extends Staff{

    public Administrator(String username, char[] password, String name, Gender gender, String staffId, int age) {
        super(username, password, name, gender, staffId , age);
    }

    @Override
    public String getRoleName() {
        return "Administrator";
    }
}
