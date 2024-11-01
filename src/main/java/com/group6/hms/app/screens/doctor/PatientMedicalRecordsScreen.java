package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.auth.LoginManagerHolder;
import com.group6.hms.app.auth.User;
import com.group6.hms.app.models.CareProvider;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.Screen;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;
import com.group6.hms.framework.screens.pagination.SinglePaginationTableScreen;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class PatientMedicalRecordsScreen extends SinglePaginationTableScreen<UUID> {
    LoginManager loginManager = LoginManagerHolder.getLoginManager();
    Doctor doc = (Doctor) loginManager.getCurrentlyLoggedInUser();

    public PatientMedicalRecordsScreen(String header, List<UUID> items) {
        super(header, items);
        addOption(2, "Update Patient Medical Record");
    }

    /**
     * Constructor to initialize the screen with a title.
     *

     */




    @Override
    public void onStart() {
        super.onStart();

        CareProvider retrievePatients = new CareProvider();
        Collection<UUID> medicalRecords = retrievePatients.getPatientIDsUnderDoctorCare(doc);
    }

    protected void handleOption(int optionId) {
        switch (optionId) {
            case 2: {
                setCurrentTextConsoleColor(ConsoleColor.PURPLE);
                print("Patient ID:");
                String userId = readString();
                Patient user = (Patient) loginManager.findUser(userId);
                navigateToScreen(new UpdatePatientMedicalScreen(user));
                break;
            }
        }
    }



    @Override
    public void displaySingleItem(UUID item) {
        Patient user = (Patient) loginManager.findUser(item);
        PatientView patient = new PatientView(user);

        PrintTableUtils.printItemAsVerticalTable(consoleInterface, patient);
    }
}
