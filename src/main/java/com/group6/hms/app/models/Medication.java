package com.group6.hms.app.models;

import java.io.Serializable;
import java.util.UUID;

public class Medication implements Serializable {
    private UUID medicationId;
    private String name;

    public Medication(UUID id, String name) {
        this.medicationId = id;
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(UUID medicationId) {
        this.medicationId = medicationId;
    }
}


