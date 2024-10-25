package com.group6.hms.app.screens.admin;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.auth.LoginManagerHolder;
import com.group6.hms.app.auth.User;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.app.roles.Staff;
import com.group6.hms.app.screens.admin.importer.PatientsCSVReader;
import com.group6.hms.app.screens.admin.importer.StaffsCSVReader;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.pagination.PaginationTableScreen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;
import java.util.List;

public class ViewAndManageUsersScreen extends PaginationTableScreen<UserView> {

    private final int CREATE_USER = 4;
    private final int IMPORT_STAFFS = 5;
    private final int IMPORT_PATIENTS = 6;
    private LoginManager loginManager;

    public ViewAndManageUsersScreen() {
        super("Users", null);
        addOption(CREATE_USER, "Create New User");
        addOption(IMPORT_STAFFS, "Import Staffs");
        addOption(IMPORT_PATIENTS, "Import Patients");
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
        setItems(userViews);
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
                waitForKeyPress();
            } catch (FileNotFoundException e) {
                setCurrentTextConsoleColor(ConsoleColor.RED);
                println("Unable to find file!");
                waitForKeyPress();
            }
        }
    }
}
