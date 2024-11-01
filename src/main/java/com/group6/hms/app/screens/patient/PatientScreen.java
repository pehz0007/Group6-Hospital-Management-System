package com.group6.hms.app.screens.patient;

import com.group6.hms.app.managers.AppointmentManager;
import com.group6.hms.app.managers.AvailabilityManager;
import com.group6.hms.app.models.Appointment;
import com.group6.hms.app.models.AppointmentOutcomeRecord;
import com.group6.hms.app.models.Availability;
import com.group6.hms.app.models.MedicalRecord;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.app.screens.MainScreen;
import com.group6.hms.app.auth.LogoutScreen;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;


import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Map;


public class PatientScreen extends LogoutScreen {
    private Patient patient;
    private AppointmentManager appointmentManager = new AppointmentManager();
    private AvailabilityManager availabilityManager = new AvailabilityManager();
    private Scanner scanner = new Scanner(System.in);

    private static final int MEDICAL_RECORD = 2;
    private static final int USER_CONFIGURATION = 3;
    private static final int VIEW_AVAILABLE_APPOINTMENTS = 4;
    private static final int SCHEDULE_APPOINTMENTS = 5;
    private static final int RESCHEDULE_APPOINTMENTS = 6;
    private static final int CANCEL_APPOINTMENTS = 7;
    private static final int APPOINTMENTS_STATUS = 8;
    private static final int VIEW_PAST_OUTCOMES = 9;

    /**
     * Constructor to initialize the PatientScreen.
     */
    public PatientScreen() {
        super("Patient Menu");
        addOption(MEDICAL_RECORD, "Show Medical Record");
        addOption(USER_CONFIGURATION, "Edit User Profile");
        addOption(VIEW_AVAILABLE_APPOINTMENTS, "View Available Appointment Slots");
        addOption(SCHEDULE_APPOINTMENTS, "Schedule Appointments");
        addOption(RESCHEDULE_APPOINTMENTS, "Reschedule Appointments");
        addOption(CANCEL_APPOINTMENTS, "Cancel Appointments");
        addOption(APPOINTMENTS_STATUS, "View Scheduled Appointments Status");
        addOption(VIEW_PAST_OUTCOMES, "View Past Appointment Outcome Records");
    }

    @Override
    public void onStart() {
        println("Welcome, " + getLoginManager().getCurrentlyLoggedInUser().getUserId());
        patient = (Patient) getLoginManager().getCurrentlyLoggedInUser();
        super.onStart();
    }

    @Override
    protected void onLogout() {
        newScreen(new MainScreen());
    }

    @Override
    protected void handleOption(int optionId) {
        switch (optionId){
            case USER_CONFIGURATION -> navigateToScreen(new PatientConfigurationScreen(patient));
            case MEDICAL_RECORD -> viewMedicalRecord();
            case VIEW_AVAILABLE_APPOINTMENTS -> viewAvailableAppointments();
            case SCHEDULE_APPOINTMENTS -> scheduleAppointments();
            case RESCHEDULE_APPOINTMENTS -> rescheduleAppointments();
            case CANCEL_APPOINTMENTS -> cancelAppointments();
            case APPOINTMENTS_STATUS -> viewAppointmentsStatus();
            case VIEW_PAST_OUTCOMES -> viewPastOutcomes();
            default -> println("Invalid option");
        }
    }

    private void viewMedicalRecord() {
        MedicalRecord medicalRecord = patient.getMedicalRecord();
        if (medicalRecord != null){
            PatientDataView patientDataView = new PatientDataView(patient);
            PrintTableUtils.printItemAsVerticalTable(consoleInterface, patientDataView);
        }else{
            println("No medical record found.");
        }
    }

    private void viewAvailableAppointments() {
        Map<LocalDate, List<Availability>> events = availabilityManager.getAllAvailability().stream()
                .collect(Collectors.groupingBy(Availability::getAvailableDate));
        navigateToScreen(new PatientAppointmentScreen(events, VIEW_AVAILABLE_APPOINTMENTS));
    }

    private void scheduleAppointments() {
        Map<LocalDate, List<Availability>> events = availabilityManager.getAllAvailability().stream()
                .collect(Collectors.groupingBy(Availability::getAvailableDate));
        navigateToScreen(new PatientAppointmentScreen(events, SCHEDULE_APPOINTMENTS));
    }

    private void rescheduleAppointments() {
        List<Appointment> appointments = appointmentManager.getAppointmentsByPatient(patient);
        if(appointments.isEmpty()){
            println("No appointments found to be rescheduled.");
            return;
        }
        //navigateToScreen(new AppointmentDetailsScreen());
    }

    private void cancelAppointments() {
        List<Appointment> appointments = appointmentManager.getAppointmentsByPatient(patient);
        if(appointments.isEmpty()){
            println("No scheduled appointments found to be cancelled.");
            return;
        }
        //navigateToScreen(new AppointmentDetailsScreen());
    }

    private void viewAppointmentsStatus() {
        List<Appointment> appointments = appointmentManager.getAppointmentsByPatient(patient);

        if (appointments.isEmpty()){
            println("No appointments found.");
            return;
        }

        println("Your Scheduled Appointments Status:");
        for (Appointment appointment : appointments){
            println("Doctor: " + appointment.getDoctor().getName() +
                    " |Date: " + appointment.getDate() +
                    " |Time: " + appointment.getStartTime() + "-" + appointment.getEndTime() +
                    " |Status: " + appointment.getStatus());
        }
    }

    private void viewPastOutcomes() {
        List<AppointmentOutcomeRecord> pastAppointments = appointmentManager.getAppointmentOutcomeRecordsByPatient(patient);

        if (pastAppointments.isEmpty()){
            println("No past appointments found.");
            return;
        }

        println("Your Past Appointment Outcomes:");
        for(AppointmentOutcomeRecord record : pastAppointments){
            println("Date: " + record.getRecordId() +
                    " |Doctor: " + record.getDoctorId());
        }
    }
}
