package com.group6.hms.app.notifications;

import java.util.List;
import java.util.UUID;

public interface NotificationInterface {

    void createNotification(Notification notification);
    void dismissNotification(Notification notification);
    List<Notification> getNotifications(UUID userId);

}
