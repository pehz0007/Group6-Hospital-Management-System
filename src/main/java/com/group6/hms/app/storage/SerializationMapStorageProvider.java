package com.group6.hms.app.storage;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class SerializationMapStorageProvider<K, T> implements MultiStorageProvider<K, T> {

    private Map<K, StorageProvider<T>> map = new HashMap<>();
    private Path path;

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

    private void loadStorageProvider(K key) {
        File file = path.resolve(key.toString() + ".ser").toFile();
        SerializationStorageProvider<T> storageProvider = new SerializationStorageProvider<>();
        map.put(key, storageProvider);

        if (file.exists()) {
            storageProvider.loadFromFile(file);
        }
    }

}
