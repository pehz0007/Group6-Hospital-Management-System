package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.auth.LoginManagerHolder;
import com.group6.hms.app.managers.AppointmentManager;
import com.group6.hms.app.managers.AvailabilityManager;
import com.group6.hms.app.models.Appointment;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.storage.SerializationStorageProvider;
import com.group6.hms.app.storage.StorageProvider;
import com.group6.hms.framework.screens.pagination.HeaderField;
import com.group6.hms.framework.screens.pagination.PaginationTableScreen;

import java.io.File;
import java.util.List;

/**
 * The {@code AcceptOrDeclineScreen} screen for a doctor to accept or decline appointments.
 * This screen displays a list of pending appointments and allows the doctor
 * to interactively accept or decline each appointment.
 *
 * * <p>This class extends {@link PaginationTableScreen} to provide a paginated view of appointments</p>
 */
public class AcceptOrDeclineScreen extends PaginationTableScreen<Appointment> {

    private static final File appointmentsFile = new File("data/appointments.ser");
    private final StorageProvider<Appointment> appointmentStorageProvider = new SerializationStorageProvider<>();
    List<Appointment> appointments;

    AppointmentManager appointmentManager = new AppointmentManager();
    AvailabilityManager availabilityManager = new AvailabilityManager();

    /**
     * Constructs an AcceptOrDeclineScreen with the specified list of appointments.
     *
     * @param items the list of appointments to display on the screen
     */
    public AcceptOrDeclineScreen(List<Appointment> items) {
        super("Accept Pending Appointments", items);
        this.appointments = items;

        addOption(1, "Accept Appointment");
        addOption(2, "Decline Appointment");
    }

    @Override
    public void onStart() {
        super.onStart();
        println("You got " + appointments.size() + " appointment(s)!");
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        println("You got " + appointments.size() + " appointment(s)!");
    }

    @Override
    protected void handleOption(int optionId) {
        super.handleOption(optionId);
        if (optionId == 1) {
            acceptAppointment(appointments);
        } else if (optionId == 2) {
            declineAppointment(appointments);
        }
    }

    /**
     * Accepts a selected appointment from the list.
     * Prompts the user to confirm the acceptance of the appointment.
     *
     * @param items the list of appointments to process
     */
    protected void acceptAppointment(List<Appointment> items) {
        for (Appointment appointment : items) {
            print("=".repeat(30) + "\n");
            println("Appointment Date & Timing: " + appointment.getDate() + " " + appointment.getStartTime());
            println("Patient Name: " + appointment.getPatient().getName());
            print("\u001B[37m Is this the appointment you want to accept? (Y/N): ");
            String result = readString();
            if (result.equalsIgnoreCase("y")) {
                appointmentManager.acceptAppointmentRequest(appointment);
                items.remove(appointment);
                // appointmentStorageProvider.saveToFile(appointmentsFile);
                return;
            }
        }
        println("\u001B[31m Invalid input. Please try again.");
    }

    /**
     * Declines a selected appointment from the list.
     * Prompts the user to confirm the decline of the appointment.
     *
     * @param appointment the list of appointments to process
     */
    protected void declineAppointment(List<Appointment> appointment) {
        for (Appointment appointments : appointment) {
            print("=".repeat(30) + "\n");
            println("Appointment Date & Timing: " + appointments.getDate() + " " + appointments.getStartTime());
            println("Patient Name: " + appointments.getPatient().getName());
            print("\u001B[37m Is this the appointment you want to decline? (Y/N): ");
            String result = readString();
            if (result.equalsIgnoreCase("n")) {
                appointmentManager.declineAppointmentRequest(appointments);
                appointment.remove(appointments);
                // appointmentStorageProvider.saveToFile(appointmentsFile);
                return;
            }
        }
        println("\u001B[31m Invalid input. Please try again.");
    }
}
