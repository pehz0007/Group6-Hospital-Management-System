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

import java.util.List;

public class PatientScreen extends LogoutScreen {


    private static final int MEDICAL_RECORD = 2;
    private static final int USER_CONFIGURATION = 3;
    private static final int VIEW_AVAILABLE_APPOINTMENTS = 4;
    private static final int SCHEDULE_APPOINTMENTS = 5;
    private static final int RESCHEDULE_APPOINTMENTS = 6;
    private static final int CANCEL_APPOINTMENTS = 7;
    private static final int APPOINTMENTS_STATUS = 8;
    private static final int VIEW_PAST_OUTCOMES = 9;
    private static final int PATIENT_BOOK_AVAILABLE_DOCTORS = 10;

    private Patient patient;
    private AvailabilityManager availabilityManager;
    private AppointmentManager appointmentManager;

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
        addOption(PATIENT_BOOK_AVAILABLE_DOCTORS, "Book Available Doctors");

        availabilityManager = new AvailabilityManager();
        appointmentManager = new AppointmentManager();
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
            case USER_CONFIGURATION -> navigateToScreen(new PatientConfigurationScreen(patient));
            case MEDICAL_RECORD -> viewMedicalRecord();
            case VIEW_AVAILABLE_APPOINTMENTS -> viewAvailableAppointments();
            //case SCHEDULE_APPOINTMENTS -> scheduleAppointments(selectedSlot);
            case RESCHEDULE_APPOINTMENTS -> rescheduleAppointments();
            case CANCEL_APPOINTMENTS -> cancelAppointments();
            case APPOINTMENTS_STATUS -> viewAppointmentsStatus();
            case VIEW_PAST_OUTCOMES -> viewPastOutcomes();
            case  PATIENT_BOOK_AVAILABLE_DOCTORS -> navigateToScreen(new ViewAvailableDoctorScreen());
            default -> println("Invalid option");
        }
    }

    private void viewMedicalRecord() {
        MedicalRecord medicalRecord = patient.getMedicalRecord();
        if (medicalRecord != null){
            println("Medical Record:");
            //println("Patient ID: " + patient.getPatientId());
            println("Patient Name: " + patient.getName());
            println("Date Of Birth: " + medicalRecord.getDateOfBirth());
            println("Gender: " + patient.getGender());
            println("Blood Type: " + medicalRecord.getBloodType());
            //println("Phone Number: " + patient.getPhoneNumber());
            println("Email: " + patient.getContactInformation());
        }else{
            println("No medical record found.");
        }
    }

    private void viewAvailableAppointments() {
        List<Availability> availabilities = availabilityManager.getAllAvailability();
        if(availabilities.isEmpty()){
            println("No available appointments slots.");
            return;
        }

        println("Available Appointment Slots:");
        for (int i = 0; i < availabilities.size(); i++){
            Availability availability = availabilities.get(i);
            println((i+1) + ". Doctor: " + availability.getDoctor().getName() +
                    " |Date: " + availability.getAvailableDate() +
                    " |Time: " + availability.getAvailableStartTime() + "-" + availability.getAvailableEndTime());
        }

        println("Select a slot number to schedule an appointment:");
        int selectSlot = readInt();
        if (selectSlot < 1 || selectSlot > availabilities.size()){
            println("Invalid slot number. Please try again.");
            return;
        }

        Availability selectedSlot = availabilities.get(selectSlot - 1);
        boolean scheduleSuccess = scheduleAppointments(selectedSlot);
        if(!scheduleSuccess){
            println("Failed to schedule an appointment. The slot may no longer be available.");
            println("Would you like to try another slots? (yes/no)");
            String answer = readString().trim().toLowerCase();
            if (answer.equals("yes")){
                viewAvailableAppointments();
            }
        }

    }

    private boolean scheduleAppointments(Availability selectedSlot) {
        try {
            appointmentManager.scheduleAppointment(patient, selectedSlot);
            println("Appointment scheduled successfully." +
                    " |Doctor: Dr. " + selectedSlot.getDoctor().getName() +
                    " |Date: " + selectedSlot.getAvailableDate() +
                    " |Time: " + selectedSlot.getAvailableStartTime() + "-" + selectedSlot.getAvailableEndTime());
            return true;
        }catch (Exception e){
            println("Failed to schedule an appointment. Please try again.");
            return false;
        }
    }

    private void rescheduleAppointments() {
        List<Appointment> appointments = appointmentManager.getAppointmentsByPatient(patient);
        if (appointments.isEmpty()){
            println("No appointments found.");
            return;
        }

        println("Your Scheduled Appointments:");
        for (int i = 0; i < appointments.size(); i++){
            Appointment appointment = appointments.get(i);
            println((i+1) + ". Doctor: " + appointment.getDoctor().getName() +
                    " |Date: " + appointment.getDate() +
                    " |Time: " + appointment.getStartTime() + "-" + appointment.getEndTime());
        }

        println("Select an appointment number to reschedule:");
        int appointmentNum = scanner.nextInt();
        if(appointmentNum < 1 || appointmentNum > appointments.size()){
            println("Invalid appointment number. Please try again.");
            return;
        }

        Appointment appointmentToReschedule = appointments.get(appointmentNum - 1);
        println("Select a new appointment slot number:");
        viewAvailableAppointments();

        List<Availability> availabilities = availabilityManager.getAllAvailability();
        int selectSlot = scanner.nextInt();
        if (selectSlot < 1 || selectSlot > availabilities.size()){
            println("Invalid slot number. Please try again.");
            return;
        }

        Availability availability = availabilities.get(selectSlot - 1);
        //boolean isRescheduled = appointmentManager.rescheduleAppointment(appointmentToReschedule, availability);

        //if(isRescheduled){
            //println("Appointment rescheduled successfully.");
        //}else{
            //println("Failed to reschedule an appointment. Please try again.");
        //}
    }

    private void cancelAppointments() {
        List<Appointment> appointments = appointmentManager.getAppointmentsByPatient(patient);

        if (appointments.isEmpty()){
            println("No appointments found.");
            return;
        }

        println("Your Scheduled Appointments:");
        for (int i = 0; i < appointments.size(); i++){
            Appointment appointment = appointments.get(i);
            println((i+1) + ". Doctor: " + appointment.getDoctor().getName() +
                    " |Date: " + appointment.getDate() +
                    " |Time: " + appointment.getStartTime() + "-" + appointment.getEndTime());
        }

        println("Select an appointment number to cancel:");
        int appointmentNum = scanner.nextInt();
        if (appointmentNum < 1 || appointmentNum > appointments.size()){
            println("Invalid appointment number. Please try again.");
            return;
        }

        Appointment appointmentToCancel = appointments.get(appointmentNum - 1);
        //boolean isCancelled = appointmentManager.cancelAppointment(appointmentToCancel);
        //if(isCancelled){
            //println("Appointment cancelled successfully.");
        //}else{
            //println("Failed to cancel an appointment. Please try again.");
        //}
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
