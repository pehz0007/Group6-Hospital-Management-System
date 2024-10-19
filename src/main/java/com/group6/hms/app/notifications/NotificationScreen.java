package com.group6.hms.app.notifications;

import com.group6.hms.app.auth.LoginManagerHolder;
import com.group6.hms.app.auth.User;
import com.group6.hms.framework.screens.pagination.PaginationTableScreen;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;

import java.util.ArrayList;
import java.util.List;

public class NotificationScreen extends PaginationTableScreen<Notification> {

    private static final int MAX_NOTIFICATIONS_DISPLAY_PER_PAGE = 3;
    // Maximum width for wrapping messages
    private static final int MAX_LINE_WIDTH = 30;

    /**
     * Constructor to initialize the NotificationScreen.
     *
     */
    public NotificationScreen() {
        super("Notifications", null, MAX_NOTIFICATIONS_DISPLAY_PER_PAGE);
        User user = LoginManagerHolder.getLoginManager().getCurrentlyLoggedInUser();
        setItems(NotificationManagerHolder.getNotificationManager().getNotifications(user.getUserId()));
    }

    @Override
    protected void handleOption(int optionId) {

    }

    @Override
    protected void printTable(List<Notification> sublist) {
        for (Notification notification : sublist) {
            displaySingleNotification(notification);
        }
    }



    private void displaySingleNotification(Notification notification) {
        PrintTableUtils.printItemAsTable(consoleInterface, notification);

    }

    /**
     * Helper method to wrap text to the given width.
     *
     * @param text The text to be wrapped.
     * @param width The maximum width of each line.
     * @return A list of strings where each string is a line of wrapped text.
     */
    private List<String> wrapText(String text, int width) {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            if (currentLine.length() + word.length() + 1 > width) { // +1 for space
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            } else {
                if (currentLine.length() > 0) {
                    currentLine.append(" ");
                }
                currentLine.append(word);
            }
        }

        // Add the last line
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines;
    }


}
