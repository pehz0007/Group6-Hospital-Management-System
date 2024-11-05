package com.group6.hms.app.managers.appointment;

import com.group6.hms.app.managers.AppointmentManager;

public class AppointmentManagerHolder {

    private static volatile AppointmentManager instance;

    // Private constructor to prevent instantiation
    private AppointmentManagerHolder() {
    }

    public static AppointmentManager getAppointmentManager() {
        if (instance == null) {
            synchronized (AppointmentManagerHolder.class) {
                if (instance == null) {
                    instance = new AppointmentManager();
                }
            }
        }
        return instance;
    }

}
