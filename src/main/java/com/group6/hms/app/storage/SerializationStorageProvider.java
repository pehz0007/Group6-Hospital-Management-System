package com.group6.hms.app.storage;

import java.io.*;
import java.util.*;

/**
 * A storage provider that manages a list of items and provides serialization
 * and deserialization functionality to save and load the items from a file.
 *
 * @param <T> the type of items stored in this provider
 */
public class SerializationStorageProvider<T> implements StorageProvider<T> {

    private List<T> items = new LinkedList<>();

    /**
     * Adds a new item to the storage.
     *
     * @param item the item to be added
     */
    @Override
    public void addNewItem(T item) {
        items.add(item);
    }

    /**
     * Removes an item from the storage.
     *
     * @param item the item to be removed
     */
    @Override
    public void removeItem(T item) {
        items.remove(item);
    }

    /**
     * Saves the current list of items to the specified file using serialization.
     *
     * @param file the file to which the items will be saved
     */
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

    /**
     * Loads the list of items from the specified file using deserialization.
     *
     * @param file the file from which the items will be loaded
     */
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

    /**
     * Returns a collection of the items stored in this provider.
     *
     * @return a collection of stored items
     */
    @Override
    public Collection<T> getItems() {
        return items;
    }
}
