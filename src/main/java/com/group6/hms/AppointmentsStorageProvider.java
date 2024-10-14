package com.group6.hms;

import com.group6.hms.app.auth.StorageProvider;
import com.group6.hms.app.models.Appointment;

import java.io.*;
import java.util.*;

public class AppointmentsStorageProvider implements StorageProvider<Appointment> {
    private ArrayList<Appointment> appointments = new ArrayList<Appointment>();


    public AppointmentsStorageProvider(File file) {
        loadFromFile(file);
    }
    @Override
    public void addNewItem(Appointment item) {
        appointments.add(item);
    }

    @Override
    public void removeItem(Appointment item) {
        appointments.remove(item);
    }

    @Override
    public void saveToFile(File file) {
        try (FileOutputStream fileOut = new FileOutputStream(file);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(appointments); // Serialize the HashMap
        } catch (IOException e) {
            System.err.println("File Save Error");
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void loadFromFile(File file) {
        // Deserialize the HashMap
        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            // deserialize object
            appointments = (ArrayList<Appointment>) in.readObject();

            for (Appointment appointment : appointments) {
                System.out.println("Appointment ID: " + appointment.getAppointmentId());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("File Load Error");
        }
    }

    @Override
    public Collection<Appointment> getItems() {
        return appointments;
    }
}
