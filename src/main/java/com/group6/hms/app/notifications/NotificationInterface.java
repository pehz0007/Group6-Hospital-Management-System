package com.group6.hms.app.notifications;

import java.util.List;
import java.util.UUID;

/**
 * The {@code NotificationInterface} provides methods for creating, dismissing,
 * and retrieving notifications associated with users in the system.
 */
public interface NotificationInterface {

    /**
     * Creates a new notification and adds it to the system.
     *
     * @param notification the {@code Notification} object to be created
     */
    void createNotification(Notification notification);

    /**
     * Dismisses a specific notification, removing it from the system.
     *
     * @param notification the {@code Notification} object to be dismissed
     */
    void dismissNotification(Notification notification);

    /**
     * Dismisses all notifications associated with a specific user.
     *
     * @param userId the {@code UUID} of the user whose notifications are to be dismissed
     */
    void dismissAllNotificationFromUser(UUID userId);

    /**
     * Retrieves all notifications associated with a specific user.
     *
     * @param userId the {@code UUID} of the user whose notifications are to be retrieved
     * @return a {@code List} of {@code Notification} objects for the specified user
     */
    List<Notification> getNotifications(UUID userId);

}
