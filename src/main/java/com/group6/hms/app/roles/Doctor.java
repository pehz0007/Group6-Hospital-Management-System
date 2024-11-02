package com.group6.hms.app.roles;

import com.group6.hms.app.models.Schedule;
import com.group6.hms.app.models.Appointment;

/**
 * The {@code Doctor} class represents a doctor role in the hospital management system.
 * It extends the {@link Staff} class and provides specific behaviors and attributes related
 * to doctors within the system.
 */
public class Doctor extends Staff {
    // private ArrayList<PatientRole> patientRoles; // List of patient roles associated with the doctor
    // private Schedule schedules; // Schedule containing appointments (binary tree and hash table)

    /**
     * Constructs a {@code Doctor} with the specified user details.
     *
     * @param userId   the unique user ID of the doctor
     * @param password the password of the doctor, represented as a character array
     * @param name     the name of the doctor
     * @param gender   the gender of the doctor, represented as a {@link Gender} enum
     * @param age      the age of the doctor
     */
    public Doctor(String userId, char[] password, String name, Gender gender, int age) {
        super(userId, password, name, gender, age);
        // patientRoles = new ArrayList<>();
        // schedules = new Schedule();
    }

    /**
     * Returns the role name of this doctor.
     *
     * @return the role name as a {@code String}, specifically "Doctor"
     */
    @Override
    public String getRoleName() {
        return "Doctor";
    }

//    /**
//     * Retrieves a list of all patients associated with this doctor.
//     */
//    public void getAllPatients() {
//        // Method implementation goes here
//    }
//
//    /**
//     * Views the schedule for a specified schedule instance.
//     *
//     * @param schedule the schedule to be viewed
//     */
//    public void viewSchedule(Schedule schedule) {
//        // Method implementation goes here
//    }
//
//    /**
//     * Sets the availability of the doctor for appointments.
//     */
//    public void setAvailability() {
//        // Method implementation goes here
//    }
//
//    /**
//     * Updates the status of an unspecified operation.
//     *
//     * @return {@code true} if the update is successful; otherwise {@code false}
//     */
//    public boolean updateStatus() {
//        return false; // update unsuccessful
//    }
//
//    /**
//     * Views upcoming appointments from the doctor's schedule.
//     */
//    public void viewUpcomingAppointments() {
//        // Method implementation goes here
//    }
//
//    /**
//     * Records the outcome of a specific appointment.
//     *
//     * @param appointment the appointment whose outcome is to be recorded
//     */
//    public void recordAppointmentOutcome(Appointment appointment) {
//        // Method implementation goes here
//    }

    /**
     * Returns a string representation of the doctor, displaying their name.
     *
     * @return the name of the doctor as a {@code String}
     */
    @Override
    public String toString() {
        return getName();
    }
}
