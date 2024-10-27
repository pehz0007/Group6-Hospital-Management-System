package com.group6.hms.app.managers;

import com.group6.hms.app.MedicationStatus;
import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.auth.LoginManagerHolder;
import com.group6.hms.app.models.*;
import com.group6.hms.app.storage.SerializationStorageProvider;
import com.group6.hms.app.storage.StorageProvider;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.roles.Patient;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.UUID;

public class AppointmentManager {
    private static final File appointmentsFile = new File("data/appointments.ser");
    private static final File appointmentOutcomesFile = new File("data/appointment_outcomes.ser");
    private final StorageProvider<Appointment> appointmentStorageProvider = new SerializationStorageProvider<>();
    private final StorageProvider<AppointmentOutcomeRecord> appointmentOutcomeStorageProvider = new SerializationStorageProvider<>();

    public static void main(String[] args) {
        LoginManager loginManager = LoginManagerHolder.getLoginManager();
        AppointmentManager appointmentManager = new AppointmentManager();
        AvailabilityManager availabilityManager = new AvailabilityManager();
        loginManager.loadUsersFromFile();
        Doctor doctor = (Doctor) loginManager.findUser("D0011");
        Patient patient = (Patient) loginManager.findUser("P1011");
        LocalTime timeNow = LocalTime.now();
        Availability avail = new Availability(doctor, LocalDate.now(), timeNow, timeNow.plusHours(1));
        availabilityManager.addAvailability(avail);
        appointmentManager.scheduleAppointment(patient, avail);

        Appointment appt = appointmentManager.getAllAppointments().getFirst();
        appointmentManager.acceptAppointmentRequest(appt);
        ArrayList<Medication> medications = new ArrayList<>();
        medications.add(new Medication(UUID.randomUUID(), "Panadol"));
        medications.add(new Medication(UUID.randomUUID(), "Cough Syrup"));
        medications.add(new Medication(UUID.randomUUID(), "Flu Medicine"));
        AppointmentOutcomeRecord record = new AppointmentOutcomeRecord(doctor.getSystemUserId(), patient.getSystemUserId(), appt.getDate(), AppointmentService.CONSULT, medications, "high fever", MedicationStatus.PENDING);
        appointmentManager.completeAppointment(appt,record);
        List<AppointmentOutcomeRecord> records = appointmentManager.getAppointmentOutcomeRecordsByStatus(MedicationStatus.PENDING);


        //CARE PROVIDER
        CareProvider careProvider = new CareProvider();
        var p = careProvider.getPatientIDsUnderDoctorCare(doctor);
        careProvider.addPatientToDoctorCare(patient, doctor);

    }
    public AppointmentManager() {
        if (!appointmentsFile.exists()) {
            appointmentStorageProvider.saveToFile(appointmentsFile);
        }
        if (!appointmentOutcomesFile.exists()) {
            appointmentOutcomeStorageProvider.saveToFile(appointmentOutcomesFile);
        }
        appointmentStorageProvider.loadFromFile(appointmentsFile);
        appointmentOutcomeStorageProvider.loadFromFile(appointmentOutcomesFile);
    }
    public List<Appointment> getAllAppointments() {
        return (List<Appointment>) appointmentStorageProvider.getItems();
    }

    // New method to get all appointment outcome records
    public List<AppointmentOutcomeRecord> getAllAppointmentOutcomeRecords() {
        Collection<AppointmentOutcomeRecord> records = appointmentOutcomeStorageProvider.getItems(); // Get items as a Collection
        return new ArrayList<>(records); // Convert Collection to List
    }

    // for the patient to get their scheduled appointments
    public ArrayList<Appointment> getAppointmentsByPatient(Patient patient) {
        List<Appointment> aptList = appointmentStorageProvider.getItems().stream().filter(apt -> apt.getPatient().getSystemUserId().equals(patient.getSystemUserId())).toList();
        return new ArrayList<>(aptList);
    }

    public ArrayList<Appointment> getAppointmentsByDoctorAndStatus(Doctor doctor, AppointmentStatus status) {
        List<Appointment> aptList = appointmentStorageProvider.getItems().stream().filter(apt -> apt.getDoctor().getUserId().equals(doctor.getUserId()) && apt.getStatus() == status).toList();
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
    public void scheduleAppointment(Patient patient, Availability availability) {
        // schedule the appointment
        Appointment appt = new Appointment(patient, availability.getDoctor(), AppointmentStatus.REQUESTED, availability.getAvailableDate(), availability.getAvailableStartTime(), availability.getAvailableEndTime());
        // TODO: UPDATE AVAILABILITY

        // update file
        appointmentStorageProvider.addNewItem(appt);
        appointmentStorageProvider.saveToFile(appointmentsFile);
        System.out.println("Successfully requested for an appointment. Do come back to check if the doctor has confirmed the appointment.");
    }

    // for the patient to change the date/time of their appointment (if possible)
    public void rescheduleAppointment(Appointment appointment, Availability availability) {
        appointmentStorageProvider.getItems().stream().filter(a -> a.getAppointmentId().equals(appointment.getAppointmentId())).findFirst().ifPresent(a -> {
            appointment.setDate(availability.getAvailableDate());
            appointment.setStartTime(availability.getAvailableStartTime());
            appointment.setEndTime(availability.getAvailableEndTime());
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

    // for doctor to set the appointment as completed and set the outcome record
    public void completeAppointment(Appointment appointment, AppointmentOutcomeRecord appointmentOutcomeRecord) {
        appointmentStorageProvider.getItems().stream().filter(a -> a.getAppointmentId().equals(appointment.getAppointmentId())).findFirst().ifPresent(a -> {
            a.setStatus(AppointmentStatus.COMPLETED);
        });
        appointment.setAppointmentOutcomeRecordId(appointmentOutcomeRecord.getRecordId());
        appointmentOutcomeStorageProvider.addNewItem(appointmentOutcomeRecord);
        appointmentOutcomeStorageProvider.saveToFile(appointmentOutcomesFile);
    }

    // for patient to view their outcomes
    public List<AppointmentOutcomeRecord> getAppointmentOutcomeRecordsByPatient(Patient patient) {
        return appointmentOutcomeStorageProvider.getItems().stream().filter(outcome ->outcome.getPatientId().equals(patient.getSystemUserId())).toList();

    }

    // for Pharmacist to fulfil medication order
    public List<AppointmentOutcomeRecord> getAppointmentOutcomeRecordsByStatus(MedicationStatus medicationStatus) {
        return appointmentOutcomeStorageProvider.getItems().stream().filter(outcome ->outcome.getMedicationStatus().equals(medicationStatus)).toList();
    }

    public void updateAppointmentOutcomeRecordMedicationStatus(AppointmentOutcomeRecord record, MedicationStatus status) {
        appointmentOutcomeStorageProvider.getItems().stream().filter(rec -> rec.getRecordId().equals(record.getRecordId())).findFirst().ifPresent(rec -> rec.setMedicationStatus(status));
        appointmentOutcomeStorageProvider.saveToFile(appointmentOutcomesFile);
    }


}
