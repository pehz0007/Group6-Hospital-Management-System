package com.group6.hms.app.screens.admin;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.auth.User;
import com.group6.hms.app.roles.Administrator;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.app.roles.Pharmacist;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.ConsoleInterface;
import com.group6.hms.framework.screens.option.Option;
import com.group6.hms.framework.screens.option.OptionsUtils;

import java.util.Map;
import java.util.TreeMap;

public class UserUtils {

    private static final int ADMIN_ID = 1;
    private static final int PATIENT_ID = 2;
    private static final int DOCTOR_ID = 3;
    private static final int PHARMACIST_ID = 4;

    private static final Map<Integer, Option> options = new TreeMap<>(Map.of(
            ADMIN_ID, new Option(ADMIN_ID, "Admin", ConsoleColor.PURPLE),
            PATIENT_ID, new Option(PATIENT_ID, "Patient", ConsoleColor.PURPLE),
            DOCTOR_ID, new Option(DOCTOR_ID, "Doctor", ConsoleColor.PURPLE),
            PHARMACIST_ID, new Option(PHARMACIST_ID, "Pharmacist", ConsoleColor.PURPLE)
    ));

    public static void askForUsernameAndPassword(ConsoleInterface consoleInterface, LoginManager loginManager) {
        // Create user
        consoleInterface.print("Username: ");
        String username = consoleInterface.readString();

        consoleInterface.print("Password: ");
        char[] password = consoleInterface.readPassword();

        int selectedOptionId = OptionsUtils.askOptions(consoleInterface, options);

        User user = switch (selectedOptionId){
           case ADMIN_ID -> new Administrator(username, password);
           case PATIENT_ID -> new Patient(username, password);
           case DOCTOR_ID -> new Doctor(username, password);
           case PHARMACIST_ID -> new Pharmacist(username, password);
           default -> throw new IllegalStateException("Unexpected value: " + selectedOptionId);
        };

        loginManager.createUser(user);
        loginManager.saveUsersToFile();
    }


}
