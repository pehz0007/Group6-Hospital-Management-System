package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.managers.appointment.AppointmentManager;
import com.group6.hms.app.managers.appointment.AppointmentManagerHolder;
import com.group6.hms.app.managers.appointment.models.Appointment;
import com.group6.hms.app.managers.appointment.models.AppointmentService;
import com.group6.hms.app.managers.appointment.models.AppointmentStatus;
import com.group6.hms.app.managers.auth.LoginManager;
import com.group6.hms.app.managers.auth.LoginManagerHolder;
import com.group6.hms.app.managers.auth.User;
import com.group6.hms.app.managers.availability.AvailabilityManager;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.storage.SerializationStorageProvider;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.calendar.CalendarScreen;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

/**
 * The {@code AppointmentScreen} class represents a screen where doctors can manage their appointments,
 * including accepting or declining appointments, recording consultation notes, and viewing detailed information.
 *
 * It extends the {@link CalendarScreen} class to display appointment events organized by date.
 */
public class AppointmentScreen extends CalendarScreen<Appointment, List<Appointment>> {

    /** A map of dates to appointments, used to display events on specific days. */
    private Map<LocalDate, List<Appointment>> events;

    /** Manages operations related to appointments. */
    private AppointmentManager appointmentManager = AppointmentManagerHolder.getAppointmentManager();

    /** Provides serialization storage for users. */
    private SerializationStorageProvider<User> userStorageProvider = new SerializationStorageProvider<>();

    /** File storage location for serialized user data. */
    private File userFile = new File("data/users.ser");

    /** The doctor currently logged in to the system. */
    private Doctor doc;

    /**
     * Invoked when the screen is displayed. Fetches the currently logged-in doctor
     * and retrieves appointments to display based on their status.
     */
    @Override
    public void onDisplay() {
        super.onDisplay();
    }

    /**
     * Handles user-selected options for managing appointments.
     *
     * @param optionId the ID of the selected option
     */
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
     * Constructor to initialize the AppointmentScreen with a title and events.
     *
     * @param events a map of dates to a list of {@link Appointment} objects, representing scheduled events
     */
    public AppointmentScreen(Map<LocalDate, List<Appointment>> events) {
        super("Appointments", events);
        this.events = events;


        addOption(5,"Accept or Decline Upcoming Appointments", ConsoleColor.YELLOW);
        addOption(6, "Record Consultation Notes", ConsoleColor.YELLOW);
        addOption(7, "View More Information", ConsoleColor.YELLOW);
    }

    /**
     * Invoked when the screen starts. Sets the currently logged-in doctor and retrieves upcoming
     * appointments that are either requested or confirmed, organized by date.
     */
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
