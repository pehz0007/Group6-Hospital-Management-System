package com.group6.hms.app.screens.admin;

import com.group6.hms.app.models.Appointment;
import com.group6.hms.app.models.AppointmentOutcomeRecord;
import com.group6.hms.app.models.AppointmentStatus;
import com.group6.hms.framework.screens.ConsoleInterface;
import com.group6.hms.framework.screens.calendar.EventInterface;

import java.time.LocalDateTime;
import java.util.UUID;

public class AppointmentView extends Appointment implements EventInterface {

    public AppointmentView(UUID patientId, UUID doctorId, AppointmentStatus status, LocalDateTime dateTime) {
        super(patientId, doctorId, status, dateTime);
    }

    public AppointmentView(UUID patientId, UUID doctorId, AppointmentStatus status, LocalDateTime dateTime, AppointmentOutcomeRecord appointmentOutcomeRecord) {
        super(patientId, doctorId, status, dateTime, appointmentOutcomeRecord);
    }

    @Override
    public LocalDateTime getEventDateTime() {
        return this.getDateTime();
    }

    @Override
    public void displayEvent(ConsoleInterface consoleInterface) {
        consoleInterface.println("Appointment Date: " + this.getEventDateTime());
        consoleInterface.println("Status: " + this.getStatus());
    }
}
