package com.group6.hms.app.notifications;

import java.util.*;

/**
 * The {@code NotificationManager} class implements the {@link NotificationInterface}
 * and provides methods to create, dismiss, and retrieve notifications associated
 * with users in the system. It manages notifications using a map that associates
 * each user with their list of notifications.
 */
public class NotificationManager implements NotificationInterface {

    private Map<UUID, List<Notification>> notifications;

    /**
     * Initializes a new {@code NotificationManager} with an empty map of notifications.
     */
    public NotificationManager() {
        this.notifications = new HashMap<>();
    }

    /**
     * Creates a new notification for a specific user. If the user does not have any existing
     * notifications, a new list is created for them.
     *
     * @param notification the {@code Notification} object to be created and added to the user's list
     */
    @Override
    public void createNotification(Notification notification) {
        if (notification != null) {
            UUID userId = notification.getUserID();
            notifications.putIfAbsent(userId, new ArrayList<>()); // Initialize the list if not present
            notifications.get(userId).add(notification); // Add the notification to the user's list
        }
    }

    /**
     * Dismisses a specific notification for a user. If it is the user's last notification,
     * they are removed from the map.
     *
     * @param notification the {@code Notification} object to be dismissed
     */
    @Override
    public void dismissNotification(Notification notification) {
        if (notification != null) {
            List<Notification> userNotifications = notifications.get(notification.getUserID());
            if (userNotifications != null) {
                userNotifications.remove(notification); // Remove the specific notification
                if (userNotifications.isEmpty()) {
                    notifications.remove(notification.getUserID()); // Remove the user if no notifications left
                }
            }
        }
    }

    /**
     * Dismisses all notifications associated with a specific user by clearing their list
     * and removing them from the map.
     *
     * @param userId the {@code UUID} of the user whose notifications are to be dismissed
     */
    @Override
    public void dismissAllNotificationFromUser(UUID userId) {
        List<Notification> userNotifications = getNotifications(userId);
        if (userNotifications != null) {
            userNotifications.clear(); // Clear all notifications for the user
            notifications.remove(userId); // Remove the user from the map if they have no notifications left
        }
    }

    /**
     * Retrieves all notifications associated with a specific user.
     *
     * @param userId the {@code UUID} of the user whose notifications are to be retrieved
     * @return a {@code List} of {@code Notification} objects for the specified user, or an empty list if none exist
     */
    @Override
    public List<Notification> getNotifications(UUID userId) {
        if (userId == null) {
            return List.of(); // Return an empty list if userId is null
        }
        return notifications.getOrDefault(userId, List.of()); // Return notifications or an empty list
    }
}
