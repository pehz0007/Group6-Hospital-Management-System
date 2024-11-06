package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.managers.auth.LoginManager;
import com.group6.hms.app.managers.auth.LoginManagerHolder;
import com.group6.hms.app.managers.careprovider.CareProvider;
import com.group6.hms.app.managers.careprovider.CareProviderHolder;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;
import com.group6.hms.framework.screens.pagination.SinglePaginationTableScreen;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * The {@code PatientMedicalRecordsScreen} class displays a paginated view of
 * patient medical records for the currently logged-in doctor. It allows the
 * doctor to view and update patient medical records.
 *
 * <p>This class extends {@link SinglePaginationTableScreen} to provide a paginated view of records</p>
 */
public class PatientMedicalRecordsScreen extends SinglePaginationTableScreen<UUID> {

    /**
     * The login manager responsible for handling user sessions and authentication.
     */
    LoginManager loginManager = LoginManagerHolder.getLoginManager();

    /**
     * The currently logged-in doctor.
     */
    Doctor doc = DoctorScreen.getDoctor();

    /**
     * Constructs a PatientMedicalRecordsScreen with the specified header
     * and list of patient medical record IDs.
     *
     * @param header The title of the screen.
     * @param items A list of UUIDs representing patient medical records.
     */
    public PatientMedicalRecordsScreen(String header, List<UUID> items) {
        super(header, items);
        addOption(2, "Update Patient Medical Record");
    }

    /**
     * Constructor to initialize the screen and retrieves the medical records of patients
     * under the care of the currently logged-in doctor.
     */
    @Override
    public void onStart() {
        super.onStart();

        CareProvider retrievePatients = CareProviderHolder.getCareProvider();
        Collection<UUID> medicalRecords = retrievePatients.getPatientIDsUnderDoctorCare(doc);
    }

    /**
     * Handles the selected option from the menu.
     *
     * @param optionId The ID of the selected option.
     */
    protected void handleOption(int optionId) {
        switch (optionId) {
            case 2: {
                setCurrentTextConsoleColor(ConsoleColor.PURPLE);
                print("Patient ID:");
                String userId = readString();
                Patient user = (Patient) loginManager.findUser(userId);
                if(user != null) {
                    navigateToScreen(new UpdatePatientMedicalScreen(user));
                }else{
                    println("\u001B[31m Cannot find user. Please enter again");
                }
                break;
            }
        }
    }

    /**
     * Displays a single patient's medical record in a vertical table format.
     *
     * @param item The UUID of the patient whose record is to be displayed.
     */
    @Override
    public void displaySingleItem(UUID item) {
        Patient user = (Patient) loginManager.findUser(item);
        PatientView patient = new PatientView(user);

        PrintTableUtils.printItemAsVerticalTable(consoleInterface, patient);
    }
}
