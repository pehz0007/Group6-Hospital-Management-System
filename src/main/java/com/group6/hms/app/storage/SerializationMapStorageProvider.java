package com.group6.hms.app.storage;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SerializationMapStorageProvider<K, T> implements MultiStorageProvider<K, T> {

    private Map<K, StorageProvider<T>> map = new HashMap<>();

    @Override
    public StorageProvider<T> getStorageProvider(K key) {
        if(map.get(key) == null) {
            loadStorageProvider(key);
        }
        return map.get(key);
    }


    private void loadStorageProvider(K key) {
        File file = new File(key.toString() + ".ser");
        SerializationStorageProvider<T> storageProvider = new SerializationStorageProvider<>();
        map.put(key, storageProvider);
        if(file.exists()){
            storageProvider.loadFromFile(file);
        }
    }

}
