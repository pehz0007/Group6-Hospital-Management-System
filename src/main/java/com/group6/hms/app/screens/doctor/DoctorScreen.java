package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.MedicationStatus;
import com.group6.hms.app.auth.*;
import com.group6.hms.app.models.*;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.roles.Gender;
import com.group6.hms.app.auth.User;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.app.screens.MainScreen;
import com.group6.hms.app.screens.doctor.PatientMedicalRecordsScreen;
import com.group6.hms.app.managers.AppointmentManager;
import com.group6.hms.app.managers.AvailabilityManager;
import com.group6.hms.app.storage.SerializationStorageProvider;
import com.group6.hms.app.auth.LoginManagerHolder;
import com.group6.hms.framework.screens.pagination.PaginationTableScreen;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;
import com.group6.hms.framework.screens.pagination.SinglePaginationTableScreen;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

public class DoctorScreen extends LogoutScreen {

    /**
     * Constructor to initialize the DoctorScreen.
     */
    AppointmentManager appointmentManager = new AppointmentManager();
    LoginManager loginManager = LoginManagerHolder.getLoginManager();
    AvailabilityManager availabilityManager = new AvailabilityManager();
    private SerializationStorageProvider<User> userStorageProvider = new SerializationStorageProvider<>();
    File userFile = new File("data/users.ser");

    private Doctor doc;
    public DoctorScreen() {
        super("Doctor Menu");
        addOption(2, "View Patient Medical Records");
        //addOption(3, "Update Patient Medical Records");
        addOption(3, "View Personal Schedule");
        //addOption(5, "Set Availability for Appointments");
        addOption(4, "Accept or Decline Appointment Requests");
        addOption(5, "View Upcoming Appointments");
        addOption(6, "Record Appointment Outcome");
    }

    @Override
    public void onStart() {
        println("Welcome, " + getLoginManager().getCurrentlyLoggedInUser().getName() + "!");
        super.onStart();

    }

    @Override
    protected void onLogout() {
        newScreen(new MainScreen());
    }

    @Override
    protected void handleOption(int optionId) {
//        loginManager.loadUsersFromFile();
        userStorageProvider.loadFromFile(userFile);
        doc = (Doctor) loginManager.getCurrentlyLoggedInUser();
        User currentUser = getLoginManager().getCurrentlyLoggedInUser();
        if (currentUser == null) {
            System.out.println("No user is currently logged in.");
            return; // Exit or handle accordingly
        }

        switch (optionId) {
            case 2: {
                patientMedicalRecords();
                break;
            }

            case 3: {
                Map<LocalDate, List<Availability>> avail_appointments = availabilityManager.getAvailabilityByDoctor(doc).stream().collect(groupingBy(Availability::getAvailableDate));
                navigateToScreen(new DoctorAvailabilityScreen(avail_appointments));
//                println("Getting personal schedule...");
//                List<Availability> avail_appointments = availabilityManager.getAvailabilityByDoctor(doctor);
//                printAvailability(avail_appointments);
                break;
            }

            case 4: {
                println("Accept or Decline Appointment Requests");
                ArrayList<Appointment> requests = appointmentManager.getAppointmentsByDoctorAndStatus(doc, AppointmentStatus.REQUESTED);
                acceptorDecline(requests);
                break;
            }
            case 5: {
                println("View Upcoming Appointments");
                ArrayList<Appointment> upcoming = appointmentManager.getAppointmentsByDoctorAndStatus(doc, AppointmentStatus.CONFIRMED);
                upcomingAppointments(upcoming);
                break;
            }
            case 6: {
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

        if (availabilityMap.isEmpty()) {
            println("No availability found.");
        }
        else {
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
    }

    protected void patientMedicalRecords(){

        CareProvider retrievePatients = new CareProvider();
        Collection<UUID> medicalRecords = retrievePatients.getPatientIDsUnderDoctorCare(doc);
        navigateToScreen(new PatientMedicalRecordsScreen("Patient Medical Records", medicalRecords.stream().toList()));
//        navigateToScreen(new SinglePaginationTableScreen<UUID>("Patient Medical Records",medicalRecords.stream().toList()) {
//
//
//            @Override
//            protected void handleOption(int optionId) {
//                super.handleOption(optionId);
//            }
//
//
//
//            @Override
//            public void displaySingleItem(UUID item) {
//                Patient user = (Patient) getLoginManager().findUser(item);
//                PatientView patient = new PatientView(user);
//
//                PrintTableUtils.printItemAsVerticalTable(consoleInterface, patient);
//            }
//
//        });

//        for (UUID userID : medicalRecords) {
//            for(User user: userStorageProvider.getItems()){
//                if(user instanceof Patient && user.getSystemUserId().equals(userID)){
//
//                    println("Patient Name: "+ user.getName());
//                    println("Patient Gender: "+ user.getGender());
//                    println("Medical Records");
//                    println("Patient Date of Birth: "+((Patient) user).getMedicalRecord().getDateOfBirth());
//                    println("Patient Blood Type: "+ ((Patient) user).getMedicalRecord().getBloodType()+"\n");
//                    break;
//                }
//            }
//        }
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

                    ArrayList<Medication> medications = new ArrayList<>();
                    medications.add(new Medication(UUID.randomUUID(), "Panadol"));
                    medications.add(new Medication(UUID.randomUUID(), "Cough Syrup"));
                    medications.add(new Medication(UUID.randomUUID(), "Flu Medicine"));
                    AppointmentOutcomeRecord appointmentOutcomeRecord = new AppointmentOutcomeRecord(upcoming1.getDoctor().getSystemUserId(), upcoming1.getPatient().getSystemUserId(), upcoming1.getDate(),
                            service1, medications, details, MedicationStatus.PENDING);
                    appointmentManager.completeAppointment(upcoming1, appointmentOutcomeRecord);
                    println("Updated Successfully!!");
                    break;
                }
            }
            println(""); // Print a blank line for better readability
        }
    }
}
