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

public class PatientMedicalRecordsScreen extends SinglePaginationTableScreen<UUID> {
    LoginManager loginManager = LoginManagerHolder.getLoginManager();
    Doctor doc = DoctorScreen.getDoctor();

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

        CareProvider retrievePatients = CareProviderHolder.getCareProvider();
        Collection<UUID> medicalRecords = retrievePatients.getPatientIDsUnderDoctorCare(doc);
    }

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



    @Override
    public void displaySingleItem(UUID item) {
        Patient user = (Patient) loginManager.findUser(item);
        PatientView patient = new PatientView(user);

        PrintTableUtils.printItemAsVerticalTable(consoleInterface, patient);
    }
}
