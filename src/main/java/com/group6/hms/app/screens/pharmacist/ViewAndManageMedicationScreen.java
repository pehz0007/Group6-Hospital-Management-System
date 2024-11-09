package com.group6.hms.app.screens.pharmacist;

import com.group6.hms.framework.screens.pagination.PaginationTableScreen;
import com.group6.hms.app.managers.inventory.models.MedicationStock;

import java.util.List;

/**
 * The {@code PharmacistViewAndManageMedicationScreen} allows pharmacists to view and manage medication stocks.
 * This screen displays current stock levels of medications and submitting
 * replenishment requests for medication that are low in stock.
 *
 * <p>This class extends {@link PaginationTableScreen} to manage and display the medication stock.</p>
 */
public class ViewAndManageMedicationScreen extends PaginationTableScreen<MedicationStock> {

    /**
     * Constructs ViewAndManageMedicationScreen.
     *
     * @param header The header title to display on the screen.
     * @param medications The list of medication stocks to display and manage.
     */
    public ViewAndManageMedicationScreen(String header, List<MedicationStock> medications) {
        super(header, medications);
    }

    /**
     * Prints a table of the specified sublist of medication stocks.
     *
     * @param sublist The subset of medication stocks to display in the table.
     */
    @Override
    protected void printTable(List<MedicationStock> sublist) {
        super.printTable(sublist);
    }

    /**
     * Handles the display logic when this screen is shown to the user.
     */
    @Override
    public void onDisplay() {
        super.onDisplay();
    }
}
