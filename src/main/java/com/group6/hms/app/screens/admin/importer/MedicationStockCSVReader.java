package com.group6.hms.app.screens.admin.importer;

import com.group6.hms.app.models.Medication;
import com.group6.hms.app.models.MedicationStock;

import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

/**
 * The {@code MedicationStockCSVReader} class is responsible for reading
 * medication stock data from a CSV file and converting it into a list
 * of {@link MedicationStock} objects.
 */
public class MedicationStockCSVReader extends CSVReader {

    /**
     * Constructs a {@code MedicationStockCSVReader} with the specified input
     * reader and buffer size.
     *
     * @param in the reader from which to read the CSV data
     * @param sz the buffer size for the reader
     */
    public MedicationStockCSVReader(Reader in, int sz) {
        super(in, sz);
    }

    /**
     * Constructs a {@code MedicationStockCSVReader} with the specified input
     * reader using the default buffer size.
     *
     * @param in the reader from which to read the CSV data
     */
    public MedicationStockCSVReader(Reader in) {
        super(in);
    }

    /**
     * Reads all medication stock entries from the CSV file and returns them
     * as a list of {@link MedicationStock} objects.
     *
     * @return a list of {@code MedicationStock} objects representing the
     *         medication stock read from the CSV file
     */
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
