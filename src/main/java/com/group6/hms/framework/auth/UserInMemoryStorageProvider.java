package com.group6.hms.framework.auth;

import java.io.File;
import java.util.*;

public class UserInMemoryStorageProvider implements StorageProvider<User> {

    private final Map<UUID, User> users = new HashMap<UUID, User>();

    @Override
    public void addNewItem(User item) {
        users.put(item.getUserId(), item);
    }

    @Override
    public void removeItem(User item) {
        users.remove(item.getUserId(), item);
    }

    @Override
    public void saveToFile() {
        throw new UnsupportedOperationException("Cannot save using in memory storage.");
    }

    @Override
    public void loadFromFile(File file) {
        throw new UnsupportedOperationException("Cannot load using in memory storage.");
    }

    @Override
    public Collection<User> getItems() {
        return users.values();
    }
}
