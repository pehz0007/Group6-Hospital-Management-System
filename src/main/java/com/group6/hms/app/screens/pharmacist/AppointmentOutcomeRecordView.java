package com.group6.hms.app.screens.pharmacist;

import com.group6.hms.app.managers.auth.LoginManagerHolder;
import com.group6.hms.app.managers.appointment.models.AppointmentOutcomeRecord;
import com.group6.hms.app.managers.appointment.models.AppointmentService;
import com.group6.hms.app.managers.inventory.models.MedicationStatus;
import com.group6.hms.app.managers.inventory.models.PrescribedMedication;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * The {@code AppointmentOutcomeRecordView} displays the outcome record of an appointment,
 * encapsulating details such as doctor and patient information, appointment date,
 * service type, prescribed medications, consultation notes, and medication status.
 */
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

    /**
     * Constructor to initialize the AppointmentOutcomeRecordView with details from an AppointmentOutcomeRecord.
     *
     * @param appointmentOutcomeRecord the appointment outcome record containing relevant data
     */
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
