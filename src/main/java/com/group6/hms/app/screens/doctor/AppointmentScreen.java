package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.auth.LoginManagerHolder;
import com.group6.hms.app.auth.User;
import com.group6.hms.app.managers.AppointmentManager;
import com.group6.hms.app.managers.AvailabilityManager;
import com.group6.hms.app.models.*;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.storage.SerializationStorageProvider;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.calendar.CalendarScreen;
import com.group6.hms.framework.screens.calendar.EventInterface;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

public class AppointmentScreen extends CalendarScreen<Appointment, List<Appointment>> {
    private Map<LocalDate, List<Appointment>> events;
    AppointmentManager appointmentManager = new AppointmentManager();
    LoginManager loginManager = LoginManagerHolder.getLoginManager();
    AvailabilityManager availabilityManager = new AvailabilityManager();
    private SerializationStorageProvider<User> userStorageProvider = new SerializationStorageProvider<>();
    File userFile = new File("data/users.ser");
    private Doctor doc;

    @Override
    public void onDisplay() {
        super.onDisplay();
//        doc = (Doctor) loginManager.getCurrentlyLoggedInUser ();
//        List<Appointment> latestAppointments = appointmentManager.getAppointmentsByDoctor(doc);
//
//        // Filter the appointments based on their status
//        this.events = latestAppointments.stream()
//                .filter(appointment ->
//                        appointment.getStatus() == AppointmentStatus.REQUESTED ||
//                                appointment.getStatus() == AppointmentStatus.CONFIRMED)
//                .collect(groupingBy(Appointment::getDate));
    }

    @Override
    protected void handleOption(int optionId) {
        super.handleOption(optionId);
        userStorageProvider.loadFromFile(userFile);
        doc = DoctorScreen.getDoctor();
        if(optionId == 5){
             List<Appointment> requests = appointmentManager.getAppointmentsByDoctorAndStatus(doc, AppointmentStatus.REQUESTED);
            navigateToScreen(new AcceptOrDeclineScreen(requests));

        }else if(optionId == 6){
            ArrayList<Appointment> appointments = appointmentManager.getAppointmentsByDoctorAndStatus(doc, AppointmentStatus.CONFIRMED);
            Map<LocalDate, List<UUID>> availabilityMap = new HashMap<>();

            for (Appointment upcoming : appointments) {
                LocalDate date = upcoming.getDate();
                if(LocalDate.now().isEqual(date)){
                    availabilityMap.putIfAbsent(date, new ArrayList<>());
                    availabilityMap.get(date).add(upcoming.getAppointmentId());
                }
            }
            if(availabilityMap.size() == 0){
                println("\u001B[31m"+ "There isn't any appointments today.");
            }else{
                for (Map.Entry<LocalDate, List<UUID>> entry : availabilityMap.entrySet()) {
                    LocalDate date = entry.getKey();
                    List<UUID> appointments1 = entry.getValue();
                    AppointmentService service1;
                    navigateToScreen(new UpdateConsultationNotesScreen(appointments1));

                }
            }


        }else if(optionId == 7){
            ArrayList<Appointment> appointments = appointmentManager.getAppointmentsByDoctorAndStatus(doc, AppointmentStatus.COMPLETED);
            Map<LocalDate, List<UUID>> availabilityMap = new HashMap<>();
            LocalDate eventsCurrentDate = events.keySet().stream()
                    .filter(date -> date.isEqual(currentDate))
                    .findFirst()
                    .orElse(null);

            for (Appointment upcoming : appointments) {
                LocalDate date = upcoming.getDate();
                assert eventsCurrentDate != null;
                if(eventsCurrentDate.isEqual(date)){
                    availabilityMap.putIfAbsent(date, new ArrayList<>());
                    availabilityMap.get(date).add(upcoming.getAppointmentOutcomeRecordId());
                }
            }
            if(availabilityMap.size() == 0){
                println("\u001B[31m"+ "There isn't any appointments today.");
            }else{
                for (Map.Entry<LocalDate, List<UUID>> entry : availabilityMap.entrySet()) {
                    LocalDate date = entry.getKey();
                    List<UUID> appointments1 = entry.getValue();
                    AppointmentService service1;
                    navigateToScreen(new ViewMoreDetailsAppointmentScreen(appointments1));

                }
            }


        }

    }

    /**
     * Constructor to initialize the screen with a title.
     *
     * @param events
     */

    public AppointmentScreen(Map<LocalDate, List<Appointment>> events) {
        super("Appointments", events);
        this.events = events;


        addOption(5,"Accept or Decline Upcoming Appointments", ConsoleColor.YELLOW);
        addOption(6, "Record Consultation Notes", ConsoleColor.YELLOW);
        addOption(7, "View More Information", ConsoleColor.YELLOW);
    }





    @Override
    public void onStart() {
        super.onStart();
        doc = DoctorScreen.getDoctor();
        this.events = appointmentManager.getAppointmentsByDoctor(doc).stream()
                .filter(appointment ->
                        appointment.getStatus() == AppointmentStatus.REQUESTED ||
                                appointment.getStatus() == AppointmentStatus.CONFIRMED)
                .collect(groupingBy(Appointment::getDate));
    }



}
