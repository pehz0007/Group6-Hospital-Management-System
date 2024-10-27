package com.group6.hms.app.storage;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;

public interface StorageProvider<T> extends Serializable {

    void addNewItem(T item);
    void removeItem(T item);

    void saveToFile(File file);
    void loadFromFile(File file);

    Collection<T> getItems();

}
