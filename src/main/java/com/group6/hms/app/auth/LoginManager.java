package com.group6.hms.app.auth;

import com.group6.hms.app.models.BloodType;
import com.group6.hms.app.roles.*;
import com.group6.hms.app.storage.SerializationStorageProvider;
import com.group6.hms.app.storage.StorageProvider;
import com.group6.hms.app.models.MedicalRecord;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * LoginManager provides authentication management for users,
 * including login, logout, and user account management. It handles loading and saving
 * users from a file, finding users by ID, changing passwords, and managing the
 * currently logged-in user.
 * It manages the users file I/O by delegating the responsibility to an instance of userStorageProvider, an instance of {@link SerializationStorageProvider}
 */
public class LoginManager {

    private StorageProvider<User> userStorageProvider = new SerializationStorageProvider<>();

    private ThreadLocal<User> currentLoginUser = new ThreadLocal<>();
    private static final File usersFile = new File("data/users.ser");

    public LoginManager() {}

    /**
     * Loads users from users serializable file
     */
    public void loadUsersFromFile(){
        userStorageProvider.loadFromFile(usersFile);
    }

    /**
     * Saves users to the users serializable file
     */
    public void saveUsersToFile(){
        userStorageProvider.saveToFile(usersFile);
    }

    /**
     * Searches userStorageProvider for a {@link User} based on the given system user UUID
     * @param systemUserId User's unique ID in system
     * @return the {@link User} instance with the found user's details if it exists, otherwise returns null
     */
    public User findUser(UUID systemUserId){
        for(User user : userStorageProvider.getItems()){
            if(user.getSystemUserId().equals(systemUserId)){
                return user;
            }
        }
        return null;
    }

    /**
     * Adds a new user to the list maintained by userStorageProvider
     * @param user User to add to the user list
     */
    public void createUser(User user){
        if(findUser(user.getUserId()) != null) throw new UserCreationException("User already exists");
        userStorageProvider.addNewItem(user);
    }

    /**
     * Deletes a specified user from the list and saves to the users serializable file
     * @param user User to delete from the user list
     */
    public void deleteUser(User user) {
        userStorageProvider.removeItem(user);
        userStorageProvider.saveToFile(usersFile);
    }

    /**
     * Searches whether userId matches existing user in the list maintained by userStorageProvider.
     * Then, hashes the input password and checks against the hashed password stored in the {@link User} instance
     * Sets the currently logged in user to the given user
     * @param userId userId to search
     * @param password (unhashed) password to verify
     * @return If userId does not match an existing user or if hashed passwords do not match, returns false. Otherwise if match found and hash passwords match, returns true
     */
    public boolean login(String userId, char[] password){
        User user = findUser(userId);
        if(user == null){
            //User does not exist
            return false;
        }
        //Verify password
        boolean result = Arrays.equals(user.getPasswordHashed(), PasswordUtils.hashPassword(password));
        if(result)currentLoginUser.set(user);
        return result;

    }

    /**
     * Searches userStorageProvider for a {@link User} based on the given user UUID
     * @param userId User's unique ID
     * @return the {@link User} instance with the found user's details if it exists, otherwise returns null
     */
    public User findUser(String userId){
        for(User user : userStorageProvider.getItems()){
            if(user.getUserId().equalsIgnoreCase(userId)){
                return user;
            }
        }
        return null;
    }

    /**
     * Updates user password and updates the users serializable file
     * @param user User to change password for
     * @param newPassword New password for the given user
     */
    public void changePassword(User user, char[] newPassword){
        user.changePassword(newPassword);
        //Update file after user change their password
        userStorageProvider.saveToFile(usersFile);
    }

    /**
     * Get all users in the system
     * @return Collection of all users in the system
     */
    public Collection<User> getAllUsers(){
        return userStorageProvider.getItems();
    }

    /**
     * Gets the currently logged in user
     * @return {@link User} instance of the currently logged in user
     */
    public User getCurrentlyLoggedInUser() {
        return currentLoginUser.get();
    }

    /**
     * Sets the currently logged in user to null
     */
    public void logout(){
        currentLoginUser.set(null);
    }

    /**
     * Checks whether any user is logged in
     * @return If no user is logged in, returns false. Otherwise, returns true
     */
    public boolean isLoggedIn(){
        return currentLoginUser != null;
    }

    /**
     * Filters the users list by {@link Doctor} class
     * @return Collection of all doctors in the system
     */
    public Collection<Doctor> get() {
        return getAllUsers().parallelStream()
                .filter(Doctor.class::isInstance)
                .map(Doctor.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Print the list of users inside the database
     */
    public void printUsers() {
        for (User user : userStorageProvider.getItems()) {
            System.out.println(user.getSystemUserId());
            System.out.println(user.getSystemUserId() + "," + user.getUserId() + "," + Arrays.toString(user.getPasswordHashed()) + "," + user);
        }
    }

}