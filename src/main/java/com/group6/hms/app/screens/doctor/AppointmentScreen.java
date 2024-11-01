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
        doc = (Doctor) loginManager.getCurrentlyLoggedInUser();
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
            ArrayList<Appointment> appointments = appointmentManager.getAppointmentsByDoctorAndStatus(doc, AppointmentStatus.CONFIRMED);
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



    protected void acceptorDecline(ArrayList<Appointment> appointments) {
        Scanner sc = new Scanner(System.in);
        if(appointments.isEmpty()){
            println("No requests appointment are found.\n");
        }else{
            println("You got "+ appointments.size()+" requests!");
            for(Appointment appointment : appointments){
                println("Scheduled Date: "+ appointment.getDate().toString());
                println("Start Time: "+ appointment.getStartTime().toString());
                println("End Time: "+ appointment.getEndTime().toString()+"\n");
                println("Accept or decline?");
                String result = sc.nextLine();
                if(result.equalsIgnoreCase("accept")){
                    appointmentManager.acceptAppointmentRequest(appointment);
                }else{
                    appointmentManager.declineAppointmentRequest(appointment);
                }
            }
        }
    }

    protected void upcomingAppointments(ArrayList<Appointment> appointments) {
        Map<LocalDate, List<Appointment>> availabilityMap = new TreeMap<>();

        for (Appointment upcoming : appointments) {
            LocalDate date = upcoming.getDate();

            availabilityMap.putIfAbsent(date, new ArrayList<>());
            availabilityMap.get(date).add(upcoming);

        }
        for (Map.Entry<LocalDate, List<Appointment>> entry : availabilityMap.entrySet()) {
            LocalDate date = entry.getKey();
            List<Appointment> appointments1 = entry.getValue();


            for (Appointment upcoming1 : appointments1) {
                println("Upcoming Appointment Date: " + upcoming1.getDate().toString());
                println("Patient Name: "+ upcoming1.getPatient().getName());
                println("Appointment Timing: "+ upcoming1.getStartTime().toString()+"\n");
            }
            println(""); // Print a blank line for better readability
        }
    }

    protected void RecordAppointmentDetails(ArrayList<Appointment> appointments) {
        Map<LocalDate, List<Appointment>> availabilityMap = new HashMap<>();

        for (Appointment upcoming : appointments) {
            LocalDate date = upcoming.getDate();
            if(LocalDate.now().isEqual(date)){
                availabilityMap.putIfAbsent(date, new ArrayList<>());
                availabilityMap.get(date).add(upcoming);
            }
        }
        String message = "Appointments for today:";
        println("-".repeat(message.length()));
        Scanner sc = new Scanner(System.in);

        for (Map.Entry<LocalDate, List<Appointment>> entry : availabilityMap.entrySet()) {
            LocalDate date = entry.getKey();
            List<Appointment> appointments1 = entry.getValue();
            AppointmentService service1;

            for (Appointment upcoming1 : appointments1) {
                println("Patient Name: "+ upcoming1.getPatient().toString());
                println("Appointment Timing: "+ upcoming1.getStartTime().toString());
                println("Is this the appointment you want to update? (Y or N)");
                char result = sc.nextLine().charAt(0);
                if(result == 'Y'){
                    println("Appointment Details Update:");
                    String details = sc.nextLine();
                    println("Appointment Service: (Consult or Xray or Blood Test");
                    String service = sc.nextLine();
                    if(service.equalsIgnoreCase("consult")){
                        service1 = AppointmentService.CONSULT;
                    }else if(service.equalsIgnoreCase("xray")){
                        service1 = AppointmentService.XRAY;
                    }else {
                        service1 = AppointmentService.BLOOD_TEST;
                    }

//                    ArrayList<Medication> medications = new ArrayList<>();
//                    medications.add(new Medication(UUID.randomUUID(), "Panadol"));
//                    medications.add(new Medication(UUID.randomUUID(), "Cough Syrup"));
//                    medications.add(new Medication(UUID.randomUUID(), "Flu Medicine"));
//                    AppointmentOutcomeRecord appointmentOutcomeRecord = new AppointmentOutcomeRecord(upcoming1.getDoctor().getSystemUserId(), upcoming1.getPatient().getSystemUserId(), upcoming1.getDate(),
//                            service1, medications, details, MedicationStatus.PENDING);
//                    appointmentManager.completeAppointment(upcoming1, appointmentOutcomeRecord);
//                    println("Updated Successfully!!");
                    // TODO:
                    //  1) get all medications
                    //  2) doctor select what medications to prescribe + quantity
                    //  3) create PrescribedMedication object to pass into AppointmentOutcomeRecord
                    ArrayList<PrescribedMedication> medications = new ArrayList<>();
//                    medications.add()
//                    medications.add(new Medication(UUID.randomUUID(), "Panadol"));
//                    medications.add(new Medication(UUID.randomUUID(), "Cough Syrup"));
//                    medications.add(new Medication(UUID.randomUUID(), "Flu Medicine"));
//                    AppointmentOutcomeRecord appointmentOutcomeRecord = new AppointmentOutcomeRecord(upcoming1.getDoctor().getUserId(), upcoming1.getPatient().getUserId(), upcoming1.getDate(),

//                            service1, medications, details, MedicationStatus.PENDING);
//                    appointmentManager.completeAppointment(upcoming1, appointmentOutcomeRecord);
                    break;
                }
            }
            println(""); // Print a blank line for better readability
        }
    }


}
