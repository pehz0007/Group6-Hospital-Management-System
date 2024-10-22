package com.group6.hms.app.managers;

import com.group6.hms.app.storage.SerializationStorageProvider;
import com.group6.hms.app.storage.StorageProvider;
import com.group6.hms.app.models.Appointment;
import com.group6.hms.app.models.AppointmentStatus;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.roles.Patient;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentManager {
    private static final File appointmentsFile = new File("appointments.ser");
    private final StorageProvider<Appointment> appointmentStorageProvider = new SerializationStorageProvider<>();

    public AppointmentManager() {
        appointmentStorageProvider.loadFromFile(appointmentsFile);
    }
    public ArrayList<Appointment> getAllAppointments() {
        return (ArrayList<Appointment>) appointmentStorageProvider.getItems();
    }

    // for the patient to get their scheduled appointments
    public ArrayList<Appointment> getAppointmentsByPatient(Patient patient) {
        List<Appointment> aptList = appointmentStorageProvider.getItems().stream().filter(apt -> apt.getPatient().getUserId().equals(patient.getUserId())).toList();
        return new ArrayList<>(aptList);
    }

    public ArrayList<Appointment> getAppointmentsByDoctorAndStatus(Doctor doctor, AppointmentStatus status) {
        List<Appointment> aptList = appointmentStorageProvider.getItems().stream().filter(apt -> apt.getDoctor().getUserId().equals(doctor.getUserId()) && apt.getStatus() == AppointmentStatus.CONFIRMED).toList();
        return new ArrayList<>(aptList);
    }

    public void acceptAppointmentRequest(Appointment appointment) {
        appointmentStorageProvider.getItems().stream().filter(a -> a.getAppointmentId().equals(appointment.getAppointmentId())).findFirst().ifPresent(a -> {
            a.setStatus(AppointmentStatus.CONFIRMED);
        });

        // update file
        appointmentStorageProvider.saveToFile(appointmentsFile);
        System.out.println("Appointment accepted!");
    }

    public void declineAppointmentRequest(Appointment appointment) {
        appointmentStorageProvider.getItems().stream().filter(a -> a.getAppointmentId().equals(appointment.getAppointmentId())).findFirst().ifPresent(a -> {
            a.setStatus(AppointmentStatus.CANCELLED);
        });

        // TODO: UPDATE AVAILABILITY

        // update file
        appointmentStorageProvider.saveToFile(appointmentsFile);
        System.out.println("Appointment declined!!");

    }

    // for the patient to schedule an appointment
    public void scheduleAppointment(Patient patient, Doctor doctor, LocalDateTime dateTime) {
        boolean isDoctorFree = checkIsDoctorFree(doctor, dateTime);
        if (!isDoctorFree) {
            System.out.println("The doctor is already booked for this time slot!");
            return;
        }

        // schedule the appointment
        Appointment appt = new Appointment(patient, doctor, AppointmentStatus.REQUESTED, dateTime);

        // TODO: UPDATE AVAILABILITY

        // update file
        appointmentStorageProvider.addNewItem(appt);
        appointmentStorageProvider.saveToFile(appointmentsFile);
        System.out.println("Successfully requested for an appointment. Do come back to check if the doctor has confirmed the appointment.");
    }

    // for the patient to change the date/time of their appointment (if possible)
    public void rescheduleAppointment(Appointment appointment, LocalDateTime newDateTime) {
        boolean isDoctorFree = checkIsDoctorFree(appointment.getDoctor(), newDateTime);
        if (!isDoctorFree) {
            System.out.println("The doctor is already booked for this time slot!");
            return;
        }

        appointmentStorageProvider.getItems().stream().filter(a -> a.getAppointmentId().equals(appointment.getAppointmentId())).findFirst().ifPresent(a -> {
            a.setDateTime(newDateTime);
            a.setStatus(AppointmentStatus.REQUESTED);
        });

        // TODO: UPDATE AVAILABILITY

        // update file
        appointmentStorageProvider.saveToFile(appointmentsFile);
        System.out.println("Successfully changed appointment date and time. The doctor will have to confirm your appointment again.");
    }

    // for the patient to cancel their appointment
    public void cancelAppointment(Appointment appointment) {
//        appointmentStorageProvider.removeItem(appointment);
        appointmentStorageProvider.getItems().stream().filter(a -> a.getAppointmentId().equals(appointment.getAppointmentId())).findFirst().ifPresent(a -> {
            a.setStatus(AppointmentStatus.CANCELLED);
        });
        // TODO: UPDATE AVAILABILITY

        // update file
        appointmentStorageProvider.saveToFile(appointmentsFile);
        System.out.println("Successfully cancelled appointment");
    }

    private boolean checkIsDoctorFree(Doctor doctor, LocalDateTime dateTime) {
        // check if doctor is free during the timeslot
        List<Appointment> filteredAppointments = appointmentStorageProvider.getItems().stream()
                .filter(apt -> apt.getDoctor().getUserId().equals(doctor.getUserId()) && apt.getDateTime().equals(dateTime)).toList();
        return filteredAppointments.isEmpty();
    }

}
