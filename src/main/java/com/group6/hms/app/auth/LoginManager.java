package com.group6.hms.app.auth;

import com.group6.hms.app.roles.Administrator;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.app.roles.Pharmacist;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

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

        loginManager.createUser(new Patient("shirokuma", "password".toCharArray()));
        loginManager.createUser(new Doctor("tonkatsu", "password".toCharArray()));
        loginManager.createUser(new Administrator("admin", "password".toCharArray()));
        loginManager.createUser(new Pharmacist("Pharmacist 1", "Password3".toCharArray()));

        loginManager.saveUsersToFile();
        loginManager.loadUsersFromFile();
        loginManager.printUsers();
    }

    protected LoginManager() {}

    public void loadUsersFromFile(){
        userStorageProvider.loadFromFile(usersFile);
    }
    public void saveUsersToFile(){
        userStorageProvider.saveToFile(usersFile);
    }


    public void createUser(User user){
        userStorageProvider.addNewItem(user);
    }

    public boolean login(String username, char[] password){
        User user = findUser(username);
        if(user == null){
            //User does not exist
            return false;
        }
        //Verify password
        boolean result = Arrays.equals(user.getPasswordHashed(), PasswordUtils.hashPassword(password));
        if(result)currentLoginUser.set(user);
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

    /**
     * Print the list of users inside the database
     */
    public void printUsers() {
        for (User user : userStorageProvider.getItems()) {
            System.out.println(user.getUserId());
            System.out.println(user.getUserId() + "," + user.getUsername() + "," + Arrays.toString(user.getPasswordHashed()) + "," + user);
        }
    }

}
