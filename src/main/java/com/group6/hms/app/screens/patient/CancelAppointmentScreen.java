package com.group6.hms.app.screens.patient;

import com.group6.hms.app.managers.appointment.AppointmentManager;
import com.group6.hms.app.managers.appointment.AppointmentManagerHolder;
import com.group6.hms.app.managers.appointment.models.Appointment;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.pagination.PaginationTableScreen;

import java.util.List;
import java.util.Optional;

/**
 * The {@code CancelAppointmentScreen} class allows view and cancel scheduled appointments.
 * This class extends {@link PaginationTableScreen} to provide a paginated view of schedule appointments
 * and provides options for patient to select and cancel a specific appointment by its ID.
 */

public class CancelAppointmentScreen extends PaginationTableScreen<Appointment>{
    private Patient patient;
    private List<Appointment> appointmentList;
    private AppointmentManager appointmentManager = AppointmentManagerHolder.getAppointmentManager();

    private final int CANCEL_APPOINTMENT = 4;

    /**
     * Constructs a {@code CancelAppointmentScreen} for a specific patient.
     * Initializes the appointments list of the patient and adds the "Cancel Appointment" option
     *
     * @param patient The {@code Patient} object representing the patient whose appointments are being managed.
     */
    public CancelAppointmentScreen(Patient patient) {
        super("Cancel Appointment", null);
        this.patient = patient;
        updateAppointmentList();
        addOption(CANCEL_APPOINTMENT, "Cancel Appointment");
    }

    /**
     * Updates the appointments list for the current patient by retrieving the patient's latest appointments
     * from the {@code AppointmentManager}
     */
    private void updateAppointmentList(){
        this.appointmentList = appointmentManager.getAppointmentsByPatient(this.patient);
        setList(this.appointmentList);
    }

    /**
     * Handles options selected by the user.
     * If the "Cancel Appointment" option is selected, the method prompts for the appointment ID
     * and attempts to cancel the corresponding appointment.
     *
     * @param optionId The ID of the option selected by the user.
     */
    @Override
    protected void handleOption(int optionId){
        if(optionId == CANCEL_APPOINTMENT){
            print("Select the appointment id to cancel: ");
            String selectedAppointmentId = readString();

            Optional<Appointment> appointmentOptional = appointmentList.stream()
                    .filter(a -> a.getAppointmentId().toString().equals(selectedAppointmentId)).findFirst();

            if(appointmentOptional.isPresent()){
                appointmentManager.cancelAppointment(appointmentOptional.get());
                updateAppointmentList();
            }else{
                setCurrentTextConsoleColor(ConsoleColor.RED);
                println("Unable to find appointment with id " + selectedAppointmentId);
            }
        }
    }
}
