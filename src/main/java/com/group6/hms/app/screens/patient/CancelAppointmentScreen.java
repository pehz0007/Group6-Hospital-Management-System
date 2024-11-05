package com.group6.hms.app.screens.patient;

import com.group6.hms.app.managers.AppointmentManager;
import com.group6.hms.app.models.Appointment;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.pagination.PaginationTableScreen;

import java.util.List;
import java.util.Optional;

public class CancelAppointmentScreen extends PaginationTableScreen<Appointment>{
    private Patient patient;
    private List<Appointment> appointmentList;
    private AppointmentManager appointmentManager = new AppointmentManager();

    private final int CANCEL_APPOINTMENT = 4;

    public CancelAppointmentScreen(Patient patient) {
        super("Cancel Appointment", null);
        this.patient = patient;
        updateAppointmentList();
        addOption(CANCEL_APPOINTMENT, "Cancel Appointment");
    }

    private void updateAppointmentList(){
        this.appointmentList = appointmentManager.getAppointmentsByPatient(this.patient);
        setList(this.appointmentList);
    }

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
