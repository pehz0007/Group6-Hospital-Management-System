package com.group6.hms.app.screens.patient;

import com.group6.hms.app.managers.appointment.models.AppointmentOutcomeRecord;
import com.group6.hms.app.managers.auth.LoginManager;
import com.group6.hms.app.managers.auth.LoginManagerHolder;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;
import com.group6.hms.framework.screens.pagination.SinglePaginationTableScreen;
import com.group6.hms.app.screens.pharmacist.AppointmentOutcomeRecordView;

import java.util.List;

/**
 * The {@code PastOutcomeRecordScreen} class displays a list of past appointment outcome for the patient.
 * The class retreives the currently logged-in patient from {@code LoginManager} to access and display their data.
 * The class extends {@link SinglePaginationTableScreen} to show these records in a paginated format.
 *
 * <p>This screen allows patient to view the detials of their past appointments,
 * including the outcome of each consultation.</p>
 */
public class PastOutcomeRecordsScreen extends SinglePaginationTableScreen<AppointmentOutcomeRecord>{
    private final LoginManager loginManager = LoginManagerHolder.getLoginManager();
    Patient patient;

    /**
     * Construct a new {@code PastOutcomeRecordsScreen} with the specified list of appointment outcome records.
     *
     * @param records A list of {@link AppointmentOutcomeRecord} objects representing past appointment outcome records.
     */
    public PastOutcomeRecordsScreen(List<AppointmentOutcomeRecord> records) {
        super("Appointment Outcome Record",records);
    }

    /**
     * This method is called when the screen is started.
     * It retrieves the currently logged-in patient from {@code LoginManager} and stores it for further use.
     */
    @Override
    public void onStart(){
        super.onStart();
        patient = (Patient) loginManager.getCurrentlyLoggedInUser();
    }

    /**
     * Displays a appointment outcome record each in one page in a vertical table format.
     * This method is called when rendering each individual item from the list of records.
     *
     * @param item The {@link AppointmentOutcomeRecord} to be displayed.
     */
    @Override
    public void displaySingleItem(AppointmentOutcomeRecord item) {
        PrintTableUtils.printItemAsVerticalTable(consoleInterface,new AppointmentOutcomeRecordView(item));
    }
}
