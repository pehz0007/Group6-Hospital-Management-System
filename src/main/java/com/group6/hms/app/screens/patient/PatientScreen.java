package com.group6.hms.app.screens.patient;

import com.group6.hms.app.managers.appointment.AppointmentManager;
import com.group6.hms.app.managers.appointment.AppointmentManagerHolder;
import com.group6.hms.app.managers.appointment.models.AppointmentOutcomeRecord;
import com.group6.hms.app.managers.auth.LogoutScreen;
import com.group6.hms.app.managers.availability.AvailabilityManager;
import com.group6.hms.app.managers.availability.AvailabilityManagerHolder;
import com.group6.hms.app.managers.availability.models.Availability;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.app.screens.MainScreen;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

/**
 * The {@code PatientScreen} class represents the main interface for patients, providing options
 * It extends {@link LogoutScreen} to let this screen inherits the attributes of logout
 * Patients can view their medical record, edit non-medical personal information,view available appointment slots,
 * and review past appointment outcome records.
 */

public class PatientScreen extends LogoutScreen {
    private static final int VIEW_MEDICAL_RECORD = 2;
    private static final int USER_CONFIGURATION = 3;
    private static final int APPOINTMENT_SYSTEM = 4;
    private static final int VIEW_PAST_APPOINTMENT_OUTCOME_RECORDS = 5;

    private Patient patient;
    private AvailabilityManager availabilityManager = AvailabilityManagerHolder.getAvailabilityManager();
    private AppointmentManager appointmentManager = AppointmentManagerHolder.getAppointmentManager();

    /**
     * Constructor to initialize the menu options for PatientScreen.
     */
    public PatientScreen() {
        super("Patient Menu");
        addOption(VIEW_MEDICAL_RECORD, "View Medical Record");
        addOption(USER_CONFIGURATION, "Edit Personal Information");
        addOption(APPOINTMENT_SYSTEM, "View Available Appointment Slots");
        addOption(VIEW_PAST_APPOINTMENT_OUTCOME_RECORDS, "View Past Appointment Outcome Records");
    }

    /**
     * Called when the screen is displayed.
     * Initialize the {@code Patient} object
     * and displays a welcome message with the login user's name
     */
    @Override
    public void onStart() {
        patient = (Patient) getLoginManager().getCurrentlyLoggedInUser();
        println("Welcome, " + patient.getName());
        super.onStart();
    }

    /**
     * Defines the behaviour when patients logout, redirect to main screen
     */
    @Override
    protected void onLogout() {
        newScreen(new MainScreen());
    }


    /**
     * Handles the selection of menu options based on the specified {@code optionId}
     * @param optionId The ID of the option selected by the user.
     */
    @Override
    protected void handleOption(int optionId) {
        switch (optionId) {
            case VIEW_MEDICAL_RECORD -> viewMedicalRecord();
            case USER_CONFIGURATION -> navigateToScreen(new PatientConfigurationScreen(patient));
            case APPOINTMENT_SYSTEM -> {
                Map<LocalDate, List<Availability>> avail = availabilityManager.getAllAvailability().stream().collect(groupingBy(Availability::getAvailableDate));
                navigateToScreen(new ViewAvailableDoctorScreen());
            }
            case VIEW_PAST_APPOINTMENT_OUTCOME_RECORDS -> viewPastOutcomeRecords();
        }
    }

    /**
     * Navigates to MedicalRecordScreen to display the patient's personal information and medical record
     * including appointment outcome records associated with the patients
     */
    private void viewMedicalRecord() {
        List<AppointmentOutcomeRecord> appointmentOutcomeRecords = appointmentManager.getAppointmentOutcomeRecordsByPatient(patient);
        navigateToScreen(new MedicalRecordScreen(appointmentOutcomeRecords));
    }

    /**
     * Navigate to PastOutcomeRecordsScreen to display the past appointment outcome records
     */
    private void viewPastOutcomeRecords() {
        List<AppointmentOutcomeRecord> appointmentOutcomeRecords = appointmentManager.getAppointmentOutcomeRecordsByPatient(patient);
        navigateToScreen(new PastOutcomeRecordsScreen(appointmentOutcomeRecords));
    }
}
