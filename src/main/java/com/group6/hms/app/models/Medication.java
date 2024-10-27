package com.group6.hms.app.models;

import java.io.Serializable;

public class Medication implements Serializable {
    private String name;

    public Medication(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}


