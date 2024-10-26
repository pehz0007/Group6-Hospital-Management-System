package com.group6.hms.app.screens.admin;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.auth.LoginManagerHolder;
import com.group6.hms.app.auth.User;
import com.group6.hms.app.roles.*;
import com.group6.hms.app.screens.admin.importer.PatientsCSVReader;
import com.group6.hms.app.screens.admin.importer.StaffsCSVReader;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.option.Option;
import com.group6.hms.framework.screens.option.OptionsUtils;
import com.group6.hms.framework.screens.pagination.PaginationTableScreen;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class ViewAndManageUsersScreen extends PaginationTableScreen<UserView> {

    private final int CREATE_USER = 5;
    private final int IMPORT_STAFFS = 6;
    private final int IMPORT_PATIENTS = 7;
    private LoginManager loginManager;

    private static final int FILTER_NONE = 1;
    private static final int FILTER_DOCTOR = 2;
    private static final int FILTER_PHARMACIST = 3;
    private static final int FILTER_ADMINISTRATOR = 4;

    private static final NavigableMap<Integer, Option> filterOption = new TreeMap<>(Map.of(
            FILTER_NONE, new Option(FILTER_NONE, "No Filtering", ConsoleColor.PURPLE),
            FILTER_DOCTOR, new Option(FILTER_DOCTOR, "Filter by Doctors", ConsoleColor.PURPLE),
            FILTER_PHARMACIST, new Option(FILTER_PHARMACIST, "Filter by Pharmacist", ConsoleColor.PURPLE),
            FILTER_ADMINISTRATOR, new Option(FILTER_ADMINISTRATOR, "Filter by Administrator", ConsoleColor.PURPLE)
    ));

    public ViewAndManageUsersScreen() {
        super("Users", null);
        addOption(CREATE_USER, "Create New User");
        addOption(IMPORT_STAFFS, "Import Staffs");
        addOption(IMPORT_PATIENTS, "Import Patients");
        setFilterFunction(this::filter);
    }

    private List<UserView> filter(List<UserView> users){
        int selectedFilterOption = OptionsUtils.askOptions(consoleInterface, filterOption);
        if(selectedFilterOption == FILTER_NONE){
            return users;
        } else if(selectedFilterOption == FILTER_DOCTOR){
            return users.stream().filter(user -> user.getUser() instanceof Doctor).toList();
        }else if(selectedFilterOption == FILTER_PHARMACIST){
            return users.stream().filter(user -> user.getUser() instanceof Pharmacist).toList();
        }else if(selectedFilterOption == FILTER_ADMINISTRATOR){
            return users.stream().filter(user -> user.getUser() instanceof Administrator).toList();
        }
        throw new RuntimeException("Invalid filter option");
    }


    @Override
    public void onStart() {
        super.onStart();
        loginManager = LoginManagerHolder.getLoginManager();
        updateUserViewsTable();
    }

    private void updateUserViewsTable() {
        Collection<User> users = loginManager.getAllUsers();
        List<UserView> userViews = users.stream().map(UserView::new).toList();
        setList(userViews);
    }

    @Override
    protected void handleOption(int optionId) {
        super.handleOption(optionId);
        if (optionId == CREATE_USER) {
            UserUtils.askForUserCreation(consoleInterface, loginManager);
            updateUserViewsTable();
            setCurrentTextConsoleColor(ConsoleColor.GREEN);
            println("User has been created!");
            waitForKeyPress();
        }else if(optionId == IMPORT_STAFFS){
            print("Staffs File Location:");
            String filePath = readString();

            try {
                StaffsCSVReader staffsCSVReader = new StaffsCSVReader(new FileReader(filePath));
                List<Staff> staffs = staffsCSVReader.readAllStaffs();
                for (Staff staff : staffs) {
                    loginManager.createUser(staff);
                }
                loginManager.saveUsersToFile();

                setCurrentTextConsoleColor(ConsoleColor.GREEN);
                println("Staffs imported successfully!");
                updateUserViewsTable();
                waitForKeyPress();
            } catch (FileNotFoundException e) {
                setCurrentTextConsoleColor(ConsoleColor.RED);
                println("Unable to find file!");
                waitForKeyPress();
            }
        }else if(optionId == IMPORT_PATIENTS){
            print("Patients File Location:");
            String filePath = readString();

            try {
                PatientsCSVReader patientsCSVReader = new PatientsCSVReader(new FileReader(filePath));
                List<Patient> patients = patientsCSVReader.readAllPatients();
                for (Patient patient : patients) {
                    loginManager.createUser(patient);
                }
                loginManager.saveUsersToFile();

                setCurrentTextConsoleColor(ConsoleColor.GREEN);
                println("Patients imported successfully!");
                updateUserViewsTable();
                waitForKeyPress();
            } catch (FileNotFoundException e) {
                setCurrentTextConsoleColor(ConsoleColor.RED);
                println("Unable to find file!");
                waitForKeyPress();
            }
        }
    }
}
