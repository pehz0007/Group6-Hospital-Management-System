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


    // for Patient to get every doctor's availability
    public List<Availability> getAllAvailability() {
        return availabilities;
    }

    // for Doctor to get their own availability
    public List<Availability> getAvailabilityByDoctor(Doctor doctor) {
        return availabilities.stream().filter(avail -> avail.getDoctor().getUserId().equals(doctor.getUserId())).toList();
    }

    // for Doctor to add new available slot
    public void addAvailability(Availability avail) {
        availabilities.add(avail);
    }

    // for Doctor to remove
    public void removeAvailability(Availability avail) {
        availabilities.remove(avail);
    }

}
