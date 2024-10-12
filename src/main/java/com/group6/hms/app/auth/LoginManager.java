package com.group6.hms.app.auth;

import com.group6.hms.app.auth.roles.Role;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class LoginManager {

    private StorageProvider<User> userStorageProvider = new UserInMemoryStorageProvider();

    private static ThreadLocal<User> currentlyLoggedInUser = new ThreadLocal<>();
    private static final File usersFile = new File("users.ser");

    public static void main(String[] args) {
        //Generate sample file
        LoginManager loginManager = new LoginManager();

        loginManager.createUser("shirokuma", "password".toCharArray(), Role.Patient);
        loginManager.createUser("tonkatsu", "password".toCharArray(), Role.Doctor);
        loginManager.createUser("admin", "password".toCharArray(), Role.Administrator);

        loginManager.saveUsersToFile();
        loginManager.loadUsersFromFile();
        loginManager.printUsers();
    }

    public void loadUsersFromFile(){
        userStorageProvider.loadFromFile(usersFile);
    }
    public void saveUsersToFile(){
        userStorageProvider.saveToFile(usersFile);
    }


    public void createUser(String username, char[] password, Role role){
        userStorageProvider.addNewItem(new User(PasswordUtils.generateUUID(), username, PasswordUtils.hashPassword(password), role));
    }


    public boolean login(String username, char[] password){
        User user = findUser(username);
        if(user == null){
            //User does not exist
            return false;
        }
        //Verify password
        boolean result = Arrays.equals(user.getPasswordHashed(), PasswordUtils.hashPassword(password));
        if(result)currentlyLoggedInUser.set(user);
        return result;

    }

    private User findUser(String username){
        for(User user : userStorageProvider.getItems()){
            if(user.getUsername().equalsIgnoreCase(username)){
                return user;
            }
        }
        return null;
    }

    public void changePassword(User user, char[] newPassword){
        userStorageProvider.removeItem(user);
        User updatedUser = new User(user.getUserId(), user.getUsername(),
                PasswordUtils.hashPassword(newPassword), user.getRole());
        userStorageProvider.addNewItem(updatedUser);
        
        currentlyLoggedInUser.set(updatedUser);
    }

    public static User getCurrentlyLoggedInUser() {
        return currentlyLoggedInUser.get();
    }

    public static void logout(){
        currentlyLoggedInUser.remove();
    }

    public static boolean isLoggedIn(){
        return currentlyLoggedInUser.get() != null;
    }

    /**
     * Print the list of users inside the database
     */
    public void printUsers() {
        for (User user : userStorageProvider.getItems()) {
            System.out.println(user.getUserId());
            System.out.println(user.getUserId() + "," + user.getUsername() + "," + Arrays.toString(user.getPasswordHashed()) + "," + user.getRole().toString());
        }
    }

}
