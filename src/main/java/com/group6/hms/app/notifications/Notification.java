package com.group6.hms.app.notifications;

import com.group6.hms.framework.screens.calendar.DateTimeRenderer;
import com.group6.hms.framework.screens.pagination.HeaderField;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The {@code Notification} class represents a notification within the system.
 * It includes a title, message, timestamp, and user ID to identify the intended recipient.
 */
public class Notification {
    private String title;
    private String message;
    @HeaderField(show = true, renderer = DateTimeRenderer.class)
    private LocalDateTime timestamp;
    @HeaderField(show = false)
    private UUID userID;

    /**
     * Constructs a new {@code Notification} with the specified title, message,
     * and user ID. The timestamp is set to the current date and time.
     *
     * @param title   the title of the notification
     * @param message the message content of the notification
     * @param userID  the unique identifier of the user receiving the notification
     */
    public Notification(String title, String message, UUID userID) {
        this(title, message, LocalDateTime.now(), userID);
    }

    /**
     * Constructs a new {@code Notification} with the specified title, message, timestamp, and user ID.
     *
     * @param title     the title of the notification
     * @param message   the message content of the notification
     * @param timestamp the time when the notification was created
     * @param userID    the unique identifier of the user receiving the notification
     */
    public Notification(String title, String message, LocalDateTime timestamp, UUID userID) {
        this.title = title;
        this.message = message;
        this.timestamp = timestamp;
        this.userID = userID;
    }

    /**
     * Returns the title of the notification.
     *
     * @return the notification title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the message content of the notification.
     *
     * @return the notification message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the timestamp of the notification.
     *
     * @return the {@code LocalDateTime} when the notification was created
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the user ID associated with the notification.
     *
     * @return the {@code UUID} of the user receiving the notification
     */
    public UUID getUserID() {
        return userID;
    }
}
