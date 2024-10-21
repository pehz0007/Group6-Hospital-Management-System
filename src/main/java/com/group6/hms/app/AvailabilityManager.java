package com.group6.hms.app;

import com.group6.hms.app.auth.MultiStorageProvider;
import com.group6.hms.app.auth.SerializationMapStorageProvider;
import com.group6.hms.app.auth.StorageProvider;
import com.group6.hms.app.models.Appointment;
import com.group6.hms.app.models.Availability;
import com.group6.hms.app.roles.Doctor;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AvailabilityManager {
// TODO: FIGURE OUT THE STORAGE
    
//    private final MultiStorageProvider<LocalDate, Appointment> availabilityStorageProvider = new SerializationMapStorageProvider<LocalDate, Appointment>();
    private List<Availability> availabilities = new ArrayList<Availability>();

    public List<Availability> getAllAvailability() {
        return availabilities;
    }

    public void addAvailability(Availability avail) {
        availabilities.add(avail);
    }

    public void removeAvailability(Availability avail) {
        availabilities.remove(avail);
    }

}
