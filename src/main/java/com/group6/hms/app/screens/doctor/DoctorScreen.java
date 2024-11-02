package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.auth.*;
import com.group6.hms.app.models.*;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.auth.User;
import com.group6.hms.app.screens.MainScreen;
import com.group6.hms.app.managers.AppointmentManager;
import com.group6.hms.app.managers.AvailabilityManager;
import com.group6.hms.app.storage.SerializationStorageProvider;
import com.group6.hms.app.auth.LoginManagerHolder;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

/**
 * The {@code DoctorScreen} class provides a menu interface for doctors to manage their operations,
 * such as viewing patient records and personal schedules.
 */
public class DoctorScreen extends LogoutScreen {

    /**
     * Appointment manager to handle appointment-related operations.
     */
    AppointmentManager appointmentManager = new AppointmentManager();

    /**
     * Login manager to handle user authentication and sessions.
     */
    LoginManager loginManager = LoginManagerHolder.getLoginManager();

    /**
     * Availability manager to manage doctor's availability for appointments.
     */
    AvailabilityManager availabilityManager = new AvailabilityManager();

    /**
     * Storage provider for serializing user data.
     */
    private SerializationStorageProvider<User> userStorageProvider = new SerializationStorageProvider<>();

    /**
     * File object to specify the user data file.
     */
    File userFile = new File("data/users.ser");

    /**
     * The currently logged-in doctor.
     */
    private static Doctor doc;

    /**
     * Constructs a DoctorScreen instance and initializes the menu options.
     */
    public DoctorScreen() {
        super("Doctor Menu");
        addOption(2, "View Patient Medical Records");
        //addOption(3, "Update Patient Medical Records");
        addOption(3, "View Personal Schedule");
        //addOption(5, "Set Availability for Appointments");
        addOption(4, "View Appointments");

    }

    /**
     * Displays a welcome message and initializes the currently logged-in doctor.
     */
    @Override
    public void onStart() {
        println("Welcome, " + getLoginManager().getCurrentlyLoggedInUser().getName() + "!");
        this.doc = (Doctor) getLoginManager().getCurrentlyLoggedInUser();
        super.onStart();
    }

    /**
     * Navigates to the main screen upon logout.
     */
    @Override
    protected void onLogout() {
        newScreen(new MainScreen());
    }

    /**
     * Handles the user's option selection from the menu.
     *
     * @param optionId The ID of the selected menu option.
     */
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

    /**
     * Navigates to the screen displaying the patient's medical records
     * under the care of the currently logged-in doctor.
     */
    protected void patientMedicalRecords(){

        CareProvider retrievePatients = new CareProvider();
        Collection<UUID> medicalRecords = retrievePatients.getPatientIDsUnderDoctorCare(doc);
        navigateToScreen(new PatientMedicalRecordsScreen("Patient Medical Records", medicalRecords.stream().toList()));
    }

    /**
     * Returns the currently logged-in doctor.
     *
     * @return The currently logged-in doctor.
     */
    public static Doctor getDoctor() {
        return doc; // Provide a static method to access the doctor
    }
}
