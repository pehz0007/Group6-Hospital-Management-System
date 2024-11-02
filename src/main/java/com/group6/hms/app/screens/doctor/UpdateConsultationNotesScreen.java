package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.models.MedicationStatus;
import com.group6.hms.app.managers.AppointmentManager;
import com.group6.hms.app.managers.InventoryManager;
import com.group6.hms.app.models.*;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;
import com.group6.hms.framework.screens.pagination.SinglePaginationTableScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UpdateConsultationNotesScreen extends SinglePaginationTableScreen<UUID> {
    AppointmentManager appointmentManager = new AppointmentManager();
    InventoryManager inventoryManager = new InventoryManager();
    List<UUID> consultationNo;

    @Override
    protected void handleOption(int optionId) {
        super.handleOption(optionId);
        if(optionId == 1){
            updateNotes();
        }
    }

    public UpdateConsultationNotesScreen(List<UUID> items) {
        super("Update Consultation Notes", items);
        addOption(1, "Update Consultation");
        this.consultationNo = items;
    }



    @Override
    public void displaySingleItem(UUID item) {
        if(item==null){
            println("No appointments to update.");
        }
        else{
            Appointment user = (Appointment) appointmentManager.getAppointmentsByUUID(item).getFirst();
            AppointmentViewLM appointment = new AppointmentViewLM(user);

            PrintTableUtils.printItemAsVerticalTable(consoleInterface, appointment);
        }

    }

    public void updateNotes(){
        for(UUID idNo: consultationNo){
            Appointment appointment = appointmentManager.getAppointmentsByUUID(idNo).get(0);
            println("+".repeat(30));
            println("Patient Name: "+ appointment.getPatient().getName());
            println("Patient Blood Type: " + appointment.getPatient().getMedicalRecord().getBloodType());
            println("Patient Date of Birth: " + appointment.getPatient().getMedicalRecord().getDateOfBirth());
            println("+".repeat(30));

            print("Is this the appointment you want to update for today?(Y/N): ");
            String result = readString();
            if(result.equalsIgnoreCase("y")){
                String details = null;
                String service = null;
                AppointmentService service1;
                try{
                    print("Appointment Details Update:");
                    details = readString();
                } catch (Exception e) {
                    println("\u001B[31m Invalid format. Please enter again");
                    return;
                }
                try{
                    print("Appointment Service (Consult or Xray or Blood Test): ");
                    service = readString();
                } catch (Exception e) {
                    println("\u001B[31m Invalid format. Please enter again");
                    return;
                }

                if(service.equalsIgnoreCase("consult")){
                    service1 = AppointmentService.CONSULT;
                }else if(service.equalsIgnoreCase("xray")){
                    service1 = AppointmentService.XRAY;
                }else {
                    service1 = AppointmentService.BLOOD_TEST;
                }
                List<Medication> medications = inventoryManager.getAllMedication();
                println("");
                println("-".repeat(30));
                int num = 1;
                for(Medication medicine: medications){
                    println(num+ ". "+ medicine.getName());
                    num++;
                }
                println("-".repeat(30));
                boolean noOfMedicine = true;
                ArrayList<PrescribedMedication> medication = new ArrayList<>();
                while(noOfMedicine){
                    print("Input the medicine required one by one (enter 0 to stop): ");
                    String name = readString();
                    if(name.equalsIgnoreCase("0")){
                        noOfMedicine = false;
                    }else {
                        print("Input the quantity needed to prescribe: ");
                        int quantity = readInt();
                        PrescribedMedication medi1 = new PrescribedMedication(name, quantity);
                        medication.add(medi1);
                    }
                }

                AppointmentOutcomeRecord appointmentOutcomeRecord = new AppointmentOutcomeRecord(appointment.getDoctor().getSystemUserId(), appointment.getPatient().getSystemUserId(), appointment.getDate(), service1, medication, details, MedicationStatus.PENDING);
                appointmentManager.completeAppointment(appointment, appointmentOutcomeRecord);
                break;
            }
        }
    }
}
