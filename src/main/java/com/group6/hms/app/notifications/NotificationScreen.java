package com.group6.hms.app.notifications;

import com.group6.hms.app.auth.LoginManagerHolder;
import com.group6.hms.app.auth.User;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;
import com.group6.hms.framework.screens.pagination.SinglePaginationTableScreen;

public class NotificationScreen extends SinglePaginationTableScreen<Notification> {

    private static final int MAX_NOTIFICATIONS_DISPLAY_PER_PAGE = 3;
    private static final int DISMISS_ALL_NOTIFICATION = 3;

    private NotificationManager notificationManager;

    /**
     * Constructor to initialize the NotificationScreen.
     *
     */
    public NotificationScreen() {
        super("Notifications", null, MAX_NOTIFICATIONS_DISPLAY_PER_PAGE);
        addOption(DISMISS_ALL_NOTIFICATION, "Dismiss All Notifications");
        updateNotifications();
    }

    private void updateNotifications() {
        User user = LoginManagerHolder.getLoginManager().getCurrentlyLoggedInUser();
        notificationManager = NotificationManagerHolder.getNotificationManager();
        setList(notificationManager.getNotifications(user.getSystemUserId()));
    }

    @Override
    protected void handleOption(int optionId) {
        if(optionId == DISMISS_ALL_NOTIFICATION) {
            User user = LoginManagerHolder.getLoginManager().getCurrentlyLoggedInUser();
            notificationManager.dismissAllNotificationFromUser(user.getSystemUserId());
            updateNotifications();
        }
    }

    @Override
    public void displaySingleItem(Notification item) {
        PrintTableUtils.printItemAsVerticalTable(consoleInterface, item);
    }
}
