package com.group6.hms.app.notifications;

import com.group6.hms.framework.screens.pagination.HeaderField;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notification {
    private String title;
    private String message;
    @HeaderField(show = false)
    private LocalDateTime timestamp;
    @HeaderField(show = false)
    private UUID userID;

    public Notification(String title, String message, UUID userID) {
        this(title, message, LocalDateTime.now(), userID);
    }

    public Notification(String title, String message, LocalDateTime timestamp, UUID userID) {
        this.title = title;
        this.message = message;
        this.timestamp = timestamp;
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public UUID getUserID() {
        return userID;
    }
}
