package com.group6.hms.app.auth;

public interface MultiStorageProvider<K, T> {

    StorageProvider<T> getStorageProvider(K key);

}
