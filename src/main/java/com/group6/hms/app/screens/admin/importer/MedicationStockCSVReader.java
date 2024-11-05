package com.group6.hms.app.screens.admin.importer;

import com.group6.hms.app.managers.inventory.models.Medication;
import com.group6.hms.app.managers.inventory.models.MedicationStock;

import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

public class MedicationStockCSVReader extends CSVReader{
    public MedicationStockCSVReader(Reader in, int sz) {
        super(in, sz);
    }

    public MedicationStockCSVReader(Reader in) {
        super(in);
    }

    public List<MedicationStock> readAllMedications() {
        String[] headers = this.readCSVLine();

        String[] values;
        List<MedicationStock> medicationStock = new LinkedList<>();
        while (this.hasNext() && (values = this.readCSVLine()).length != 0) {
            String medicationName = values[0];
            int currentStock = Integer.parseInt(values[1]);
            int lowStockLimit = Integer.parseInt(values[2]);
            Medication medication = new Medication(medicationName);
            MedicationStock medStock = new MedicationStock(medication, currentStock, lowStockLimit);
            medicationStock.add(medStock);
        }
        return medicationStock;
    }
}
