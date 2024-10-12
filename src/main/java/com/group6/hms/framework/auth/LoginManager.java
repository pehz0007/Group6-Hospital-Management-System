package com.group6.hms.framework.auth;

import java.util.Arrays;

public class LoginManager {

    private StorageProvider<User> userStorageProvider = new UserInMemoryStorageProvider();

    private static ThreadLocal<User> currentlyLoggedInUser = new ThreadLocal<>();

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
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public static User getCurrentlyLoggedInUser() {
        return currentlyLoggedInUser.get();
    }
}
