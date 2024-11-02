package com.group6.hms.app.screens.pharmacist;

import com.group6.hms.framework.screens.pagination.PaginationTableScreen;
import com.group6.hms.app.models.MedicationStock;
import com.group6.hms.framework.screens.ConsoleColor;

import java.util.List;

/**
 * The {@code ViewAndManageMedicationScreen} provides a screen for viewing and managing medication stocks.
 *
 * <p>This class extends {@link PaginationTableScreen} to manage and display the medication stock.</p>
 */
public class ViewAndManageMedicationScreen extends PaginationTableScreen<MedicationStock> {

    /**
     * Constructs a ViewAndManageMedicationScreen with a specified header
     * and a list of medication stocks.
     *
     * @param header the header to be displayed on the screen
     * @param medications the list of medications to be managed
     */
    public ViewAndManageMedicationScreen(String header, List<MedicationStock> medications) {
        super(header, medications);
    }

    @Override
    protected void printTable(List<MedicationStock> sublist) {
        super.printTable(sublist);
    }

    @Override
    public void onDisplay() {
        super.onDisplay();
    }
}
