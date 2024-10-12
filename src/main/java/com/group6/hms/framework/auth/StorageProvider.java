package com.group6.hms.framework.auth;

import java.io.File;
import java.util.Collection;

public interface StorageProvider<T> {

    void addNewItem(T item);
    void removeItem(T item);

    void saveToFile();
    void loadFromFile(File file);

    Collection<T> getItems();

}
