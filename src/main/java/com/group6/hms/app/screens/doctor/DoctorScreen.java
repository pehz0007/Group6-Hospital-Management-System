package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.MedicationStatus;
import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.auth.User;
import com.group6.hms.app.models.*;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.screens.MainScreen;
import com.group6.hms.app.auth.LogoutScreen;
import com.group6.hms.app.screens.doctor.PatientMedicalRecordsScreen;
import com.group6.hms.app.managers.AppointmentManager;
import com.group6.hms.app.managers.AvailabilityManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DoctorScreen extends LogoutScreen {

    /**
     * Constructor to initialize the DoctorScreen.
     */
    AppointmentManager appointmentManager = new AppointmentManager();
    AvailabilityManager availabilityManager = new AvailabilityManager();
    Doctor doc = new Doctor("tonkatsu", "password".toCharArray());

    public DoctorScreen() {
        super("Doctor Menu");
        addOption(2, "View Patient Medical Records");
        addOption(3, "Update Patient Medical Records");
        addOption(4, "View Personal Schedule");
        addOption(5, "Set Availability for Appointments");
        addOption(6, "Accept or Decline Appointment Requests");
        addOption(7, "View Upcoming Appointments");
        addOption(8, "Record Appointment Outcome");
    }

    @Override
    public void onStart() {
        println("Welcome, " + getLoginManager().getCurrentlyLoggedInUser().getUserId());
        super.onStart();
    }

    @Override
    protected void onLogout() {
        newScreen(new MainScreen());
    }

    @Override
    protected void handleOption(int optionId) {
        User currentUser  = getLoginManager().getCurrentlyLoggedInUser ();
        if (currentUser  == null) {
            System.out.println("No user is currently logged in.");
            return; // Exit or handle accordingly
        }

        switch(optionId){
            case 2, 3: {
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter patient name: ");
                String name = sc.nextLine();
                patientMedicialRecords();
                break;
            }
            case 4: {
                println("Getting personal schedule...");
                List<Availability> avail_appointments  = availabilityManager.getAvailabilityByDoctor(doc);
               printAvailability(avail_appointments);
                break;
            }
            case 5: {
                println("Set Availability");
                Scanner sc = new Scanner(System.in);
                println("Enter availability date (yyyy-mm-dd): ");
                String date = sc.nextLine();


                LocalDate date1 = LocalDate.parse(date);
                println("Enter availability start time: ");
                String startTime = sc.nextLine();
                LocalTime startTime1 = LocalTime.parse(startTime);
                Availability avail = new Availability(doc,date1, startTime1, startTime1.plusHours(1 ));
                availabilityManager.addAvailability(avail);
                break;
            }

            case 6: {
                println("Accept or Decline Appointment Requests");
                ArrayList<Appointment> requests = appointmentManager.getAppointmentsByDoctorAndStatus(doc, AppointmentStatus.REQUESTED);
                acceptorDecline(requests);
                break;
            }
            case 7: {
                println("View Upcoming Appointments");
                ArrayList<Appointment> upcoming = appointmentManager.getAppointmentsByDoctorAndStatus(doc, AppointmentStatus.CONFIRMED);
                upcomingAppointments(upcoming);
                break;
            }
            case 8: {
                println("Record Appointment Outcome");
                ArrayList<Appointment> upcoming = appointmentManager.getAppointmentsByDoctorAndStatus(doc, AppointmentStatus.CONFIRMED);
                RecordAppointmentDetails(upcoming);
                break;
            }
        }
    }
    protected void printAvailability(List<Availability> avail_appointments) {
        // Step 1: Group by date
        Map<LocalDate, List<Availability>> availabilityMap = new TreeMap<>();

        for (Availability avail : avail_appointments) {
            LocalDate date = avail.getAvailableDate();
            availabilityMap.putIfAbsent(date, new ArrayList<>());
            availabilityMap.get(date).add(avail);
        }

        // Step 2: Print grouped availability
        for (Map.Entry<LocalDate, List<Availability>> entry : availabilityMap.entrySet()) {
            LocalDate date = entry.getKey();
            List<Availability> availabilities = entry.getValue();

            println("Availability Date: " + date.toString());
            for (Availability avail : availabilities) {
                println("Availability time: " + avail.getAvailableStartTime().toString() + " - " + avail.getAvailableEndTime().toString());
            }
            println(""); // Print a blank line for better readability
        }
    }

    protected void patientMedicialRecords(){

    }

    protected void acceptorDecline(ArrayList<Appointment> appointments) {
        Scanner sc = new Scanner(System.in);
        if(appointments.isEmpty()){
            println("No requests appointment are found.");
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
            if(LocalDate.now().isBefore(date)){
                availabilityMap.putIfAbsent(date, new ArrayList<>());
                availabilityMap.get(date).add(upcoming);
            }
        }
        for (Map.Entry<LocalDate, List<Appointment>> entry : availabilityMap.entrySet()) {
            LocalDate date = entry.getKey();
            List<Appointment> appointments1 = entry.getValue();

            println("Upcoming Appointment Date: " + date.toString());
            for (Appointment upcoming1 : appointments1) {
                println("Patient Name: "+ upcoming1.getPatient().toString());
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

                    ArrayList<Medication> medications = new ArrayList<>();
                    medications.add(new Medication(UUID.randomUUID(), "Panadol"));
                    medications.add(new Medication(UUID.randomUUID(), "Cough Syrup"));
                    medications.add(new Medication(UUID.randomUUID(), "Flu Medicine"));
                    AppointmentOutcomeRecord appointmentOutcomeRecord = new AppointmentOutcomeRecord(upcoming1.getDoctor().getUserId(), upcoming1.getPatient().getUserId(), upcoming1.getDate(),
                            service1, medications, details, MedicationStatus.PENDING);
                    appointmentManager.completeAppointment(upcoming1, appointmentOutcomeRecord);
                    break;
                }
            }
            println(""); // Print a blank line for better readability
        }
    }
}
