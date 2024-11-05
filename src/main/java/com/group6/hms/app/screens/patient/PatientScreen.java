package com.group6.hms.app.screens.patient;

import com.group6.hms.app.managers.AppointmentManager;
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

public class PatientScreen extends LogoutScreen {
    private static final int VIEW_MEDICAL_RECORD = 2;
    private static final int USER_CONFIGURATION = 3;
    private static final int APPOINTMENT_SYSTEM = 4;
    private static final int VIEW_PAST_APPOINTMENT_OUTCOME_RECORDS = 5;

    private Patient patient;
    private AvailabilityManager availabilityManager = AvailabilityManagerHolder.getAvailabilityManager();

    /**
     * Constructor to initialize the PatientScreen.
     */
    public PatientScreen() {
        super("Patient Menu");
        addOption(VIEW_MEDICAL_RECORD, "View Medical Record");
        addOption(USER_CONFIGURATION, "Edit Personal Information");
        addOption(APPOINTMENT_SYSTEM, "View Available Appointment Slots");
        addOption(VIEW_PAST_APPOINTMENT_OUTCOME_RECORDS, "View Past Appointment Outcome Records");
    }

    @Override
    public void onStart() {
        patient = (Patient) getLoginManager().getCurrentlyLoggedInUser();
        println("Welcome, " + patient.getName());
        super.onStart();
    }

    @Override
    protected void onLogout() {
        newScreen(new MainScreen());
    }

    @Override
    protected void handleOption(int optionId) {
        switch (optionId){
            case VIEW_MEDICAL_RECORD -> viewMedicalRecord();
            case USER_CONFIGURATION -> navigateToScreen(new PatientConfigurationScreen(patient));
            case APPOINTMENT_SYSTEM -> {
                Map<LocalDate, List<Availability>> avail = availabilityManager.getAllAvailability().stream().collect(groupingBy(Availability::getAvailableDate));
                navigateToScreen(new ViewAvailableDoctorScreen());
            }
            case VIEW_PAST_APPOINTMENT_OUTCOME_RECORDS -> viewPastOutcomeRecords();
        }
    }

    private void viewMedicalRecord() {
        AppointmentManager appointmentManager = new AppointmentManager();
        List<AppointmentOutcomeRecord> appointmentOutcomeRecords = appointmentManager.getAppointmentOutcomeRecordsByPatient(patient);
        navigateToScreen(new MedicalRecordScreen(appointmentOutcomeRecords));
    }

    private void viewPastOutcomeRecords() {
        AppointmentManager appointmentManager = new AppointmentManager();
        List<AppointmentOutcomeRecord> appointmentOutcomeRecords = appointmentManager.getAppointmentOutcomeRecordsByPatient(patient);
        navigateToScreen(new PastOutcomeRecordsScreen(appointmentOutcomeRecords));
    }
}
