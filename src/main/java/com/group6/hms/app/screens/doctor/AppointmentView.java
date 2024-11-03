package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.managers.inventory.models.MedicationStatus;
import com.group6.hms.app.managers.auth.LoginManager;
import com.group6.hms.app.managers.auth.LoginManagerHolder;
import com.group6.hms.app.managers.appointment.models.AppointmentOutcomeRecord;
import com.group6.hms.app.managers.appointment.models.AppointmentService;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.roles.Patient;

public class AppointmentView {
    private String doctorName;
    private String PatientName;
    private AppointmentService serviceType;
    //private List<PrescribedMedication> prescribedMedicationList;
    private String consultationNotes;
    private MedicationStatus medicationStatus;




    public AppointmentView(AppointmentOutcomeRecord appointment) {
        LoginManager loginManager = LoginManagerHolder.getLoginManager();

        Doctor doc = (Doctor) loginManager.findUser(appointment.getDoctorId());
        Patient patient = (Patient) loginManager.findUser(appointment.getPatientId());
        this.doctorName = doc.getName();;
        this.PatientName = patient.getName();
        this.serviceType = appointment.getServiceType();
        //this.prescribedMedicationList = appointment.getPrescribedMedications();
        this.consultationNotes = appointment.getConsultationNotes();
        this.medicationStatus = appointment.getMedicationStatus();
    }
}
