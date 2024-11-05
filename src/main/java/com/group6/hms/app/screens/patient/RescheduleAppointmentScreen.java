package com.group6.hms.app.screens.patient;

import com.group6.hms.app.managers.AppointmentManager;
import com.group6.hms.app.models.Appointment;
import com.group6.hms.app.models.Availability;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.pagination.PaginationTableScreen;

import java.util.List;
import java.util.Optional;

public class RescheduleAppointmentScreen extends PaginationTableScreen<Appointment> {
    private Patient patient;
    private Availability availability;
    private List<Appointment> appointmentList;
    private AppointmentManager appointmentManager = new AppointmentManager();

    private final int RESCHEDULE_APPOINTMENT = 3;

    public RescheduleAppointmentScreen(List<Appointment> appointmentList, Availability availability, Patient patient) {
        super("Reschedule Appointment", null);
        this.patient = patient;
        //this.appointmentList = appointmentList;
        this.availability = availability;
        updateAppointmentList();

        addOption(RESCHEDULE_APPOINTMENT, "Reschedule Appointment", ConsoleColor.CYAN);
    }

    private void updateAppointmentList(){
        this.appointmentList = appointmentManager.getAppointmentsByPatient(this.patient);
        setList(this.appointmentList);
    }

    @Override
    protected void handleOption(int optionId) {
        if(optionId == RESCHEDULE_APPOINTMENT){
            rescheduleAppointment();
        }
    }

    private void rescheduleAppointment(){
        print("Select the appointment id to reschedule:");
        String selectedAppointmentId = readString();
        Optional<Appointment> appointmentOptional = findAppointmentById(selectedAppointmentId);

        if (appointmentOptional.isPresent()) {
            appointmentManager.rescheduleAppointment(patient,appointmentOptional.get(), availability);
            updateAppointmentList();
            setCurrentTextConsoleColor(ConsoleColor.GREEN);
            println("Appointment rescheduled successfully.");
        } else {
            setCurrentTextConsoleColor(ConsoleColor.RED);
            println("No appointment found.");
        }waitForKeyPress();
    }

    private Optional<Appointment> findAppointmentById(String appointmentId){
        return appointmentList.stream().filter(appointment -> appointment.getAppointmentId().toString().equals(appointmentId))
                .findFirst();
    }

}
