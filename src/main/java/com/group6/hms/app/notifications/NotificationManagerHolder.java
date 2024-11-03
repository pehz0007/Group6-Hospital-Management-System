package com.group6.hms.app.notifications;

/**
 * The {@code NotificationManagerHolder} class implements the Singleton pattern to provide
 * a single, global instance of {@link NotificationManager}. This ensures that all parts
 * of the application access the same {@code NotificationManager} instance.
 *
 * <p>This class uses lazy initialization with double-checked locking to ensure thread safety
 * in a multithreaded environment.</p>
 */
public class NotificationManagerHolder {

    private static volatile NotificationManager instance;

    /**
     * Private constructor to prevent instantiation of the {@code NotificationManagerHolder} class.
     */
    private NotificationManagerHolder() {
    }

    /**
     * Returns the singleton instance of {@code NotificationManager}. If the instance is {@code null},
     * it initializes it in a thread-safe manner using double-checked locking.
     *
     * @return the singleton instance of {@code NotificationManager}
     */
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