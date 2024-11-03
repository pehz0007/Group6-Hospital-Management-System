package com.group6.hms.app.screens.pharmacist;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.auth.LoginManagerHolder;
import com.group6.hms.app.models.AppointmentOutcomeRecord;
import com.group6.hms.app.models.AppointmentService;
import com.group6.hms.app.models.MedicationStatus;
import com.group6.hms.app.models.PrescribedMedication;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AppointmentOutcomeRecordView {

    private UUID recordId;
    private UUID doctorId;
    private String doctorName;
    private UUID patientId;
    private String patientName;
    private LocalDate dateOfAppointment;
    private AppointmentService serviceType;
    private List<PrescribedMedication> prescribedMedications;
    private String consultationNotes;
    private MedicationStatus medicationStatus;

    public AppointmentOutcomeRecordView(AppointmentOutcomeRecord appointmentOutcomeRecord) {
        this.recordId = appointmentOutcomeRecord.getRecordId();
        this.doctorId = appointmentOutcomeRecord.getDoctorId();
        this.doctorName = LoginManagerHolder.getLoginManager().findUser(this.doctorId).getName();
        this.patientId = appointmentOutcomeRecord.getPatientId();
        this.patientName = LoginManagerHolder.getLoginManager().findUser(this.patientId).getName();
        this.dateOfAppointment = appointmentOutcomeRecord.getDateOfAppointment();
        this.serviceType = appointmentOutcomeRecord.getServiceType();
        this.prescribedMedications = appointmentOutcomeRecord.getPrescribedMedications();
        this.consultationNotes = appointmentOutcomeRecord.getConsultationNotes();
        this.medicationStatus = appointmentOutcomeRecord.getMedicationStatus();
    }

}
