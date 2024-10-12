package com.group6.hms.framework.auth;

import java.io.*;
import java.util.*;

public class UserInMemoryStorageProvider implements StorageProvider<User> {

    private Map<UUID, User> users = new HashMap<UUID, User>();

    @Override
    public void addNewItem(User item) {
        users.put(item.getUserId(), item);
    }

    @Override
    public void removeItem(User item) {
        users.remove(item.getUserId(), item);
    }

    @Override
    public void saveToFile(File file) {
        // serialize HasMap
        try (FileOutputStream fileOut = new FileOutputStream(file);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(users); // Serialize the HashMap
        } catch (IOException e) {
            System.err.println("File Save Error");
        }
    }


    @Override
    public void loadFromFile(File file) {
        // Deserialize the HashMap
        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            // deserialize object
            users = (Map<UUID, User>) in.readObject();
            System.out.println("HashMap has been deserialized.");

            for (Map.Entry<UUID, User> entry : users.entrySet()) {
                System.out.println(entry.getKey());
                User usr = entry.getValue();
                System.out.println(usr.getUserId() + "," + usr.getUsername() + "," + Arrays.toString(usr.getPasswordHashed()) + "," + usr.getRole().toString());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("File Load Error");
        }
    }

    @Override
    public Collection<User> getItems() {
        return users.values();
    }
}
