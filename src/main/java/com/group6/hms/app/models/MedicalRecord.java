package com.group6.hms.app.models;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecord {
    private List<String> pastDiagnoses;

    public MedicalRecord() {
        this.pastDiagnoses = new ArrayList<>();
    }

    public void addDiagnose(String diagnoses) {
        pastDiagnoses.add(diagnoses);
    }

    public void display(){
        if(pastDiagnoses.isEmpty()){
            System.out.println("No diagnoses recorded");
        }else{
            System.out.println("Your past diagnoses recorded: ");
            for (String diagnoses : pastDiagnoses) {
                System.out.println(diagnoses);
            }
        }
    }
}
