package com.group6.hms.app.screens.pharmacist;

import com.group6.hms.framework.screens.pagination.PaginationTableScreen;
import com.group6.hms.app.managers.inventory.models.MedicationStock;

import java.util.List;

public class ViewAndManageMedicationScreen extends PaginationTableScreen<MedicationStock> {

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
