package com.group6.hms.app.screens.patient;

import com.group6.hms.app.managers.appointment.models.AppointmentOutcomeRecord;
import com.group6.hms.app.managers.auth.LoginManager;
import com.group6.hms.app.managers.auth.LoginManagerHolder;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;
import com.group6.hms.framework.screens.pagination.SinglePaginationTableScreen;

import java.util.List;

/**
 * The {@code MedicalRecordScreen} class displays the patient's personal information and medical records.
 * The class extends {@link SinglePaginationTableScreen} to provide a paginated view of the records.
 * {@code AppointmentOutcomeRecord} entries, each representing the details and outcome of a past medical appointment for the patient.
 *
 * <p>This screen also retrieves the currently logged-in patient from the {@code LoginManager}
 * to provide access to the patient's data.</p>
 */
public class MedicalRecordScreen extends SinglePaginationTableScreen<AppointmentOutcomeRecord>{
    private final LoginManager loginManager = LoginManagerHolder.getLoginManager();
    private Patient patient;

    /**
     * Constructs a {@code MedicalRecordScreen} with a list of {@code AppointmentOutcomeRecord} entries
     * to be displayed for the patient.
     *
     * @param records List of appointment outcome records associated with the patient.
     */
    public MedicalRecordScreen(List<AppointmentOutcomeRecord> records) {
        super("Medical Record",records);
    }

    /**
     * Called when the screen starts.
     * Retrives the currently logged-in user from the {@code LoginManager} and assigns it to the {@code patient} field.
     * Allow the screen has access to the specific patient's details for displaying their personal information and past medical records.
     */
    @Override
    public void onStart(){
        super.onStart();
        patient = (Patient) loginManager.getCurrentlyLoggedInUser();
    }

    /**
     * Displays the details of the past appointment records from {@code AppointmentOutcomeRecord}
     * The details of this record are shown in a vertical table format using the {@code PrintTableUtils} utility.
     *
     * @param item the item to be displayed.
     */
    @Override
    public void displaySingleItem(AppointmentOutcomeRecord item) {
        PrintTableUtils.printItemAsVerticalTable(consoleInterface,new PatientMedicalRecordDataView(patient, item));
    }
}
