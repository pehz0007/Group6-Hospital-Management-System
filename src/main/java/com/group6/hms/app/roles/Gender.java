package com.group6.hms.app.roles;

public enum Gender {

    Female, Male;

    public static Gender fromString(String value){
        for (Gender gender : Gender.values()) {
            if(gender.toString().equals(value))return gender;
        }
        return null;
    }


}
