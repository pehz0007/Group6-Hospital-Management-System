package com.group6.hms.app.screens.patient;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.auth.LoginManagerHolder;
import com.group6.hms.app.models.AppointmentOutcomeRecord;
import com.group6.hms.app.roles.Patient;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;
import com.group6.hms.framework.screens.pagination.SinglePaginationTableScreen;

import java.util.List;

public class MedicalRecordScreen extends SinglePaginationTableScreen<AppointmentOutcomeRecord>{
    private final LoginManager loginManager = LoginManagerHolder.getLoginManager();
    Patient patient;

    public MedicalRecordScreen(List<AppointmentOutcomeRecord> records) {
        super("Appointment Outcome Record",records);
    }

    @Override
    public void onStart(){
        super.onStart();
        patient = (Patient) loginManager.getCurrentlyLoggedInUser();
        //this.appointmentOutcomeRecord = appointmentOutcomeRecord;
    }

    @Override
    public void displaySingleItem(AppointmentOutcomeRecord item) {
        PrintTableUtils.printItemAsVerticalTable(consoleInterface,new PatientMedicalRecordDataView(patient, item));
    }
}
