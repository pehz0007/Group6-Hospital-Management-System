package com.group6.hms.app;

import com.group6.hms.app.auth.StorageProvider;
import com.group6.hms.app.models.Availability;

import java.io.File;

public class AvailabilityManager {
    private static final File availabilitiesFile = new File("data/availabilities.ser");

    // TODO: CHANGE TYPE TO StorageProvider<Availability> WHEN INTERFACE IS FIXED
    private final AvailabilityStorageProvider availabilityStorageProvider = new AvailabilityStorageProvider(availabilitiesFile);

    public AvailabilityManager() {

    }

}
