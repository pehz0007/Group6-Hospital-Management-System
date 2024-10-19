package com.group6.hms.app.screens.admin;

import com.group6.hms.app.auth.User;
import com.group6.hms.app.notifications.Notification;
import com.group6.hms.app.notifications.NotificationManagerHolder;
import com.group6.hms.app.notifications.NotificationScreen;
import com.group6.hms.app.screens.MainScreen;
import com.group6.hms.app.auth.LogoutScreen;

import java.util.List;

public class AdministratorScreen extends LogoutScreen {

    private final int VIEW_NOTIFICATIONS = 2;
    private final int VIEW_AND_MANAGE_USERS = 3;
    private final int VIEW_APPOINTMENTS = 4;
    private final int VIEW_AND_MANAGE_MEDICATION_INVENTORY = 5;
    private final int APPROVE_REPLENISHMENT_REQUEST = 6;

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
    }

    @Override
    public void onStart() {
        User user = getLoginManager().getCurrentlyLoggedInUser();
        List<Notification> notifications = NotificationManagerHolder.getNotificationManager().getNotifications(user.getUserId());
        NotificationManagerHolder.getNotificationManager().createNotification(new Notification("Appointment with patient", "You have an appointment with patient 1!", user.getUserId()));
        println("Welcome, " + user.getUsername() + ", you have " + notifications.size() + " notifications");
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
                //Retrieve all Appointments in the database

                //Display all the Appointments

            }
        }
    }
}
