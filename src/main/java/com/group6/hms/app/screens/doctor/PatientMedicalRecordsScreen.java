package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.auth.LogoutScreen;
import com.group6.hms.app.screens.MainScreen;

public class PatientMedicalRecordsScreen extends LogoutScreen {
    String patient_name;
    int num;
    public PatientMedicalRecordsScreen() {
        super("Patient Medical Records");
    }
    public PatientMedicalRecordsScreen(String patient_name, int num) {
        super("Patient Medical Records");
        this.patient_name = patient_name;
        this.num = num;
        if(this.num == 2){
            viewPatientMedicalRecord(this.patient_name);
        }else{
            updatePatientMedicalRecord(this.patient_name);
        }
    }

    public void viewPatientMedicalRecord(String patient_name){

    }

    public boolean updatePatientMedicalRecord(String patient_name){
        return false;
    }

    protected void onLogout() {
        navigateBack();
    }

    @Override
    protected void handleOption(int optionId) {

    }
}
