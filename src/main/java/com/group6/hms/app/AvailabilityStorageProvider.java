package com.group6.hms.app;

import com.group6.hms.app.auth.StorageProvider;
import com.group6.hms.app.auth.User;
import com.group6.hms.app.models.Availability;
import com.group6.hms.app.roles.Doctor;

import java.io.*;
import java.time.LocalDate;
import java.util.*;


// TODO: FIX THE INTERFACE StorageProvider<T>, THEN IMPLEMENT IT
public class AvailabilityStorageProvider {
    private Map<String, ArrayList<Availability>> availabilities = new HashMap<>();

    public AvailabilityStorageProvider(File file) {
        loadFromFile(file);
    }
//    @Override
    public void addNewItem(Availability item) {
        String key = item.getAvailabilityString();
        availabilities.putIfAbsent(key, new ArrayList<>());

        if (availabilities.get(key).contains(item)) {
            System.err.println("Availability already exists");
            return;
        }
        availabilities.get(key).add(item);
    }

//    @Override
    public void removeItem(Availability item) {
        String key = item.getAvailabilityString();
        if (!availabilities.containsKey(key) || !availabilities.get(key).contains(item)) {
            System.err.println("Availability does not exist.");
            return;
        }
        availabilities.get(key).remove(item);

    }

//    @Override
    public void saveToFile(File file) {
        // serialize HashMap
        try (FileOutputStream fileOut = new FileOutputStream(file);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(availabilities); // Serialize the HashMap
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("File Save Error");
        }
    }

//    @Override
    public void loadFromFile(File file) {
        // Deserialize the HashMap
        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            // deserialize object
            availabilities = (Map<String, ArrayList<Availability>>) in.readObject();
            //System.out.println("HashMap has been deserialized.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("File Load Error");
        }
    }


}
