    package com.group6.hms.app.roles;

    import com.group6.hms.app.auth.User;
    import com.group6.hms.app.models.AppointmentOutcomeRecord;
    import com.group6.hms.app.models.MedicalRecord;

    import java.util.List;
    import java.util.ArrayList;

    /**
     * The {@code Patient} class represents a patient in the hospital management system.
     * It extends the {@link User} class and contains attributes and methods specific to a patient.
     */
    public class Patient extends User {
        private String contactInformation; // Email
        private MedicalRecord medicalRecord;
        private List<AppointmentOutcomeRecord> appointmentOutcomeRecord;

        /**
         * Constructs a {@code Patient} with the specified user details.
         *
         * @param userId            the unique user ID of the patient
         * @param password          the password of the patient, represented as a character array
         * @param name              the name of the patient
         * @param gender            the gender of the patient, represented as a {@link Gender} enum
         * @param contactInformation the contact information of the patient, typically an email
         */
        public Patient(String userId, char[] password, String name, Gender gender, String contactInformation) {
            super(userId, password, name, gender);
            this.contactInformation = contactInformation;
            this.medicalRecord = new MedicalRecord();
            this.appointmentOutcomeRecord = new ArrayList<>();
        }

        /**
         * Retrieves a list of past treatments for the patient.
         *
         * @return a list of {@link AppointmentOutcomeRecord} representing past treatments
         */
        public List<AppointmentOutcomeRecord> getPastTreatments(){
            return appointmentOutcomeRecord;
        }

        /**
         * Retrieves a list of past diagnoses for the patient.
         *
         * @return a list of {@link AppointmentOutcomeRecord} representing past diagnoses
         */
        public List<AppointmentOutcomeRecord> getPastDiagnoses(){
            return appointmentOutcomeRecord;
        }

        /**
         * Updates the patient's medical record.
         *
         * @param medicalRecord the new {@link MedicalRecord} to be set for the patient
         */
        public void updateMedicalRecord(MedicalRecord medicalRecord) {
            this.medicalRecord = medicalRecord;
        }

        /**
         * Retrieves the patient's medical record.
         *
         * @return the {@link MedicalRecord} of the patient
         */
        public MedicalRecord getMedicalRecord() {
            return medicalRecord;
        }

        /**
         * Retrieves the contact information of the patient.
         *
         * @return the contact information as a {@code String}
         */
        public String getContactInformation() {
            return contactInformation;
        }

        /**
         * Updates the contact information of the patient.
         *
         * @param contactInformation the new contact information to be set for the patient
         */
        public void setContactInformation(String contactInformation) {
            this.contactInformation = contactInformation;
        }

        /**
         * Returns the role name of this patient.
         *
         * @return the role name as a {@code String}, specifically "Patient"
         */
        @Override
        public String getRoleName() {
            return "Patient";
        }
    }
