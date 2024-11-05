package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.managers.careprovider.CareProvider;
import com.group6.hms.app.managers.appointment.AppointmentManager;
import com.group6.hms.app.managers.appointment.AppointmentManagerHolder;
import com.group6.hms.app.managers.appointment.models.Appointment;
import com.group6.hms.app.managers.appointment.models.AppointmentStatus;
import com.group6.hms.app.managers.auth.LoginManager;
import com.group6.hms.app.managers.auth.LogoutScreen;
import com.group6.hms.app.managers.availability.AvailabilityManagerHolder;
import com.group6.hms.app.managers.availability.models.Availability;
import com.group6.hms.app.managers.careprovider.CareProviderHolder;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.managers.auth.User;
import com.group6.hms.app.screens.MainScreen;
import com.group6.hms.app.managers.availability.AvailabilityManager;
import com.group6.hms.app.storage.SerializationStorageProvider;
import com.group6.hms.app.managers.auth.LoginManagerHolder;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

public class DoctorScreen extends LogoutScreen {

    /**
     * Constructor to initialize the DoctorScreen.
     */
    private AppointmentManager appointmentManager = AppointmentManagerHolder.getAppointmentManager();
    private LoginManager loginManager = LoginManagerHolder.getLoginManager();
    private AvailabilityManager availabilityManager = AvailabilityManagerHolder.getAvailabilityManager();
    private SerializationStorageProvider<User> userStorageProvider = new SerializationStorageProvider<>();
    private File userFile = new File("data/users.ser");

    private static Doctor doc;
    public DoctorScreen() {
        super("Doctor Menu");
        addOption(2, "View Patient Medical Records");
        //addOption(3, "Update Patient Medical Records");
        addOption(3, "View Personal Schedule");
        //addOption(5, "Set Availability for Appointments");
        addOption(4, "View Appointments");

    }

    @Override
    public void onStart() {
        println("Welcome, " + getLoginManager().getCurrentlyLoggedInUser().getName() + "!");
        this.doc = (Doctor) getLoginManager().getCurrentlyLoggedInUser();
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
                List<Availability> trial = availabilityManager.getAvailabilityByDoctor(doc);
                Map<LocalDate, List<Availability>> avail_appointments = availabilityManager.getAvailabilityByDoctor(doc).stream().collect(groupingBy(Availability::getAvailableDate));
                navigateToScreen(new DoctorAvailabilityScreen(avail_appointments));
//                println("Getting personal schedule...");
//                List<Availability> avail_appointments = availabilityManager.getAvailabilityByDoctor(doctor);
//                printAvailability(avail_appointments);
                break;
            }

            case 4: {
                List<Appointment> getall = appointmentManager.getAllAppointments();
                List<Appointment> appt = appointmentManager.getAppointmentsByDoctor(doc);
                Map<LocalDate, List<Appointment>> requests = appointmentManager.getAppointmentsByDoctor(doc).stream()
                        .filter(appointment ->
                                appointment.getStatus() == AppointmentStatus.REQUESTED ||
                                        appointment.getStatus() == AppointmentStatus.CONFIRMED)
                        .collect(groupingBy(Appointment::getDate));
                navigateToScreen(new AppointmentScreen(requests));
                break;
            }

        }
    }


    protected void patientMedicalRecords(){

        CareProvider retrievePatients = CareProviderHolder.getCareProvider();
        Collection<UUID> medicalRecords = retrievePatients.getPatientIDsUnderDoctorCare(doc);
        navigateToScreen(new PatientMedicalRecordsScreen("Patient Medical Records", medicalRecords.stream().toList()));
    }

    public static Doctor getDoctor() {
        return doc; // Provide a static method to access the doctor
    }

}
