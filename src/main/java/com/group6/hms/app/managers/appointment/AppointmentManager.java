package com.group6.hms.app.managers;

import com.group6.hms.app.managers.appointment.models.Appointment;
import com.group6.hms.app.managers.appointment.models.AppointmentOutcomeRecord;
import com.group6.hms.app.managers.appointment.models.AppointmentStatus;
import com.group6.hms.app.managers.availability.AvailabilityManager;
import com.group6.hms.app.managers.availability.models.Availability;
import com.group6.hms.app.managers.availability.AvailabilityManagerHolder;
import com.group6.hms.app.managers.availability.models.AvailabilityStatus;
import com.group6.hms.app.managers.inventory.models.MedicationStatus;
import com.group6.hms.app.storage.SerializationStorageProvider;
import com.group6.hms.app.storage.StorageProvider;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.roles.Patient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.UUID;

/**
 * The {@code AppointmentManager} class manages appointment scheduling, updating, and cancellation
 * within the system. It handles operations related to patient appointments, including appointment
 * requests, confirmations, rescheduling, and outcomes.
 */
public class AppointmentManager {
    private static final File appointmentsFile = new File("data/appointments.ser");
    private static final File appointmentOutcomesFile = new File("data/appointment_outcomes.ser");
    private final StorageProvider<Appointment> appointmentStorageProvider = new SerializationStorageProvider<>();
    private final StorageProvider<AppointmentOutcomeRecord> appointmentOutcomeStorageProvider = new SerializationStorageProvider<>();


    /**
     * Initializes the {@code AppointmentManager} and loads existing appointments and appointment
     * outcome records from storage files. The file I/O for the appointment and appointment outcomes are managed by separate {@link SerializationStorageProvider} instances.
     */
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

    /**
     * Retrieves all appointments from appointmentStorageProvider.
     *
     * @return a {@code List} of all {@code Appointment} objects
     */
    public List<Appointment> getAllAppointments() {
        return appointmentStorageProvider.getItems().stream().toList();
    }

    /**
     * Retrieves all appointment outcome records from appointmentOutcomeStorageProvider.
     *
     * @return a {@code List} of all {@code AppointmentOutcomeRecord} objects
     */
    public List<AppointmentOutcomeRecord> getAllAppointmentOutcomeRecords() {
        Collection<AppointmentOutcomeRecord> records = appointmentOutcomeStorageProvider.getItems(); // Get items as a Collection
        return new ArrayList<>(records); // Convert Collection to List
    }

    /**
     * Retrieves appointments from appointmentStorageProvider for a specific patient.
     *
     * @param patient the {@code Patient} whose appointments are to be retrieved
     * @return an {@code ArrayList} of {@code Appointment} objects for the specified patient
     */
    public ArrayList<Appointment> getAppointmentsByPatient(Patient patient) {
        List<Appointment> aptList = appointmentStorageProvider.getItems().stream().filter(apt -> apt.getPatient().getSystemUserId().equals(patient.getSystemUserId())).toList();
        return new ArrayList<>(aptList);
    }

    /**
     * Retrieves appointments from appointmentStorageProvider by their unique identifier.
     *
     * @param uuid the unique identifier of the appointment
     * @return an {@code ArrayList} of {@code Appointment} objects matching the specified UUID
     */
    public ArrayList<Appointment> getAppointmentsByUUID(UUID uuid) {
        List<Appointment> aptList = appointmentStorageProvider.getItems().stream().filter(apt -> apt.getAppointmentId().equals(uuid)).toList();
        return new ArrayList<>(aptList);
    }

    /**
     * Retrieves appointments from appointmentStorageProvider by their unique identifier.
     *
     * @param id the unique identifier of the appointment
     * @return a {@code Appointment} object matching the specified UUID
     */
    public Appointment getAppointmentByID(String id) {
        return appointmentStorageProvider.getItems().stream()
                .filter(apt -> apt.getAppointmentId().toString().equals(id))
                .findFirst().orElse(null);
    }

    /**
     * Retrieves appointments from appointmentStorageProvider for a specific doctor.

     * @param doctor the {@code Doctor} whose appointments are to be retrieved
     * @return an {@code ArrayList} of {@code Appointment} objects for the specified doctor
     */
    public ArrayList<Appointment> getAppointmentsByDoctor(Doctor doctor) {
        List<Appointment> aptList = appointmentStorageProvider.getItems().stream().filter(apt -> apt.getDoctor().getSystemUserId().equals(doctor.getSystemUserId())).toList();
        return new ArrayList<>(aptList);
    }

