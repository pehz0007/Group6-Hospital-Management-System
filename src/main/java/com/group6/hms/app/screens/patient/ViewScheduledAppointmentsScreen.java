package com.group6.hms.app.screens.patient;

import com.group6.hms.app.managers.appointment.AppointmentManager;
import com.group6.hms.app.managers.appointment.AppointmentManagerHolder;
import com.group6.hms.app.managers.appointment.models.Appointment;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.framework.screens.pagination.PaginationTableScreen;

import java.util.List;

public class ViewScheduledAppointmentsScreen extends PaginationTableScreen<Appointment> {
    private Patient patient;
    private List<Appointment> appointmentList;
    private AppointmentManager appointmentManager = AppointmentManagerHolder.getAppointmentManager();

    public ViewScheduledAppointmentsScreen(Patient patient) {
        super("Scheduled Appointments", null);
        this.patient = patient;
        updateAppointmentList();
    }

    private void updateAppointmentList(){
        this.appointmentList = appointmentManager.getAppointmentsByPatient(this.patient);
        setList(this.appointmentList);
    }
}
