package com.group6.hms.app.models;

public enum BloodType {
    A_PLUS("A+"),
    A_MINUS("A-"),
    B_PLUS("B+"),
    B_MINUS("B-"),
    O_PLUS("O+"),
    O_MINUS("O-"),
    AB_PLUS("AB+"),
    AB_MINUS("AB-");

    private String bloodType;

    BloodType(String bloodType){
        this.bloodType = bloodType;
    }

    public static BloodType fromString(String value){
        for(BloodType bloodType : BloodType.values()){
            if(bloodType.toString().equals(value)){
                return bloodType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return bloodType;
    }
}
