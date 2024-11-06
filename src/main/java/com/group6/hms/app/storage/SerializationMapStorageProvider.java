package com.group6.hms.app.storage;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * A storage provider that manages multiple storage providers using serialization.
 * It allows for storing and retrieving data associated with a key, with
 * persistence to the filesystem.
 *
 * @param <K> the type of the key used to identify storage providers
 * @param <T> the type of data managed by the storage providers
 */
public class SerializationMapStorageProvider<K, T> implements MultiStorageProvider<K, T> {

    private Map<K, StorageProvider<T>> map = new HashMap<>();
    private Path path;

    /**
     * Constructs a SerializationMapStorageProvider with the specified path for
     * storing serialized files.
     *
     * @param path the path where serialized files will be stored
     */
    public SerializationMapStorageProvider(Path path) {
        this.path = path;
    }

    @Override
    public StorageProvider<T> getStorageProvider(K key) {
        if(map.get(key) == null) {
            loadStorageProvider(key);
        }
        return map.get(key);
    }

    @Override
    public void saveStorageProvider(K key) {
        SerializationStorageProvider<T> storageProvider = (SerializationStorageProvider<T>) map.get(key);
        if (storageProvider != null) {
            File file = path.resolve(key.toString() + ".ser").toFile();
            path.toFile().mkdirs(); // Create directory if not exist
            storageProvider.saveToFile(file);
        }
    }

    /**
     * Loads the storage provider associated with the specified key from the
     * corresponding serialized file if it exists.
     *
     * @param key the key used to identify the storage provider to be loaded
     */
    private void loadStorageProvider(K key) {
        File file = path.resolve(key.toString() + ".ser").toFile();
        SerializationStorageProvider<T> storageProvider = new SerializationStorageProvider<>();
        map.put(key, storageProvider);

        if (file.exists()) {
            storageProvider.loadFromFile(file);
        }
    }

}