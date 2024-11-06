package com.group6.hms.app.screens.patient;

import com.group6.hms.app.managers.appointment.AppointmentManager;
import com.group6.hms.app.managers.appointment.AppointmentManagerHolder;
import com.group6.hms.app.managers.appointment.models.Appointment;
import com.group6.hms.app.managers.availability.models.Availability;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.pagination.PaginationTableScreen;

import java.util.List;
import java.util.Optional;

/**
 * The {@code RescheduleAppointmentScreen} class displays a lists of patient's appointments,
 * allowing patient to select the appointment ID that want to reschedule and book.
 * It extends {@link PaginationTableScreen} to display the list in paginated view.
 *
 * <p>This screen allows the patient to choose an existing available appointment slot for rescheduling
 * and select which scheduled appointment the patient want to change.</p>
 */
public class RescheduleAppointmentScreen extends PaginationTableScreen<Appointment> {
    private Patient patient;
    private Availability availability;
    private List<Appointment> appointmentList;
    private AppointmentManager appointmentManager = AppointmentManagerHolder.getAppointmentManager();

    private final int RESCHEDULE_APPOINTMENT = 3;

    /**
     * Constructs a {@code RescheduleAppointmentScreen} with the given list of appointment, available slots for rescheduling,
     * and the currently logged-in patient.
     *
     * @param appointmentList The list of appointments for the patient.
     * @param availability The available slots for patient to reschedule.
     * @param patient The patient whose appointment are being managed.
     */
    public RescheduleAppointmentScreen(List<Appointment> appointmentList, Availability availability, Patient patient) {
        super("Reschedule Appointment", null);
        this.patient = patient;
        //this.appointmentList = appointmentList;
        this.availability = availability;
        updateAppointmentList();

        addOption(RESCHEDULE_APPOINTMENT, "Reschedule Appointment", ConsoleColor.CYAN);
    }

    /**
     * Update the list of appointments for the patient and set the list in pagination table.
     */
    private void updateAppointmentList(){
        this.appointmentList = appointmentManager.getAppointmentsByPatient(this.patient);
        setList(this.appointmentList);
    }

    /**
     * Handles the selection of an option by the user.
     * If the "Reschedule Appointment" options is selected, it called the {@link #rescheduleAppointment()} method.
     *
     * @param optionId The ID of the option selected by the user.
     */
    @Override
    protected void handleOption(int optionId) {
        super.handleOption(optionId);
        if(optionId == RESCHEDULE_APPOINTMENT){
            rescheduleAppointment();
        }
    }

    /**
     * Allows the patient to select an appointment to reschedule and updates it with the new availability.
     * If the appointment ID is valid, the appointment is rescheduled, otherwise, an error message is displayed.
     */
    private void rescheduleAppointment(){
        print("Select the appointment id to reschedule:");
        String selectedAppointmentId = readString();
        Optional<Appointment> appointmentOptional = findAppointmentById(selectedAppointmentId);

        if (appointmentOptional.isPresent()) {
            appointmentManager.rescheduleAppointment(appointmentOptional.get(), availability);
            updateAppointmentList();
            setCurrentTextConsoleColor(ConsoleColor.GREEN);
            println("Appointment rescheduled successfully.");
        } else {
            setCurrentTextConsoleColor(ConsoleColor.RED);
            println("No appointment found.");
        }
        waitForKeyPress();
    }

    /**
     * Searches for an appointment by its ID from the list of appointments.
     *
     * @param appointmentId The appointment ID to search for.
     * @return An {@link Optional} containing the appointment if found, or empty if not found.
     */
    private Optional<Appointment> findAppointmentById(String appointmentId){
        return appointmentList.stream().filter(appointment -> appointment.getAppointmentId().toString().equals(appointmentId))
                .findFirst();
    }

}
