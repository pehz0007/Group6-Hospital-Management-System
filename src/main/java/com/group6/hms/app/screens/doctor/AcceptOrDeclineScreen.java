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

public class AcceptOrDeclineScreen extends PaginationTableScreen<Appointment> {

    private static final File appointmentsFile = new File("data/appointments.ser");
    private final StorageProvider<Appointment> appointmentStorageProvider = new SerializationStorageProvider<>();
    List<Appointment> appointments;

    AppointmentManager appointmentManager = new AppointmentManager();
    AvailabilityManager availabilityManager = new AvailabilityManager();


    @Override
    public void onStart() {
        super.onStart();
        println("You got "+appointments.size()+" appointment(s)!");
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
        println("You got "+appointments.size()+" appointment(s)!");
    }

    @Override
    protected void handleOption(int optionId) {
        super.handleOption(optionId);
        if(optionId == 1){
            acceptAppointment(appointments);
        }else if(optionId == 2){
            declineAppointment(appointments);
        }
    }

    public AcceptOrDeclineScreen(List<Appointment> items) {
        super("Accept Pending Appointments", items);

        this.appointments = items;

        addOption(1, "Accept Appointment");
        addOption(2, "Decline Appointment");
    }

    protected void acceptAppointment(List<Appointment> items){
        for(Appointment appointment : items){
            print("=".repeat(30 )+"\n");
            println("Appointment Date & Timing: " + appointment.getDate() + " "+ appointment.getStartTime());
            println("Patient Name: "+ appointment.getPatient().getName());
            print("\u001B[37m Is this the appointment you want to accept? (Y/N): ");
            String result = readString();
            if(result.equalsIgnoreCase("y")){
                appointmentManager.acceptAppointmentRequest(appointment);
                items.remove(appointment);
                //appointmentStorageProvider.saveToFile(appointmentsFile);
                break;
            }
        }
    }

    protected void declineAppointment(List<Appointment> appointment){
        for(Appointment appointments : appointment){
            print("=".repeat(30 )+"\n");
            println("Appointment Date & Timing: " + appointments.getDate() + " "+ appointments.getStartTime());
            println("Patient Name: "+ appointments.getPatient().getName());
            print("\u001B[37m Is this the appointment you want to decline? (Y/N): ");
            String result = readString();
            if(result.equalsIgnoreCase("n")){
                appointmentManager.declineAppointmentRequest(appointments);
                appointment.remove(appointments);
                //appointmentStorageProvider.saveToFile(appointmentsFile);

                break;
            }
        }
    }
}
