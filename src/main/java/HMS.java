import system.LoginManager;
import users.Doctor;
import users.Patient;
import users.User;

import java.util.Scanner;

public class HMS {

    public static void main(String[] args) {

        //Perform user login
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Group 6 HMS Application");
        System.out.println("Username:");
        String username = scanner.nextLine();
        System.out.println("Password:");
        String password = scanner.nextLine();

        LoginManager loginManager = new LoginManager();
        if(loginManager.login(username, password)){
            System.out.println("You have successfully logged in!");

            User user = loginManager.getCurrentlyLoggedInUser();

            //Display user specific menu
            if(user instanceof Patient patient){
                System.out.println("You are a patient!");

            }else if(user instanceof Doctor){
                System.out.println("You are a doctor!");
            }
        }else {
            System.out.println("Something went wrong!");
        }

    }

}
