package system;

import users.User;

import java.io.File;
import java.util.Collection;

public class LoginManager {

    private Collection<User> users;

    private User currentlyLoggedInUser;

    public void readUserFromFile(File file){

    }

    public boolean login(String username, String password){
        return true;
    }

    public User getCurrentlyLoggedInUser() {
        return currentlyLoggedInUser;
    }
}
