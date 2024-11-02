package com.group6.hms.app.storage;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;

/**
 * An interface for a storage provider that manages a collection of items.
 *
 * @param <T> the type of items stored in this provider
 */
public interface StorageProvider<T> extends Serializable {

    /**
     * Adds a new item to the storage.
     *
     * @param item the item to be added
     */
    void addNewItem(T item);

    /**
     * Removes an item from the storage.
     *
     * @param item the item to be removed
     */
    void removeItem(T item);

    /**
     * Saves the current items to the specified file.
     *
     * @param file the file to which the items will be saved
     */
    void saveToFile(File file);

    /**
     * Loads the items from the specified file.
     *
     * @param file the file from which the items will be loaded
     */
    void loadFromFile(File file);

    /**
     * Returns a collection of the items stored in this provider.
     *
     * @return a collection of stored items
     */
    Collection<T> getItems();
}
