package com.group6.hms.app.screens.admin;

import com.group6.hms.app.managers.inventory.InventoryManager;
import com.group6.hms.app.managers.inventory.models.MedicationStock;
import com.group6.hms.app.screens.admin.importer.MedicationStockCSVReader;
import com.group6.hms.app.storage.SerializationStorageProvider;
import com.group6.hms.app.storage.StorageProvider;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.pagination.PaginationTableScreen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class AdminViewAndManageMedicationScreen extends PaginationTableScreen<MedicationStock> {

    private InventoryManager inventoryManager;

    //ADMIN OPTIONS
    private final int APPROVE_REPLENISHMENT_REQUEST = 4;
    private final int IMPORT_MEDICATIONS_STOCK = 5;

    public AdminViewAndManageMedicationScreen() {
        super("Medications", null);
        this.inventoryManager = new InventoryManager();
        updateMedicationStocks();

        addOption(APPROVE_REPLENISHMENT_REQUEST, "Approve Replenishment Request");
        addOption(IMPORT_MEDICATIONS_STOCK, "Import Medications");
    }

    private void updateMedicationStocks() {
        List<MedicationStock> medications = inventoryManager.getAllMedicationStock();
        setList(medications);
    }

    @Override
    protected void handleOption(int optionId) {
        switch (optionId){
            case APPROVE_REPLENISHMENT_REQUEST -> {
                navigateToScreen(new ReplenishmentRequestScreen());
            }
            case IMPORT_MEDICATIONS_STOCK -> {
                print("Medication File Location:");
                String filePath = readString();
                try {
                    MedicationStockCSVReader medicationStockCSVReader = new MedicationStockCSVReader(new FileReader(filePath));
                    List<MedicationStock> medications = medicationStockCSVReader.readAllMedications();
                    StorageProvider<MedicationStock> medicationStorageProvider = new SerializationStorageProvider<>();
                    File medicationsFile = new File("data/medications.ser");

                    for (MedicationStock medicationStock : medications) {
                        medicationStorageProvider.addNewItem(medicationStock);
                    }
                    medicationStorageProvider.saveToFile(medicationsFile);
                    setCurrentTextConsoleColor(ConsoleColor.GREEN);
                    println("Medication Stock imported successfully!");
                    waitForKeyPress();
                    updateMedicationStocks();
                } catch (FileNotFoundException e) {
                    setCurrentTextConsoleColor(ConsoleColor.RED);
                    println("Unable to find file!");
                    waitForKeyPress();
                }
            }
        }
    }
}
