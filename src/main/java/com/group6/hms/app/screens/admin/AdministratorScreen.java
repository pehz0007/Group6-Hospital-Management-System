package com.group6.hms.app.screens.admin;

import com.group6.hms.app.managers.auth.models.User;
import com.group6.hms.app.managers.appointment.AppointmentManager;
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

public class AdministratorScreen extends LogoutScreen {

    private final int VIEW_NOTIFICATIONS = 2;
    private final int VIEW_AND_MANAGE_USERS = 3;
    private final int VIEW_APPOINTMENTS = 4;
    private final int VIEW_AND_MANAGE_MEDICATION_INVENTORY = 5;
    /**
     * Constructor to initialize the AdministratorScreen.
     **/
    public AdministratorScreen() {
        super("Administrator");

        addOption(VIEW_NOTIFICATIONS, "View Notifications");
        addOption(VIEW_AND_MANAGE_USERS, "View and Manage Users");
        addOption(VIEW_APPOINTMENTS, "View All Appointments");
        addOption(VIEW_AND_MANAGE_MEDICATION_INVENTORY, "View and Manage Medication Inventory");

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
            case VIEW_AND_MANAGE_MEDICATION_INVENTORY -> {
                navigateToScreen(new AdminViewAndManageMedicationScreen());
            }
            case VIEW_APPOINTMENTS -> {

                //SAMPLE
                AppointmentManager appointmentManager = new AppointmentManager();
                Map<LocalDate, List<Appointment>> appointments = appointmentManager.getAllAppointments().stream().collect(groupingBy(Appointment::getDate));
                navigateToScreen(new CalendarScreen<>("Appointments", appointments));

            }

        }
    }
}
