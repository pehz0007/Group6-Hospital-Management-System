package com.group6.hms.app;

import com.group6.hms.AppointmentsStorageProvider;
import com.group6.hms.app.auth.StorageProvider;
import com.group6.hms.app.models.Appointment;

import java.io.File;
import java.util.ArrayList;

public class AppointmentManager {
    private static final File appointmentsFile = new File("appointments.ser");
    private StorageProvider<Appointment> appointmentStorageProvider = new AppointmentsStorageProvider(appointmentsFile);

    public ArrayList<Appointment> getAppointments() {
        return (ArrayList<Appointment>) appointmentStorageProvider.getItems();
    }

//    public ArrayList<Appointment> addAppointment(Appointment appt) {
//        appointmentStorageProvider.addNewItem(appt);
//        appointmentStorageProvider.saveToFile(appointmentsFile);
//    }


}
