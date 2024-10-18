package com.group6.hms.app.screens.admin;

import com.group6.hms.app.auth.User;
import com.group6.hms.app.notifications.Notification;
import com.group6.hms.app.notifications.NotificationManagerHolder;
import com.group6.hms.app.notifications.NotificationScreen;
import com.group6.hms.app.screens.MainScreen;
import com.group6.hms.framework.screens.PaginationTableScreen;
import com.group6.hms.app.auth.LogoutScreen;

import java.util.Collection;
import java.util.List;

public class AdministratorScreen extends LogoutScreen {

    private final int VIEW_NOTIFICATIONS = 2;
    private final int CREATE_USER = 3;
    private final int VIEW_ALL_USERS = 4;
    private final int VIEW_APPOINTMENTS = 5;

    /**
     * Constructor to initialize the AdministratorScreen.
     **/
    public AdministratorScreen() {
        super("Administrator");

        addOption(VIEW_NOTIFICATIONS, "View Notifications");
        addOption(CREATE_USER, "Create User");
        addOption(VIEW_ALL_USERS, "View All Users");
        addOption(VIEW_APPOINTMENTS, "View All Appointments");
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
            case CREATE_USER -> {
                //Create user
                print("Username:");

            }
            case VIEW_NOTIFICATIONS -> {
                navigateToScreen(new NotificationScreen());
            }
            case VIEW_ALL_USERS -> {
                Collection<User> users = getLoginManager().getAllUsers();
                navigateToScreen(new PaginationTableScreen<>("Users", users.stream().map(UserView::new).toList()));
            }
            case VIEW_APPOINTMENTS -> {
                //Retrieve all Appointments in the database

                //Display all the Appointments

            }
        }
    }
}
