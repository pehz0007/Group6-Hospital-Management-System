package com.group6.hms.app.notifications;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;

import java.util.List;
import java.util.UUID;

public class NotificationManager implements NotificationInterface{

    private ListMultimap<UUID, Notification> notifications;

    public NotificationManager() {
        this.notifications = MultimapBuilder.hashKeys().arrayListValues().build();
    }

    @Override
    public void createNotification(Notification notification) {
        if (notification != null) {
            notifications.put(notification.getUserID(), notification);
        }
    }

    @Override
    public void dismissNotification(Notification notification) {
        if (notification != null) {
            notifications.remove(notification.getUserID(), notification);
        }
    }

    @Override
    public List<Notification> getNotifications(UUID userId) {
        if (userId == null) {
            return List.of();
        }
        return notifications.get(userId);
    }
}
