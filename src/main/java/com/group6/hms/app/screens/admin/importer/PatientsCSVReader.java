package com.group6.hms.app.screens.admin.importer;

import com.group6.hms.app.models.BloodType;
import com.group6.hms.app.models.MedicalRecord;
import com.group6.hms.app.roles.*;

import java.io.Reader;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class PatientsCSVReader extends CSVReader {

    private static final char[] defaultPassword = "password".toCharArray();

    public PatientsCSVReader(Reader in, int sz) {
        super(in, sz);
    }

    public PatientsCSVReader(Reader in) {
        super(in);
    }

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

            Patient patient = new Patient(patientId, defaultPassword, patientName, patientGender);

            MedicalRecord medicalRecord = new MedicalRecord();
            medicalRecord.setDateOfBirth(dateOfBirth);
            medicalRecord.setBloodType(bloodType);
            medicalRecord.setContextInformation(contactInformation);
            patient.updateMedicalRecord(medicalRecord);

            patients.add(patient);
        }
        return patients;
    }

}
