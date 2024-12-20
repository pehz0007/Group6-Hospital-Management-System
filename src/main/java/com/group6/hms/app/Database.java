package com.group6.hms.app;

import com.group6.hms.app.managers.careprovider.CareProvider;
import com.group6.hms.app.managers.appointment.AppointmentManager;
import com.group6.hms.app.managers.appointment.AppointmentManagerHolder;
import com.group6.hms.app.managers.appointment.models.Appointment;
import com.group6.hms.app.managers.appointment.models.AppointmentOutcomeRecord;
import com.group6.hms.app.managers.appointment.models.AppointmentService;
import com.group6.hms.app.managers.auth.LoginManager;
import com.group6.hms.app.managers.auth.LoginManagerHolder;
import com.group6.hms.app.managers.availability.AvailabilityManagerHolder;
import com.group6.hms.app.managers.availability.models.Availability;
import com.group6.hms.app.managers.availability.AvailabilityManager;
import com.group6.hms.app.managers.availability.models.AvailabilityStatus;
import com.group6.hms.app.managers.careprovider.CareProviderHolder;
import com.group6.hms.app.managers.inventory.models.MedicationStatus;
import com.group6.hms.app.managers.inventory.models.PrescribedMedication;
import com.group6.hms.app.models.*;
import com.group6.hms.app.roles.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The {@code Database} class is a utility for resetting and populating the database with
 * sample data.
 * <p>Running this class will initialize entities including patients, doctors, administrators,
 *  and pharmacists, and also schedules sample appointments, availabilities, and appointment
 *  outcome records.</p>
 */
public class Database {

    /**
     * RUN THIS TO RESET DATABASE
     *
     * This method serves as the entry point for the application.
     * It initializes sample data, creates users, schedules appointments,
     * and saves/loading user data to/from a file.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) throws IOException {

        for(File file: Paths.get("./data").toFile().listFiles())
            if (!file.isDirectory())
                file.delete();

        //Generate sample file
        LoginManager loginManager = LoginManagerHolder.getLoginManager();

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setDateOfBirth(LocalDate.of(1997,8,5));
        medicalRecord.setBloodType(BloodType.AB_PLUS);
        Patient patient = new Patient("P1011", "password".toCharArray(), "freya", Gender.Male, "patient@example.com", "1234-5678");
        patient.updateMedicalRecord(medicalRecord);

        loginManager.createUser(patient);
        //loginManager.createUser(new Patient("P1011", "password".toCharArray(), "freya", Gender.Male, "patient@example.com"));
        loginManager.createUser(new Doctor("D0011", "password".toCharArray(), "ethan", Gender.Male, 22));
        loginManager.createUser(new Administrator("A001", "password".toCharArray(), "phoebe", Gender.Female, 34));
        loginManager.createUser(new Pharmacist("P0003", "password".toCharArray(), "sage", Gender.Female, 50));

        loginManager.saveUsersToFile();
        loginManager.loadUsersFromFile();
        loginManager.printUsers();

        AppointmentManager appointmentManager = AppointmentManagerHolder.getAppointmentManager();
        AvailabilityManager availabilityManager = AvailabilityManagerHolder.getAvailabilityManager();
        loginManager.loadUsersFromFile();
        Doctor doctor = (Doctor) loginManager.findUser("D0011");
        LocalTime timeNow = LocalTime.now();
        Availability avail = new Availability(doctor, LocalDate.now(), AvailabilityStatus.AVAILABLE, timeNow, timeNow.plusHours(1));
        Availability avail1 = new Availability(doctor, LocalDate.now(), AvailabilityStatus.AVAILABLE, LocalTime.parse("12:00"), LocalTime.parse("13:00"));

        availabilityManager.addAvailability(avail);
        availabilityManager.addAvailability(avail1);



        appointmentManager.scheduleAppointment(patient, avail);
        appointmentManager.scheduleAppointment(patient, avail1);


        Appointment appt = appointmentManager.getAllAppointments().getFirst();
        appointmentManager.acceptAppointmentRequest(appt);
//        ArrayList<Medication> medications = new ArrayList<>();

//        PrescribedMedication.add(new Medication(UUID.randomUUID(), "Paracetamol"));
//        PrescribedMedication.add(new Medication(UUID.randomUUID(), "Ibuprofen"));
//        PrescribedMedication.add(new Medication(UUID.randomUUID(), "Amoxicillin"));
//        AppointmentOutcomeRecord record = new AppointmentOutcomeRecord(doctor.getUserId(), patient.getUserId(), appt.getDate(), AppointmentService.CONSULT, medications, "high fever", MedicationStatus.PENDING);
//
//        appointmentManager.completeAppointment(appt,record);
//        List<AppointmentOutcomeRecord> records = appointmentManager.getAppointmentOutcomeRecordsByStatus(MedicationStatus.PENDING);


        // create a list for prescribed medications
        List<PrescribedMedication> prescribedMedications = new ArrayList<>();
        Random random = new Random();

        // add medications with random quantities (between 1-5)
        prescribedMedications.add(new PrescribedMedication("Paracetamol", random.nextInt(5) + 1));
        prescribedMedications.add(new PrescribedMedication("Ibuprofen", random.nextInt(30) + 1));
        prescribedMedications.add(new PrescribedMedication("Amoxicillin", random.nextInt(5) + 1));

        // generate UUIDs for doctor and patient

        // create an AppointmentOutcomeRecord
        AppointmentOutcomeRecord record = new AppointmentOutcomeRecord(
                doctor.getSystemUserId(),
                patient.getSystemUserId(),
                LocalDate.now(),
                AppointmentService.CONSULT,
                prescribedMedications,
                "high fever",
                MedicationStatus.PENDING
        );

        // Complete the appointment and retrieve records
        appointmentManager.completeAppointment(appt, record);
        List<AppointmentOutcomeRecord> records = appointmentManager.getAppointmentOutcomeRecordsByStatus(MedicationStatus.PENDING);


        //CARE PROVIDER
        CareProvider careProvider = CareProviderHolder.getCareProvider();
        var p = careProvider.getPatientIDsUnderDoctorCare(doctor);
        careProvider.addPatientToDoctorCare(patient, doctor);

    }

}
