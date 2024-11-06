package com.group6.hms.app.screens.admin;

import com.group6.hms.app.managers.appointment.AppointmentManagerHolder;
import com.group6.hms.app.managers.appointment.AppointmentManager;
import com.group6.hms.app.managers.auth.User;
import com.group6.hms.app.managers.appointment.models.Appointment;
import com.group6.hms.app.notifications.Notification;
import com.group6.hms.app.notifications.NotificationManagerHolder;
import com.group6.hms.app.notifications.NotificationScreen;
import com.group6.hms.app.screens.MainScreen;
import com.group6.hms.app.managers.auth.LogoutScreen;
import com.group6.hms.framework.screens.calendar.CalendarScreen;

import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

/**
 * The {@code AdministratorScreen} represents the screen for the administrator role,
 * allowing management of various functionalities including notifications, user management,
 * appointments, and medication inventory.
 */
public class AdministratorScreen extends LogoutScreen {

    /**
     * Option ID for viewing notifications.
     */
    private final int VIEW_NOTIFICATIONS = 2;

    /**
     * Option ID for viewing and managing users.
     */
    private final int VIEW_AND_MANAGE_USERS = 3;

    /**
     * Option ID for viewing all appointments.
     */
    private final int VIEW_APPOINTMENTS = 4;

    /**
     * Option ID for viewing and managing medication inventory.
     */
    private final int VIEW_AND_MANAGE_MEDICATION_INVENTORY = 5;

    /**
     * Constructs an {@code AdministratorScreen} instance to initialize the administrator menu options.
     */
    public AdministratorScreen() {
        super("Administrator");

        addOption(VIEW_NOTIFICATIONS, "View Notifications");
        addOption(VIEW_AND_MANAGE_USERS, "View and Manage Users");
        addOption(VIEW_APPOINTMENTS, "View All Appointments");
        addOption(VIEW_AND_MANAGE_MEDICATION_INVENTORY, "View and Manage Medication Inventory");

    }

    /**
     * Starts the screen by retrieving the currently logged-in user and their notifications,
     * displaying a welcome message along with the number of notifications.
     */
    @Override
    public void onStart() {
        User user = getLoginManager().getCurrentlyLoggedInUser();
        List<Notification> notifications = NotificationManagerHolder.getNotificationManager().getNotifications(user.getSystemUserId());
        NotificationManagerHolder.getNotificationManager().createNotification(new Notification("Appointment with patient", "You have an appointment with patient 1!", user.getSystemUserId()));
        println("Welcome, " + user.getName() + ", you have " + notifications.size() + " notifications");
        super.onStart();

    }

    /**
     * Handles the logout action by navigating to the main screen.
     */
    @Override
    protected void onLogout() {
        newScreen(new MainScreen());
    }

    /**
     * Handles the user's option selection from the administrator menu.
     *
     * @param optionId The ID of the selected option.
     */
    @Override
    protected void handleOption(int optionId) {
        switch (optionId) {

            case VIEW_NOTIFICATIONS -> {
                navigateToScreen(new NotificationScreen());
            }
            case VIEW_AND_MANAGE_USERS -> {
                navigateToScreen(new ViewAndManageUsersScreen());
            }
            case VIEW_AND_MANAGE_MEDICATION_INVENTORY -> {
                navigateToScreen(new AdminViewAndManageMedicationScreen());
            }
            case VIEW_APPOINTMENTS -> {

                AppointmentManager appointmentManager = AppointmentManagerHolder.getAppointmentManager();
                Map<LocalDate, List<Appointment>> appointments = appointmentManager.getAllAppointments().stream().collect(groupingBy(Appointment::getDate));
                navigateToScreen(new CalendarScreen<>("Appointments", appointments));

            }

        }
    }
}
