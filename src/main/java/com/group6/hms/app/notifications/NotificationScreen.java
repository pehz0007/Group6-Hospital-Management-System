package com.group6.hms.app.notifications;

import com.group6.hms.app.managers.auth.LoginManagerHolder;
import com.group6.hms.app.managers.auth.User;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;
import com.group6.hms.framework.screens.pagination.SinglePaginationTableScreen;

/**
 * The {@code NotificationScreen} class displays notifications for the currently
 * logged-in user in a paginated table format. It allows users to view individual
 * notifications and provides an option to dismiss all notifications at once.
 */
public class NotificationScreen extends SinglePaginationTableScreen<Notification> {

    private static final int MAX_NOTIFICATIONS_DISPLAY_PER_PAGE = 3;
    private static final int DISMISS_ALL_NOTIFICATION = 3;

    private NotificationManager notificationManager;

    /**
     * Constructs a new {@code NotificationScreen} and initializes the notification display.
     * It sets up pagination with a maximum of {@code MAX_NOTIFICATIONS_DISPLAY_PER_PAGE}
     * items per page and adds an option to dismiss all notifications.
     */
    public NotificationScreen() {
        super("Notifications", null, MAX_NOTIFICATIONS_DISPLAY_PER_PAGE);
        addOption(DISMISS_ALL_NOTIFICATION, "Dismiss All Notifications");
        updateNotifications();
    }

    /**
     * Updates the notifications displayed for the currently logged-in user.
     * Retrieves the user's notifications from the {@code NotificationManager}.
     */
    private void updateNotifications() {
        User user = LoginManagerHolder.getLoginManager().getCurrentlyLoggedInUser();
        notificationManager = NotificationManagerHolder.getNotificationManager();
        setList(notificationManager.getNotifications(user.getSystemUserId()));
    }

    /**
     * Handles user-selected options. If the dismiss all notifications option is selected,
     * it removes all notifications for the current user and refreshes the display.
     *
     * @param optionId the ID of the selected option
     */
    @Override
    protected void handleOption(int optionId) {
        if(optionId == DISMISS_ALL_NOTIFICATION) {
            User user = LoginManagerHolder.getLoginManager().getCurrentlyLoggedInUser();
            notificationManager.dismissAllNotificationFromUser(user.getSystemUserId());
            updateNotifications();
        }
    }

    /**
     * Displays a single notification item in a vertical table format.
     *
     * @param item the {@code Notification} to be displayed
     */
    @Override
    public void displaySingleItem(Notification item) {
        PrintTableUtils.printItemAsVerticalTable(consoleInterface, item);
    }
}
