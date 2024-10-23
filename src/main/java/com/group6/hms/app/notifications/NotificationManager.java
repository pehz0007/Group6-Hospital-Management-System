package com.group6.hms.app.notifications;

import java.util.*;

public class NotificationManager implements NotificationInterface {

    private Map<UUID, List<Notification>> notifications;

    public NotificationManager() {
        this.notifications = new HashMap<>();
    }

    @Override
    public void createNotification(Notification notification) {
        if (notification != null) {
            UUID userId = notification.getUserID();
            notifications.putIfAbsent(userId, new ArrayList<>()); // Initialize the list if not present
            notifications.get(userId).add(notification); // Add the notification to the user's list
        }
    }

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

    @Override
    public void dismissAllNotificationFromUser(UUID userId) {
        List<Notification> userNotifications = getNotifications(userId);
        if (userNotifications != null) {
            userNotifications.clear(); // Clear all notifications for the user
            notifications.remove(userId); // Remove the user from the map if they have no notifications left
        }
    }

    @Override
    public List<Notification> getNotifications(UUID userId) {
        if (userId == null) {
            return List.of(); // Return an empty list if userId is null
        }
        return notifications.getOrDefault(userId, List.of()); // Return notifications or an empty list
    }
}
