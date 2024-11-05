    package com.group6.hms.app.roles;

    import com.group6.hms.app.auth.User;
    import com.group6.hms.app.models.MedicalRecord;

    public class Patient extends User {
        private String contactInformation; // Email
        private String phoneNumber;
        private MedicalRecord medicalRecord;

        public Patient(String userId, char[] password, String name, Gender gender, String contactInformation, String phoneNumber) {
            super(userId, password, name, gender);
            this.contactInformation = contactInformation;
            this.phoneNumber = phoneNumber;
            this.medicalRecord = new MedicalRecord();
        }

        public void updateMedicalRecord(MedicalRecord medicalRecord) {
            this.medicalRecord = medicalRecord;
        }
        public MedicalRecord getMedicalRecord() {
            return medicalRecord;
        }

        public String getContactInformation() {
            return contactInformation;
        }
        public void setContactInformation(String contactInformation) {
            this.contactInformation = contactInformation;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        @Override
        public String getRoleName() {
            return "Patient";
        }
    }