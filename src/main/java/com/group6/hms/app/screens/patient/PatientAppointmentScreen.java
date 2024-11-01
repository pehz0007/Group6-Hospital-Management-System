package com.group6.hms.app.screens.patient;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.managers.AppointmentManager;
import com.group6.hms.app.managers.AvailabilityManager;
import com.group6.hms.app.models.AppointmentStatus;
import com.group6.hms.app.models.Availability;
import com.group6.hms.app.models.Appointment;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.framework.screens.calendar.CalendarScreen;
import com.group6.hms.framework.screens.pagination.PaginationTableScreen;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class PatientAppointmentScreen extends CalendarScreen<Availability, List<Availability>>{
    Map<LocalDate, List<Availability>> doctorAvailability;
    Patient patient;
    AvailabilityManager availabilityManager = new AvailabilityManager();
    AppointmentManager appointmentManager = new AppointmentManager();
    LoginManager loginManager = new LoginManager();

    public PatientAppointmentScreen(Map<LocalDate, List<Availability>> events, int option) {
        super(getScreenTitle(option), events);
        this.patient = (Patient) loginManager.getCurrentlyLoggedInUser();
        this.doctorAvailability = events;
    }

    @Override
    public void onStart(){
        super.onStart();
        doctorAvailability = availabilityManager.getAllAvailability().stream()
                        .collect(groupingBy(Availability::getAvailableDate));
    }

    @Override
    protected void handleOption(int optionId){
        super.handleOption(optionId);
        switch (optionId){
            case 4-> displayAvailabilityForDate(currentDate);
            case 5-> scheduleAppointmentSlot(currentDate);
            default-> println("Invalid option selected");
        }
    }

    private static String getScreenTitle(int option){
        switch (option){
            case 4->{
                return "Available Appointment Slots";
            }
            case 5->{
                return "Scheduled Appointments";
            }
            default->{
                return "Appointment Screen";
            }
        }
    }

    private void displayAvailabilityForDate(LocalDate date){
        List<Availability> availableSlots = doctorAvailability.getOrDefault(currentDate, List.of());

        if(availableSlots == null && !availableSlots.isEmpty()){
            println("Available Slots for " + date + ":");
            navigateToScreen(new PaginationTableScreen<>(
                    "Available Appointments", availableSlots
            ));
        }else{
            println("No available appointment slots for this date.");
        }
    }

    private void scheduleAppointmentSlot(LocalDate date){
        List<Availability> availableSlots = doctorAvailability.get(date);

        if(availableSlots != null && !availableSlots.isEmpty()){
            println("Select a slot to schedule an appointment.");
            for(int i=0; i<availableSlots.size(); i++){
                Availability slot = availableSlots.get(i);
                println((i+1) + "." + slot.getDoctor().getName() + ":" +
                        slot.getAvailableStartTime() + "-" + slot.getAvailableEndTime());
            }

            int choice = Integer.parseInt(readString()) -1;
            if (choice>=0 && choice<availableSlots.size()){
                Availability selectedSlot = availableSlots.get(choice);

                Appointment appointment = new Appointment(patient, selectedSlot.getDoctor(), AppointmentStatus.REQUESTED, selectedSlot.getAvailableDate(), selectedSlot.getAvailableStartTime(), selectedSlot.getAvailableEndTime());
                appointmentManager.scheduleAppointment(patient, selectedSlot);

                println("Appointment scheduled successfully with Dr. ." + selectedSlot.getDoctor().getName());
            }else{
                println("Invalid choice");
            }
        }else{
            println("No available appointment slots for this date.");
        }
    }
}