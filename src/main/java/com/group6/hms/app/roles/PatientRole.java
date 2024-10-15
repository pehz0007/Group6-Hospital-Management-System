package com.group6.hms.app.roles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.group6.hms.framework.auth.Role;
import com.group6.hms.app.models.MedicalRecord;
import com.group6.hms.app.models.Appointment;

public class PatientRole extends Role {
    private String patientId;
    private String patientName;
    private LocalDate dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String emailAddress;
    private String bloodType;
    private MedicalRecord medicalRecords; //past diagnoses & treatment
    private List<Appointment> appointments;

    public PatientRole() {
        this.patientId = null;
        this.patientName = null;
        this.dateOfBirth = null;
        this.gender = null;
        this.phoneNumber = null;
        this.emailAddress = null;
        this.bloodType = null;
        this.medicalRecords = new MedicalRecord();
        this.appointments = new ArrayList<>();
    }

    //view medical records
    public void viewMedicalRecords() {
        System.out.println("Patient ID: " + this.patientId);
        System.out.println("Patient Name: " + this.patientName);
        System.out.println("Date of Birth: " + this.dateOfBirth);
        System.out.println("Gender: " + this.gender);
        System.out.println("Phone Number: " + this.phoneNumber);
        System.out.println("Email Address: " + this.emailAddress);
        System.out.println("Blood Type: " + this.bloodType);
        System.out.println("Medical Records: ");
        System.out.println(this.medicalRecords);
    }

    //update non-medical personal information
    public void updateInfo(String newPhoneNumber, String newEmailAddress){
        if(newPhoneNumber != null && !newPhoneNumber.isEmpty()){
            if(newPhoneNumber.equals(this.phoneNumber)){
                System.out.println("The phone number you entered is the same as the current phone number.");
            }else{
                this.phoneNumber = newPhoneNumber;
                System.out.println("New phone number updated successfully.");
            }
        }else{
            System.out.println("Invalid phone number.");
        }

        if(newEmailAddress != null && !newEmailAddress.isEmpty()){
            if(newEmailAddress.equals(this.emailAddress)){
                System.out.println("The email address you entered is the same as the current email address.");
            }else if(isValidEmail(newPhoneNumber)){
                this.emailAddress = newEmailAddress;
                System.out.println("New email address updated successfully.");
            }else{
                System.out.println("Invalid email address.");
            }
        }else{
            System.out.println("Invalid email address.");
        }
    }

    //check if it's valid email
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void viewAvailableAppointments(){

    }

    public void scheduleAppointments(){

    }

    public void rescheduleAppointments(){

    }

    public void cancelAppointments(){

    }

    public void viewPastOutcomes(){

    }

    @Override
    public String toString() {
        return "Patient";
    }
}
