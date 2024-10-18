package com.group6.hms.app.models;
import com.group6.hms.app.models.Appointment;
import java.util.ArrayList;

public class Schedule {
    private ArrayList<Appointment> appointments;

    public Schedule() {
        this.appointments = new ArrayList<>();
    }

    public void addAppointment(Appointment appointment) {
//        Appointment newAppointment = new Appointment();
//        newAppointment = appointment;
//        appointments.add(newAppointment);
    }

    public void removeAppointment(Appointment appointment) {

    }

    public void viewAppointments(ArrayList<Appointment> schedule) {

    }

    public Appointment updateAppointment(Appointment appointment) {
        return appointment;
    }

    public ArrayList<Appointment> getAppointments() {
        return this.appointments;
    }

    public ArrayList<Appointment> getUpcomingSchedule() {
        return null;
    }
}
