package com.group6.hms.app.managers;

import com.group6.hms.app.models.Availability;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.storage.SerializationStorageProvider;
import com.group6.hms.app.storage.StorageProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code AvailabilityManager} class manages doctor availability slots in the system.
 * It provides methods for retrieving, adding, and removing availability, allowing both patients
 * and doctors to access and modify availability data.
 */
public class AvailabilityManager  {
    private static final File availabilitiesFile = new File("data/availabilities.ser");
    private final StorageProvider<Availability> availabilityStorageProvider = new SerializationStorageProvider<>();

    /**
     * Initializes the {@code AvailabilityManager} and loads existing availabilities from storage.
     * If the availability storage file does not exist, it creates a new file.
     */
    public AvailabilityManager() {
        if (!availabilitiesFile.exists()) {
            availabilityStorageProvider.saveToFile(availabilitiesFile);
        }
        availabilityStorageProvider.loadFromFile(availabilitiesFile);
    }

    /**
     * Retrieves all availability slots for all doctors.
     * This method is intended for patients to view available appointment slots.
     *
     * @return a {@code List} of all {@code Availability} objects
     */
    public List<Availability> getAllAvailability() {
        return (List<Availability>) availabilityStorageProvider.getItems();
    }

    /**
     * Retrieves availability slots for a specific doctor.
     * This method allows doctors to view and manage their own availability.
     *
     * @param doctor the {@code Doctor} whose availability slots are to be retrieved
     * @return a {@code List} of {@code Availability} objects for the specified doctor
     */
    public List<Availability> getAvailabilityByDoctor(Doctor doctor) {
        return availabilityStorageProvider.getItems().stream().filter(avail -> avail.getDoctor().getSystemUserId().equals(doctor.getSystemUserId())).toList();
    }

    /**
     * Adds a new availability slot for a doctor and updates the availability list in the availabilityStorageProvider. It also updates the availabilities serializable file.
     * This method is intended for doctors to add new available appointment times.
     *
     * @param avail the {@code Availability} object representing the new availability slot
     */
    public void addAvailability(Availability avail) {
        availabilityStorageProvider.addNewItem(avail);
        availabilityStorageProvider.saveToFile(availabilitiesFile);
    }

    /**
     * Removes an existing availability slot for a doctor and updates the availability list in the availabilityStorageProvider. It also updates the availabilities serializable file.
     * This method allows doctors to remove availability times they no longer wish to offer.
     *
     * @param avail the {@code Availability} object representing the slot to be removed
     */
    public void removeAvailability(Availability avail) {
        availabilityStorageProvider.removeItem(avail);
        availabilityStorageProvider.saveToFile(availabilitiesFile);
    }

}
