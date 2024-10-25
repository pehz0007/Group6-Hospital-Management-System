package com.group6.hms.app.storage;

import java.io.*;
import java.util.*;

public class SerializationStorageProvider<T> implements StorageProvider<T> {

    private List<T> items = new LinkedList<>();

    @Override
    public void addNewItem(T item) {
        items.add(item);
    }

    @Override
    public void removeItem(T item) {
        items.remove(item);
    }

    @Override
    public void saveToFile(File file) {
        // serialize list
        try (FileOutputStream fileOut = new FileOutputStream(file);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(items);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("File Save Error");
        }
    }


    @Override
    public void loadFromFile(File file) {
        // Deserialize list
        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            // deserialize object
            items = (LinkedList<T>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("File Load Error");
            System.err.println("Try resetting the database");
        }
    }

    @Override
    public Collection<T> getItems() {
        return items;
    }
}
