package com.group6.hms.app.screens.admin.importer;

import com.group6.hms.app.models.BloodType;
import com.group6.hms.app.models.MedicalRecord;
import com.group6.hms.app.roles.*;

import java.io.Reader;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * The {@code PatientsCSVReader} class is responsible for reading patient
 * data from a CSV file and converting it into a list of {@link Patient}
 * objects.
 */
public class PatientsCSVReader extends CSVReader {

    private static final char[] defaultPassword = "password".toCharArray();

    /**
     * Constructs a {@code PatientsCSVReader} with the specified input
     * reader and buffer size.
     *
     * @param in the reader from which to read the CSV data
     * @param sz the buffer size for the reader
     */
    public PatientsCSVReader(Reader in, int sz) {
        super(in, sz);
    }

    /**
     * Constructs a {@code PatientsCSVReader} with the specified input
     * reader using the default buffer size.
     *
     * @param in the reader from which to read the CSV data
     */
    public PatientsCSVReader(Reader in) {
        super(in);
    }

    /**
     * Reads all patient entries from the CSV file and returns them as a
     * list of {@link Patient} objects.
     *
     * @return a list of {@code Patient} objects representing the
     *         patients read from the CSV file
     */
    public List<Patient> readAllPatients() {
        String[] headers = this.readCSVLine();

        String[] values;
        List<Patient> patients = new LinkedList<>();
        while (this.hasNext() && (values = this.readCSVLine()).length != 0) {
            String patientId = values[0];
            String patientName = values[1];
            LocalDate dateOfBirth = LocalDate.parse(values[2]);
            Gender patientGender = Gender.fromString(values[3]);
            BloodType bloodType = BloodType.fromString(values[4]);
            String contactInformation = values[5];

            Patient patient = new Patient(patientId, defaultPassword, patientName, patientGender,contactInformation);
            patient.setContactInformation(contactInformation);

            MedicalRecord medicalRecord = patient.getMedicalRecord();
            medicalRecord.setDateOfBirth(dateOfBirth);
            medicalRecord.setBloodType(bloodType);
            patient.updateMedicalRecord(medicalRecord);

            patients.add(patient);
        }
        return patients;
    }
}
