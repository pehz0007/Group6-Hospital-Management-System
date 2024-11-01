package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.managers.AppointmentManager;
import com.group6.hms.app.models.Appointment;
import com.group6.hms.app.models.AppointmentOutcomeRecord;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;
import com.group6.hms.framework.screens.pagination.SinglePaginationTableScreen;

import java.util.List;
import java.util.UUID;

public class ViewMoreDetailsAppointmentScreen extends SinglePaginationTableScreen<UUID> {
    AppointmentManager appointmentManager = new AppointmentManager();
    public ViewMoreDetailsAppointmentScreen( List<UUID> items) {
        super("Confirmed Appointment Details", items);
    }

    @Override
    public void displaySingleItem(UUID appointment) {
        //Patient user = (Patient) loginManager.findUser(item);
        AppointmentOutcomeRecord appt1 = appointmentManager.getAppointmentOutcomeRecordsByUUID(appointment);
        AppointmentView appt = new AppointmentView(appt1);

        PrintTableUtils.printItemAsVerticalTable(consoleInterface, appt);
    }
}
