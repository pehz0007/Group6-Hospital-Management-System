package com.group6.hms.app.roles;

import com.group6.hms.app.models.Schedule;
import com.group6.hms.app.models.Appointment;


public class Doctor extends Staff {
//    private ArrayList<PatientRole> patientRoles;
//    private Schedule schedules; // binary tree and hash table


    public Doctor(String userId, char[] password, String name, Gender gender , int age) {
        super(userId, password, name, gender, age);
//        patientRoles = new ArrayList<>();
//        schedules = new Schedule();


    }

    @Override
    public String getRoleName() {
        return "Doctor";
    }

    //    public PatientRole getPatient() {
//        // search through the patientlist,
//        return null;
//    }
    public void getAllPatients() {
    }
//
//    public void addPatient(PatientRole patient) {
//
//    }
//
//    public void viewPatient(PatientRole patient) {
//        // use getPatient method to get the class
//        // Patient.getMedicalRecords
//    }
//
//    public boolean updatePatient(PatientRole patient) {
//        return false; // never update properly
//    }

    public void viewSchedule(Schedule schedule) {

    }

//    public boolean viewPersonalSchedule() {
//        ArrayList<Appointment> schedules= new ArrayList<>();
//        schedules = this.schedules.getAppointments();
//        this.schedules.viewAppointments(schedules);
//        return false;
//    }

    public void setAvailability(){

    }

    public boolean updateStatus() {
        return false; // update unsuccessful
    }

    public void viewUpcomingAppointments() {
        // get upcoming appointments from the schedule class
    }

    public void recordAppointmentOutcome(Appointment appointment) {
        //update status
    }



    @Override
    public String toString() {
        return "Doctor";
    }


}