    /**
     * Retrieves appointments from appointmentStorageProvider for a specific doctor with a specified status.
     *
     * @param doctor the {@code Doctor} whose appointments are to be retrieved
     * @param status the status of the appointments to be retrieved
     * @return an {@code ArrayList} of {@code Appointment} objects with the specified status
     */
    public ArrayList<Appointment> getAppointmentsByDoctorAndStatus(Doctor doctor, AppointmentStatus status) {
        List<Appointment> aptList = appointmentStorageProvider.getItems().stream().filter(apt -> apt.getDoctor().getSystemUserId().equals(doctor.getSystemUserId()) && apt.getStatus() == status).toList();
        return new ArrayList<>(aptList);
    }

    /**
     * Confirms an appointment request and updates its status to confirmed.
     * Saves the updated appointments list to the serializable appointments file
     *
     * @param appointment the {@code Appointment} to be confirmed
     */
    public void acceptAppointmentRequest(Appointment appointment) {
        appointmentStorageProvider.getItems().stream().filter(a -> a.getAppointmentId().equals(appointment.getAppointmentId())).findFirst().ifPresent(a -> {
            a.setStatus(AppointmentStatus.CONFIRMED);
        });

        // update file
        appointmentStorageProvider.saveToFile(appointmentsFile);
        System.out.println("Appointment accepted!");
    }

    /**
     * Declines an appointment request and updates its status to cancelled.
     * Saves the updated appointments list to the serializable appointments file
     *
     * @param appointment the {@code Appointment} to be declined
     */
    public void declineAppointmentRequest(Appointment appointment) {
//        appointmentStorageProvider.getItems().stream().filter(a -> a.getAppointmentId().equals(appointment.getAppointmentId())).findFirst().ifPresent(a -> {
//            a.setStatus(AppointmentStatus.CANCELLED);
//        });

        appointment.setStatus(AppointmentStatus.CANCELLED);
        // UPDATE AVAILABILITY
        Availability availability = AvailabilityManagerHolder.getAvailabilityManager().getAvailabilityById(appointment.getAppointmentId());
        availability.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);

