package com.group6.hms.app;

import com.group6.hms.AppointmentsStorageProvider;
import com.group6.hms.app.models.Appointment;
import com.group6.hms.app.models.AppointmentStatus;
import com.group6.hms.framework.auth.StorageProvider;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AppointmentManager {
    private static final File appointmentsFile = new File("appointments.ser");
    private StorageProvider<Appointment> appointmentStorageProvider = new AppointmentsStorageProvider(appointmentsFile);

    public ArrayList<Appointment> getAllAppointments() {
        return (ArrayList<Appointment>) appointmentStorageProvider.getItems();
    }

    // for the patient to get their scheduled appointments
    public ArrayList<Appointment> getAppointmentsByPatient(UUID patientId) {
        List<Appointment> aptList = appointmentStorageProvider.getItems().stream().filter(apt -> apt.getPatientId().equals(patientId)).toList();
        return new ArrayList<>(aptList);
    }

    // for the patient to schedule an appointment
    public void scheduleAppointment(UUID patientId, UUID doctorId, LocalDateTime dateTime) {
        boolean isDoctorFree = checkIsDoctorFree(doctorId, dateTime);
        if (!isDoctorFree) {
            System.out.println("The doctor is already booked for this time slot!");
            return;
        }

        // schedule the appointment
        Appointment appt = new Appointment(patientId, doctorId, AppointmentStatus.CONFIRMED, dateTime);
        appointmentStorageProvider.addNewItem(appt);
        appointmentStorageProvider.saveToFile(appointmentsFile);
        System.out.println("Successfully scheduled appointment");
    }

    // for the patient to change the date/time of their appointment (if possible)
    public void rescheduleAppointment(Appointment appointment, LocalDateTime newDateTime) {
        boolean isDoctorFree = checkIsDoctorFree(appointment.getDoctorId(), newDateTime);
        if (!isDoctorFree) {
            System.out.println("The doctor is already booked for this time slot!");
            return;
        }

        appointmentStorageProvider.getItems().stream().filter(a -> a.getAppointmentId().equals(appointment.getAppointmentId())).findFirst().ifPresent(a -> a.setDateTime(newDateTime));
        appointmentStorageProvider.saveToFile(appointmentsFile);
        System.out.println("Successfully changed appointment date and time");
    }

    // for the patient to cancel their appointment
    public void cancelAppointment(Appointment appointment) {
        appointmentStorageProvider.removeItem(appointment);
        appointmentStorageProvider.saveToFile(appointmentsFile);
        System.out.println("Successfully cancelled appointment");
    }

    private boolean checkIsDoctorFree(UUID doctorId, LocalDateTime dateTime) {
        // check if doctor is free during the timeslot
        List<Appointment> filteredAppointments =appointmentStorageProvider.getItems().stream()
                .filter(apt -> apt.getDoctorId().equals(doctorId) && apt.getDateTime().equals(dateTime)).toList();
        return filteredAppointments.isEmpty();
    }

}
