package com.group6.hms.app.screens.admin;

import com.group6.hms.app.managers.auth.LoginManager;
import com.group6.hms.app.managers.auth.User;
import com.group6.hms.app.roles.*;
import com.group6.hms.app.screens.admin.importer.InvalidStaffRoleException;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.ConsoleInterface;
import com.group6.hms.framework.screens.option.Option;
import com.group6.hms.framework.screens.option.OptionsUtils;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * The {@code UserUtils} class provides utility methods for managing user creation
 * within the administration interface of the Hospital Management System (HMS).
 * It facilitates the input of user information and the creation of user objects
 * for different roles, including Administrator, Doctor, Pharmacist, and Patient.
 */
public class UserUtils {

    private static final int ADMIN_ID = 1;
    private static final int PATIENT_ID = 2;
    private static final int DOCTOR_ID = 3;
    private static final int PHARMACIST_ID = 4;

    private static final int FEMALE_ID = 1;
    private static final int MALE_ID = 2;

    private static final NavigableMap<Integer, Option> options = new TreeMap<>(Map.of(
            ADMIN_ID, new Option(ADMIN_ID, "Admin", ConsoleColor.PURPLE),
            PATIENT_ID, new Option(PATIENT_ID, "Patient", ConsoleColor.PURPLE),
            DOCTOR_ID, new Option(DOCTOR_ID, "Doctor", ConsoleColor.PURPLE),
            PHARMACIST_ID, new Option(PHARMACIST_ID, "Pharmacist", ConsoleColor.PURPLE)
    ));

    private static final NavigableMap<Integer, Option> genderOptions = new TreeMap<>(Map.of(
            FEMALE_ID, new Option(FEMALE_ID, "Female", ConsoleColor.PURPLE),
            MALE_ID, new Option(MALE_ID, "Male", ConsoleColor.PURPLE)
    ));

    /**
     * Prompts the user to enter information required for creating a new user in the system.
     * <p>
     * Based on the role selected (e.g., Admin, Doctor, Pharmacist, or Patient), different input fields
     * are displayed to gather necessary data. The new user is then created and registered within
     * the specified {@link LoginManager}.
     *
     * @param consoleInterface the interface through which user input is received
     * @param loginManager     the login manager responsible for user management operations
     * @throws InvalidStaffRoleException if an invalid role selection is encountered
     */
    public static void askForUserCreation(ConsoleInterface consoleInterface, LoginManager loginManager) {
        int selectedOptionId = OptionsUtils.askOptions(consoleInterface, options);
        // Create user
        consoleInterface.print("User ID: ");
        String userId = consoleInterface.readString();

        consoleInterface.print("Password: ");
        char[] password = consoleInterface.readPassword();

        consoleInterface.print("Name: ");
        String name = consoleInterface.readString();

        consoleInterface.print("Gender: ");
        int genderId = OptionsUtils.askOptions(consoleInterface, genderOptions);
        Gender gender = switch (genderId){
            case FEMALE_ID -> Gender.Female;
            case MALE_ID -> Gender.Male;
            default -> throw new IllegalStateException("Unexpected gender value: " + genderId);
        };

        User user;
        if(selectedOptionId == ADMIN_ID || selectedOptionId == PHARMACIST_ID || selectedOptionId == DOCTOR_ID) {
            //Create Staff
//            consoleInterface.print("Staff ID: ");
//            String staffId = consoleInterface.readString();

            consoleInterface.print("Age: ");
            int age = consoleInterface.readInt();

            user = switch (selectedOptionId){
                case ADMIN_ID -> new Administrator(userId, password, name, gender, age);
                case DOCTOR_ID -> new Doctor(userId, password, name, gender, age);
                case PHARMACIST_ID -> new Pharmacist(userId, password, name, gender, age);
                default -> throw new IllegalStateException("Unexpected value: " + selectedOptionId);
            };
        }else if(selectedOptionId == PATIENT_ID) {
            //Create patient
            consoleInterface.print("Email: ");
            consoleInterface.print("Phone Number: ");
            String contactInformation = consoleInterface.readString();
            String phoneNumber = consoleInterface.readString();
            user = new Patient(userId, password, name, gender, contactInformation, phoneNumber);

        }else throw new InvalidStaffRoleException("Unexpected role value: " + selectedOptionId);

        loginManager.createUser(user);
        loginManager.saveUsersToFile();

    }



}
