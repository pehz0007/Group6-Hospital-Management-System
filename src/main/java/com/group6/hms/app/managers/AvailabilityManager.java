package com.group6.hms.app.managers;

import com.group6.hms.app.models.Availability;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.storage.SerializationStorageProvider;
import com.group6.hms.app.storage.StorageProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AvailabilityManager  {
    private static final File availabilitiesFile = new File("data/availabilities.ser");
    private final StorageProvider<Availability> availabilityStorageProvider = new SerializationStorageProvider<>();

    public AvailabilityManager() {
        if (!availabilitiesFile.exists()) {
            availabilityStorageProvider.saveToFile(availabilitiesFile);
        }
        availabilityStorageProvider.loadFromFile(availabilitiesFile);
    }

    // for Patient to get every doctor's availability
    public List<Availability> getAllAvailability() {
        return (List<Availability>) availabilityStorageProvider.getItems();
    }

    // for Doctor to get their own availability
    public List<Availability> getAvailabilityByDoctor(Doctor doctor) {
        return availabilityStorageProvider.getItems().stream().filter(avail -> avail.getDoctor().getSystemUserId().equals(doctor.getSystemUserId())).toList();
    }

    // for Doctor to add new available slot
    public void addAvailability(Availability avail) {
        availabilityStorageProvider.addNewItem(avail);
        availabilityStorageProvider.saveToFile(availabilitiesFile);
    }

    // for Doctor to remove
    public void removeAvailability(Availability avail) {
        availabilityStorageProvider.removeItem(avail);
        availabilityStorageProvider.saveToFile(availabilitiesFile);
    }

}
