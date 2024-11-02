package com.group6.hms.app.storage;

/**
 * An interface for managing multiple storage providers.
 * It allows for retrieving and saving storage providers based on a key.
 *
 * @param <K> the type of the key used to identify the storage provider
 * @param <T> the type of the data managed by the storage provider
 */
public interface MultiStorageProvider<K, T> {

    /**
     * Retrieves a storage provider associated with the specified key.
     *
     * @param key the key used to identify the storage provider
     * @return the storage provider associated with the given key
     */
    StorageProvider<T> getStorageProvider(K key);

    /**
     * Saves the current state of the storage provider associated with the specified key.
     *
     * @param key the key used to identify the storage provider to be saved
     */
    void saveStorageProvider(K key);

}
