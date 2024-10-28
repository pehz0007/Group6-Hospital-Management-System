package com.group6.hms.app.screens.admin;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.auth.LoginManagerHolder;
import com.group6.hms.app.auth.User;
import com.group6.hms.app.models.MedicationStock;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.app.roles.Staff;
import com.group6.hms.app.screens.admin.importer.MedicationStockCSVReader;
import com.group6.hms.app.storage.SerializationStorageProvider;
import com.group6.hms.app.storage.StorageProvider;
import com.group6.hms.app.auth.UserCreationException;
import com.group6.hms.app.roles.*;
import com.group6.hms.app.screens.admin.importer.PatientsCSVReader;
import com.group6.hms.app.screens.admin.importer.StaffsCSVReader;
import com.group6.hms.app.screens.patient.PatientConfigurationScreen;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.option.Option;
import com.group6.hms.framework.screens.option.OptionsUtils;
import com.group6.hms.framework.screens.pagination.PaginationTableScreen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class ViewAndManageUsersScreen extends PaginationTableScreen<UserView> {
    private final int CREATE_USER = 4;
    private final int IMPORT_STAFFS = 5;
    private final int IMPORT_PATIENTS = 6;
    private final int UPDATE_USER = 7;
    private final int DELETE_USER = 8;
   private final int IMPORT_MEDICATION_STOCK = 9;

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
        addOption(UPDATE_USER, "Update Existing User");
        addOption(DELETE_USER, "Delete Existing User");
        addOption(IMPORT_STAFFS, "Import Staffs");
        addOption(IMPORT_PATIENTS, "Import Patients");
        addOption(IMPORT_MEDICATION_STOCK, "Import Medication Stock");

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
        }else if(optionId == UPDATE_USER){
            setCurrentTextConsoleColor(ConsoleColor.PURPLE);
            print("User ID:");
            String userId = readString();
            User user = loginManager.findUser(userId);

            if(user != null){
                if(user instanceof Patient patient){
                    navigateToScreen(new PatientConfigurationScreen(patient));
                }
            }else{
                setCurrentTextConsoleColor(ConsoleColor.RED);
                println("User not found!");
                waitForKeyPress();
            }

        }else if(optionId == DELETE_USER){
            setCurrentTextConsoleColor(ConsoleColor.PURPLE);
            print("User ID:");
            String userId = readString();
            User user = loginManager.findUser(userId);

            if(user != null){
                loginManager.deleteUser(user);
                setCurrentTextConsoleColor(ConsoleColor.GREEN);
                println("User has been deleted!");
                updateUserViewsTable();
                waitForKeyPress();
            }else{
                setCurrentTextConsoleColor(ConsoleColor.RED);
                println("User not found!");
                updateUserViewsTable();
                waitForKeyPress();
            }
        } else if(optionId == IMPORT_STAFFS){
            print("Staffs File Location:");
            String filePath = readString();

            try {
                StaffsCSVReader staffsCSVReader = new StaffsCSVReader(new FileReader(filePath));
                List<Staff> staffs = staffsCSVReader.readAllStaffs();
                for (Staff staff : staffs) {
                    try{
                        loginManager.createUser(staff);
                    }catch (UserCreationException e){
                        setCurrentTextConsoleColor(ConsoleColor.YELLOW);
                        println("Skipping " + staff.getUserId() + " due to duplicated staff id");
                    }
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
                    try{
                        loginManager.createUser(patient);
                    }catch (UserCreationException e){
                        setCurrentTextConsoleColor(ConsoleColor.YELLOW);
                        println("Skipping " + patient.getUserId() + " due to duplicated patient id");
                    }
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
        else if (optionId == IMPORT_MEDICATION_STOCK) {
            print("Medication File Location:");
            String filePath = readString();
            try {
                MedicationStockCSVReader medicationStockCSVReader = new MedicationStockCSVReader(new FileReader(filePath));
                List<MedicationStock> medications = medicationStockCSVReader.readAllMedications();
                StorageProvider<MedicationStock> medicationStorageProvider = new SerializationStorageProvider<>();
                File medicationsFile = new File("data/medications.ser");

                for (MedicationStock medicationStock : medications) {
                    medicationStorageProvider.addNewItem(medicationStock);
                }
                medicationStorageProvider.saveToFile(medicationsFile);
                setCurrentTextConsoleColor(ConsoleColor.GREEN);
                println("Medication Stock imported successfully!");

            } catch (FileNotFoundException e) {
                setCurrentTextConsoleColor(ConsoleColor.RED);
                println("Unable to find file!");
                waitForKeyPress();
            }
        }
    }
}
