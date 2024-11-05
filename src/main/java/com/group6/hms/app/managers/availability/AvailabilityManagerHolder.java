package com.group6.hms.app.managers.availability;

public class AvailabilityManagerHolder {

    private static volatile AvailabilityManager instance;

    // Private constructor to prevent instantiation
    private AvailabilityManagerHolder() {
    }

    public static AvailabilityManager getAvailabilityManager() {
        if (instance == null) {
            synchronized (AvailabilityManagerHolder.class) {
                if (instance == null) {
                    instance = new AvailabilityManager();
                }
            }
        }
        return instance;
    }

}
