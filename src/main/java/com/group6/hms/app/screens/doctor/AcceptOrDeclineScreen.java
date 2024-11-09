package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.managers.appointment.AppointmentManager;
import com.group6.hms.app.managers.appointment.AppointmentManagerHolder;
import com.group6.hms.app.managers.appointment.models.Appointment;
import com.group6.hms.framework.screens.pagination.PaginationTableScreen;

import java.util.List;

/**
 * The {@code AcceptOrDeclineScreen} screen for a doctor to accept or decline appointments.
 * This screen displays a list of pending appointments and allows the doctor
 * to interactively accept or decline each appointment.
 *
 * * <p>This class extends {@link PaginationTableScreen} to provide a paginated view of appointments</p>
 */
public class AcceptOrDeclineScreen extends PaginationTableScreen<Appointment> {

    private List<Appointment> appointments;

    private AppointmentManager appointmentManager = AppointmentManagerHolder.getAppointmentManager();

    /**
     * Called when the screen starts, initializing the screen with the list of appointments.
     * It also prints out the number of pending appointments.
     */
    @Override
    public void onStart() {
        super.onStart();
        println("You got "+appointments.size()+" appointment(s)!");
    }

    /**
     * Displays the list of pending appointments along with the available options to the user.
     */
    @Override
    public void onDisplay() {
        super.onDisplay();
        println("You got "+appointments.size()+" appointment(s)!");
    }

    /**
     * Handles the user option to accept or decline appointments.
     *
     * @param optionId the ID of the selected option
     */
    @Override
    protected void handleOption(int optionId) {
        super.handleOption(optionId);
        if(optionId == 1){
            acceptAppointment(appointments);
        }else if(optionId == 2){
            declineAppointment(appointments);
        }
    }

    /**
     * Constructor to initialize the screen with specified list of appointments.
     * It also sets up options for accepting or declining appointments.
     *
     * @param items a list of {@link Appointment} objects to be displayed
     */
    public AcceptOrDeclineScreen(List<Appointment> items) {
        super("Accept Pending Appointments", items);

        this.appointments = items;

        addOption(1, "Accept Appointment");
        addOption(2, "Decline Appointment");
    }

    /**
     * Accepts a selected appointment from the list.
     * Prompts the user to confirm the acceptance of the appointment.
     *
     * @param items the list of appointments to process
     */
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

                return;
            }
        }
        println("\u001B[31m Invalid input. Please try again.");
    }
}
