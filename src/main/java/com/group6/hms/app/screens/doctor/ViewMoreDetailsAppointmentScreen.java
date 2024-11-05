package com.group6.hms.app.screens.doctor;


import com.group6.hms.app.managers.appointment.AppointmentManager;
import com.group6.hms.app.managers.appointment.AppointmentManagerHolder;
import com.group6.hms.app.managers.appointment.models.AppointmentOutcomeRecord;
import com.group6.hms.app.managers.inventory.models.PrescribedMedication;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;
import com.group6.hms.framework.screens.pagination.SinglePaginationTableScreen;

import java.util.List;
import java.util.UUID;

public class ViewMoreDetailsAppointmentScreen extends SinglePaginationTableScreen<UUID> {
    private AppointmentManager appointmentManager = AppointmentManagerHolder.getAppointmentManager();
    public ViewMoreDetailsAppointmentScreen( List<UUID> items) {
        super("Confirmed Appointment Details", items);
    }

    public String getPrescribedMedicationTable(AppointmentOutcomeRecord appointment) {
        StringBuilder tableBuilder = new StringBuilder();
        tableBuilder.append(String.format("%-20s %-20s%n", "Medication Name", "Dosage"));
        tableBuilder.append("-".repeat(30)).append("\n"); // Corrected line

        for (PrescribedMedication medication : appointment.getPrescribedMedications()) {
            tableBuilder.append(String.format("%-20s %-20s%n",
                    medication.getName(),
                    medication.getQuantityToPrescribe()));
        }

        return tableBuilder.toString();
    }

    @Override
    public void displaySingleItem(UUID item) {
        //Patient user = (Patient) loginManager.findUser(item);
        AppointmentOutcomeRecord appt1 = appointmentManager.getAppointmentOutcomeRecordsByUUID(item);
        AppointmentView appt = new AppointmentView(appt1);

        PrintTableUtils.printItemAsVerticalTable(consoleInterface, appt);
        System.out.println("Medicine Details");
        System.out.println(getPrescribedMedicationTable(appt1));
    }
}
