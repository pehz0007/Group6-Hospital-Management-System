package com.group6.hms.app.screens.admin;

import com.group6.hms.app.auth.User;
import com.group6.hms.app.managers.AppointmentManager;
import com.group6.hms.app.models.Appointment;
import com.group6.hms.app.models.MedicationStock;
import com.group6.hms.app.notifications.Notification;
import com.group6.hms.app.notifications.NotificationManagerHolder;
import com.group6.hms.app.notifications.NotificationScreen;
import com.group6.hms.app.screens.MainScreen;
import com.group6.hms.app.auth.LogoutScreen;
import com.group6.hms.app.screens.admin.importer.MedicationStockCSVReader;
import com.group6.hms.app.storage.SerializationStorageProvider;
import com.group6.hms.app.storage.StorageProvider;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.calendar.CalendarScreen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

public class AdministratorScreen extends LogoutScreen {

    private final int VIEW_NOTIFICATIONS = 2;
    private final int VIEW_AND_MANAGE_USERS = 3;
    private final int VIEW_APPOINTMENTS = 4;
    private final int VIEW_AND_MANAGE_MEDICATION_INVENTORY = 5;
    private final int APPROVE_REPLENISHMENT_REQUEST = 6;
    private final int IMPORT_MEDICATIONS_STOCK = 7;
    /**
     * Constructor to initialize the AdministratorScreen.
     **/
    public AdministratorScreen() {
        super("Administrator");

        addOption(VIEW_NOTIFICATIONS, "View Notifications");
        addOption(VIEW_AND_MANAGE_USERS, "View and Manage Users");
        addOption(VIEW_APPOINTMENTS, "View All Appointments");
        addOption(VIEW_AND_MANAGE_MEDICATION_INVENTORY, "View and Manage Medication Inventory");
        addOption(APPROVE_REPLENISHMENT_REQUEST, "Approve Replenishment Request");
        addOption(IMPORT_MEDICATIONS_STOCK, "Import Medications");
    }

    @Override
    public void onStart() {
        User user = getLoginManager().getCurrentlyLoggedInUser();
        List<Notification> notifications = NotificationManagerHolder.getNotificationManager().getNotifications(user.getSystemUserId());
        NotificationManagerHolder.getNotificationManager().createNotification(new Notification("Appointment with patient", "You have an appointment with patient 1!", user.getSystemUserId()));
        println("Welcome, " + user.getName() + ", you have " + notifications.size() + " notifications");
        super.onStart();

    }

    @Override
    protected void onLogout() {
        newScreen(new MainScreen());
    }

    @Override
    protected void handleOption(int optionId) {
        switch (optionId) {

            case VIEW_NOTIFICATIONS -> {
                navigateToScreen(new NotificationScreen());
            }
            case VIEW_AND_MANAGE_USERS -> {
                navigateToScreen(new ViewAndManageUsersScreen());
            }
            case VIEW_APPOINTMENTS -> {

                //SAMPLE
                AppointmentManager appointmentManager = new AppointmentManager();
                Map<LocalDate, List<Appointment>> appointments = appointmentManager.getAllAppointments().stream().collect(groupingBy(Appointment::getDate));
                navigateToScreen(new CalendarScreen<>("Appointments", appointments));
//                Multimap<LocalDate, AppointmentView> appointmentViews = MultimapBuilder.hashKeys().arrayListValues().build();
//                var a1 = new AppointmentView(UUID.randomUUID(), UUID.randomUUID(), AppointmentStatus.CONFIRMED, LocalDateTime.now());
//                appointmentViews.put(a1.getEventDateTime().toLocalDate(), a1);
//                navigateToScreen(new CalendarScreen<>("Appointments", appointmentViews));

                //Retrieve all Appointments in the database

                //Display all the Appointments

            }
            case IMPORT_MEDICATIONS_STOCK -> {
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
}
