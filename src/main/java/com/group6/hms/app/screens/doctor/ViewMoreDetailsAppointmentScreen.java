package com.group6.hms.app.screens.doctor;


import com.group6.hms.app.managers.appointment.AppointmentManager;
import com.group6.hms.app.managers.appointment.AppointmentManagerHolder;
import com.group6.hms.app.managers.appointment.models.AppointmentOutcomeRecord;
import com.group6.hms.app.managers.inventory.models.PrescribedMedication;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;
import com.group6.hms.framework.screens.pagination.SinglePaginationTableScreen;

import java.util.List;
import java.util.UUID;

/**
 * The {@code ViewMoreDetailsAppointmentScreen} class provides an interface
 * to view appointment details and any prescribed medications.
 *
 * <p>This class extends {@link SinglePaginationTableScreen} to manage the display of appointment details</p>
 */
public class ViewMoreDetailsAppointmentScreen extends SinglePaginationTableScreen<UUID> {
    private AppointmentManager appointmentManager = AppointmentManagerHolder.getAppointmentManager();

    /**
     * Constructs a ViewMoreDetailsAppointmentScreen.
     *
     * @param items a list of UUIDs representing the confirmed appointments to display
     */
    public ViewMoreDetailsAppointmentScreen( List<UUID> items) {
        super("Confirmed Appointment Details", items);
    }

    /**
     * Generates a formatted table of prescribed medications from an appointment outcome record.
     *
     * @param appointment the AppointmentOutcomeRecord containing prescribed medications
     * @return a string representation of the prescribed medications table
     */
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

    /**
     * Displays detailed information for a single appointment outcome record.
     *
     * @param item The unique identifier (UUID) of the appointment outcome record to display.
     * Retrieves the record, formats it as a vertical table, and displays prescribed medication details.
     */
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
