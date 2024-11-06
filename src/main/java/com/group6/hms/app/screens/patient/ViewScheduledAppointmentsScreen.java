package com.group6.hms.app.screens.patient;

import com.group6.hms.app.managers.appointment.AppointmentManager;
import com.group6.hms.app.managers.appointment.AppointmentManagerHolder;
import com.group6.hms.app.managers.appointment.models.Appointment;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.framework.screens.pagination.PaginationTableScreen;

import java.util.List;

/**
 * The {@code ViewScheduledAppointmentsScreen} class displays a paginated list of the patient's scheduled appointments,
 * allowing the patient to view their upcoming appointments.
 *
 * This class extends {@link PaginationTableScreen} to display the appointments list in paginated view.
 */
public class ViewScheduledAppointmentsScreen extends PaginationTableScreen<Appointment> {
    private Patient patient;
    private List<Appointment> appointmentList;
    private AppointmentManager appointmentManager = AppointmentManagerHolder.getAppointmentManager();

    /**
     * Constructs a {@code ViewScheduledAppointmentsScreen} for displaying the patient's schedules appointments.
     *
     * @param patient The patient whose appointments are being displayed.
     */
    public ViewScheduledAppointmentsScreen(Patient patient) {
        super("Scheduled Appointments", null);
        this.patient = patient;
        updateAppointmentList();
    }

    /**
     * Updates the list of appointments for the patient by fetching them from {@code AppointmentManager}
     * Sets the list in the pagination table for display.
     */
    private void updateAppointmentList(){
        this.appointmentList = appointmentManager.getAppointmentsByPatient(this.patient);
        setList(this.appointmentList);
    }
}
