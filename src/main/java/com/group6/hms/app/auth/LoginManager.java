package com.group6.hms.app.auth;

import com.group6.hms.app.roles.*;
import com.group6.hms.app.storage.SerializationStorageProvider;
import com.group6.hms.app.storage.StorageProvider;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class LoginManager {

    private StorageProvider<User> userStorageProvider = new SerializationStorageProvider<>();

    private ThreadLocal<User> currentLoginUser = new ThreadLocal<>();
    private static final File usersFile = new File("data/users.ser");

    /**
     * RUN THIS TO RESET DATABASE
     *
     */
    public static void main(String[] args) {
        //Generate sample file
        LoginManager loginManager = LoginManagerHolder.getLoginManager();

        loginManager.createUser(new Patient("P1011", "password".toCharArray(), "freya", Gender.Male));
        loginManager.createUser(new Doctor("D0011", "password".toCharArray(), "ethan", Gender.Male, 22));
        loginManager.createUser(new Administrator("A001", "password".toCharArray(), "phoebe", Gender.Female, 34));
        loginManager.createUser(new Pharmacist("P0003", "password".toCharArray(), "sage", Gender.Female, 50));

        loginManager.saveUsersToFile();
        loginManager.loadUsersFromFile();
        loginManager.printUsers();
    }

    public LoginManager() {}

    public void loadUsersFromFile(){
        userStorageProvider.loadFromFile(usersFile);
    }
    public void saveUsersToFile(){
        userStorageProvider.saveToFile(usersFile);
    }


    public void createUser(User user){
        if(findUser(user.getUserId()) != null) throw new UserCreationException("User already exists");
        userStorageProvider.addNewItem(user);
    }

    public void deleteUser(User user) {
        userStorageProvider.removeItem(user);
        userStorageProvider.saveToFile(usersFile);
    }

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


    public User findUser(String userId){
        for(User user : userStorageProvider.getItems()){
            if(user.getUserId().equalsIgnoreCase(userId)){
                return user;
            }
        }
        return null;
    }

    public void changePassword(User user, char[] newPassword){
        user.changePassword(newPassword);
        //Update file after user change their password
        userStorageProvider.saveToFile(usersFile);
    }

    public Collection<User> getAllUsers(){
        return userStorageProvider.getItems();
    }

    public User getCurrentlyLoggedInUser() {
        return currentLoginUser.get();
    }

    public void logout(){
        currentLoginUser.set(null);
    }

    public boolean isLoggedIn(){
        return currentLoginUser != null;
    }

    public Collection<Doctor> getDoctors() {
        return getAllUsers().parallelStream()
                .filter(Doctor.class::isInstance)
                .map(Doctor.class::cast)
                .collect(Collectors.toList());
    }

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
