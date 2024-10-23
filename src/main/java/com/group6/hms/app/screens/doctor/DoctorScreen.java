package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.auth.User;
import com.group6.hms.app.models.Appointment;
import com.group6.hms.app.models.AppointmentStatus;
import com.group6.hms.app.models.Availability;
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
        println("Welcome, " + getLoginManager().getCurrentlyLoggedInUser().getUsername());
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

        Doctor doc = new Doctor("tonkatsu", "password".toCharArray());
        switch(optionId){
            case 2, 3: {
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter patient name: ");
                String name = sc.nextLine();
                navigateToScreen(new PatientMedicalRecordsScreen());
                break;
            }
            case 4: {
                println("Getting personal schedule...");
                AppointmentManager appointmentManager = new AppointmentManager();
                ArrayList<Appointment> appointments  = appointmentManager.getAppointmentsByDoctorAndStatus(doc, AppointmentStatus.CONFIRMED);
                for(Appointment appointment : appointments){
                    println(appointment.toString());
                }
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
                println("Enter availability end time: ");
                String endTime = sc.nextLine();
                LocalTime endTime1 = LocalTime.parse(endTime);
                AvailabilityManager availabilityManager = new AvailabilityManager();
                Availability avail = new Availability(doc,date1, startTime1, endTime1 );
                availabilityManager.addAvailability(avail);
                break;
            }
        }
    }
}
