package com.group6.hms.app.notifications;

public class NotificationManagerHolder {

    private static volatile NotificationManager instance;

    // Private constructor to prevent instantiation
    private NotificationManagerHolder() {
    }

    public static NotificationManager getNotificationManager() {
        if (instance == null) {
            synchronized (NotificationManagerHolder.class) {
                if (instance == null) {
                    instance = new NotificationManager();
                }
            }
        }
        return instance;
    }
}