        // update file
        appointmentStorageProvider.saveToFile(appointmentsFile);
        System.out.println("Appointment declined!!");

    }

    /**
     * Schedules a new appointment request for a patient with a specified availability.
     * Saves the new appointment to the appointments list managed by appointmentStorageProvider and saves the updated list to the serializable appointments file
     *
     * @param patient      the {@code Patient} requesting the appointment
     * @param availability the {@code Availability} details for the appointment
     */
    public void scheduleAppointment(Patient patient, Availability availability) {
        // schedule the appointment
        Appointment appt = new Appointment(patient, availability.getDoctor(), AppointmentStatus.REQUESTED, availability);
        // UPDATE AVAILABILITY
        AvailabilityManagerHolder.getAvailabilityManager().updateAvailability(availability, AvailabilityStatus.BOOKED);

        // update file
        appointmentStorageProvider.addNewItem(appt);
        appointmentStorageProvider.saveToFile(appointmentsFile);
        //System.out.println("Successfully requested for an appointment. Do come back to check if the doctor has confirmed the appointment.");
    }

    /**
     * Reschedules an existing appointment to a new date and time based on the given newAvailability.
     * Saves the updated appointments list to the serializable appointments file
     *
     * @param appointment  the {@code Appointment} to be rescheduled
     * @param newAvailability the new {@code Availability} for the appointment
     */
    public void rescheduleAppointment(Appointment appointment, Availability newAvailability) {
//        appointmentStorageProvider.getItems().stream().filter(a -> a.getAppointmentId().equals(appointment.getAppointmentId())).findFirst().ifPresent(a -> {
//            appointment.setDate(newAvailability.getAvailableDate());
//            appointment.setStartTime(newAvailability.getAvailableStartTime());
//            appointment.setEndTime(newAvailability.getAvailableEndTime());
//        });

        AvailabilityManager availabilityManager = AvailabilityManagerHolder.getAvailabilityManager();

        Availability previousAvailability = availabilityManager.getAvailabilityById(appointment.getAvailabilityId());
        availabilityManager.updateAvailability(previousAvailability, AvailabilityStatus.AVAILABLE);

        appointment.setAvailabilityId(newAvailability.getAvailabilityId());
        appointment.setDate(newAvailability.getAvailableDate());
        appointment.setStartTime(newAvailability.getAvailableStartTime());
        appointment.setEndTime(newAvailability.getAvailableEndTime());

        // UPDATE AVAILABILITY
        availabilityManager.updateAvailability(newAvailability, AvailabilityStatus.BOOKED);

        // update file
        appointmentStorageProvider.saveToFile(appointmentsFile);
        //System.out.println("Successfully changed appointment date
//        appointmentStorageProvider.addNewItem(appt);
    }

    /**
     * Cancels an existing appointment and updates its status to canceled.
     * Saves the updated appointments list to the serializable appointments file
     *
     * @param appointment the {@code Appointment} to be canceled
     */
    public void cancelAppointment(Appointment appointment) {
//        appointmentStorageProvider.getItems().stream().filter(a -> a.getAppointmentId().equals(appointment.getAppointmentId())).findFirst().ifPresent(a -> {
//            a.setStatus(AppointmentStatus.CANCELLED);
//        });
        appointment.setStatus(AppointmentStatus.CANCELLED);
        // UPDATE AVAILABILITY
        AvailabilityManager availabilityManager = AvailabilityManagerHolder.getAvailabilityManager();
        Availability availability = availabilityManager.getAvailabilityById(appointment.getAvailabilityId());
        availabilityManager.updateAvailability(availability, AvailabilityStatus.AVAILABLE);

        // update file
        appointmentStorageProvider.saveToFile(appointmentsFile);
        //System.out.println("Successfully cancelled appointment");
    }

    /**
     * Marks an appointment as completed and associates an outcome record with it.
     * Saves the updated appointments list to the serializable appointments file
     * Adds a new record to the appointment outcomes list and saves the updated appointment outcomes list to the serializable appointment outcomes file
     *
     * @param appointment            the {@code Appointment} to be completed
     * @param appointmentOutcomeRecord the {@code AppointmentOutcomeRecord} detailing the appointment outcome
     */
    public void completeAppointment(Appointment appointment, AppointmentOutcomeRecord appointmentOutcomeRecord) {
        appointmentStorageProvider.getItems().stream().filter(a -> a.getAppointmentId().equals(appointment.getAppointmentId())).findFirst().ifPresent(a -> {
            a.setStatus(AppointmentStatus.COMPLETED);
            a.setAppointmentOutcomeRecordId(appointmentOutcomeRecord.getRecordId());
        });
        appointmentStorageProvider.saveToFile(appointmentsFile);
        appointmentOutcomeStorageProvider.addNewItem(appointmentOutcomeRecord);
        appointmentOutcomeStorageProvider.saveToFile(appointmentOutcomesFile);
    }

    /**
     * Retrieves outcome records for a specific patient.
     *
     * @param patient the {@code Patient} whose outcome records are to be retrieved
     * @return a {@code List} of {@code AppointmentOutcomeRecord} objects for the specified patient
     */
    public List<AppointmentOutcomeRecord> getAppointmentOutcomeRecordsByPatient(Patient patient) {
        return appointmentOutcomeStorageProvider.getItems().stream().filter(outcome ->outcome.getPatientId().equals(patient.getSystemUserId())).toList();

    }

    /**
     * Retrieves an appointment outcome record by its appointment UUID.
     * This method allows doctors to access the outcome record associated with a specific appointment.
     *
     * @param appointmentId the UUID of the appointment outcome record to retrieve
     * @return the {@code AppointmentOutcomeRecord} matching the specified UUID, or {@code null} if not found
     */
    public AppointmentOutcomeRecord getAppointmentOutcomeRecordsByUUID(UUID appointmentId) {
        for (AppointmentOutcomeRecord outcome : appointmentOutcomeStorageProvider.getItems()) {
            if (outcome.getRecordId().equals(appointmentId)) {
                return outcome; // Return the found record
            }
        }
        return null;
    }

    /**
     * Retrieves appointment outcome records with a specific medication status.
     * This method allows pharmacists to find and fulfill medication orders based on their status.
     *
     * @param medicationStatus the {@code MedicationStatus} to filter outcome records by
     * @return a {@code List} of {@code AppointmentOutcomeRecord} objects with the specified medication status
     */
    public List<AppointmentOutcomeRecord> getAppointmentOutcomeRecordsByStatus(MedicationStatus medicationStatus) {
        return appointmentOutcomeStorageProvider.getItems().stream().filter(outcome ->outcome.getMedicationStatus().equals(medicationStatus)).toList();
    }

    /**
     * Updates the medication status of a specified appointment outcome record.
     * This method is used by pharmacists to update the status of medication orders.
     *
     * @param record the {@code AppointmentOutcomeRecord} whose medication status is to be updated
     * @param status the new {@code MedicationStatus} to set
     */
    public void updateAppointmentOutcomeRecordMedicationStatus(AppointmentOutcomeRecord record, MedicationStatus status) {
        appointmentOutcomeStorageProvider.getItems().stream().filter(rec -> rec.getRecordId().equals(record.getRecordId())).findFirst().ifPresent(rec -> rec.setMedicationStatus(status));
        appointmentOutcomeStorageProvider.saveToFile(appointmentOutcomesFile);
    }


}
