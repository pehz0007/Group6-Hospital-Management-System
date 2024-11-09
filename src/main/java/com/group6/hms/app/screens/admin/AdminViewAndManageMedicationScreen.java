package com.group6.hms.app.screens.admin;

import com.group6.hms.app.managers.inventory.InventoryManager;
import com.group6.hms.app.managers.inventory.InventoryManagerHolder;
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

/**
 * The {@code AdminViewAndManageMedicationScreen} is a screen for viewing and managing medication stocks in the inventory.
 * It provides functionalities for approving replenishment requests and importing medication stock data.
 *
 * * <p>This class extends {@link PaginationTableScreen} to manage and display the medication stock.</p>
 */
public class AdminViewAndManageMedicationScreen extends PaginationTableScreen<MedicationStock> {

    private InventoryManager inventoryManager;

    //ADMIN OPTIONS
    private final int APPROVE_REPLENISHMENT_REQUEST = 4;
    private final int IMPORT_MEDICATIONS_STOCK = 5;

    /**
     * Constructor to initialize the {@code AdminViewAndManageMedicationScreen}.
     * It sets up the inventory manager and updates the medication stock list.
     */
    public AdminViewAndManageMedicationScreen() {
        super("Medications", null);
        this.inventoryManager = InventoryManagerHolder.getInventoryManager();
        updateMedicationStocks();

        addOption(APPROVE_REPLENISHMENT_REQUEST, "Approve Replenishment Request");
        addOption(IMPORT_MEDICATIONS_STOCK, "Import Medications");
    }

    /**
     * Updates the list of medication stocks displayed on the screen.
     * This method retrieves all medication stocks from the {@code inventoryManager}
     * and sets them to be displayed.
     */
    private void updateMedicationStocks() {
        List<MedicationStock> medications = inventoryManager.getAllMedicationStock();

        setList(medications);
    }

    /**
     * Handles the user's option selection from the medication management menu.
     *
     * @param optionId The ID of the selected option.
     */
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
                    inventoryManager.addMedicationStocks(medications);
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
