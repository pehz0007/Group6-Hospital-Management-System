package com.group6.hms.app.storage;

public interface MultiStorageProvider<K, T> {

    StorageProvider<T> getStorageProvider(K key);
    void saveStorageProvider(K key);

}
