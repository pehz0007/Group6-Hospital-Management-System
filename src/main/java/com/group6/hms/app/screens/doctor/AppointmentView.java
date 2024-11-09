package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.managers.inventory.models.MedicationStatus;
import com.group6.hms.app.managers.auth.LoginManager;
import com.group6.hms.app.managers.auth.LoginManagerHolder;
import com.group6.hms.app.managers.appointment.models.AppointmentOutcomeRecord;
import com.group6.hms.app.managers.appointment.models.AppointmentService;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.app.roles.Patient;

/**
 * The {@code AppointmentView} class represents a simplified view of an appointment's outcome,
 * including details about the doctor, patient, consultation notes, and medication status.
 */
public class AppointmentView {

    /** The name of the doctor associated with the appointment. */
    private String doctorName;

    /** The name of the patient associated with the appointment. */
    private String PatientName;

    /** The type of service provided during the appointment. */
    private AppointmentService serviceType;

    // /** A list of prescribed medications for the appointment. */
    //private List<PrescribedMedication> prescribedMedicationList;

    /** The consultation notes recorded by the doctor for the appointment. */
    private String consultationNotes;

    /** The status of medication prescribed during the appointment. */
    private MedicationStatus medicationStatus;

    /**
     * Constructs an {@code AppointmentView} from the specified {@link AppointmentOutcomeRecord}.
     * Retrieves doctor and patient details based on IDs in the appointment record and initializes
     * fields with appointment outcome information, including service type, consultation notes,
     * and medication status.
     *
     * @param appointment the {@code AppointmentOutcomeRecord} containing details of the appointment outcome
     */
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