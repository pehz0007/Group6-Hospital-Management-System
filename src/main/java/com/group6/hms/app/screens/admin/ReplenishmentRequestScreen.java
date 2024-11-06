package com.group6.hms.app.screens.admin;

import com.group6.hms.app.managers.inventory.InventoryManager;
import com.group6.hms.app.managers.inventory.InventoryManagerHolder;
import com.group6.hms.app.managers.inventory.models.ReplenishmentRequest;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;
import com.group6.hms.framework.screens.pagination.SinglePaginationTableScreen;

/**
 * The {@code ReplenishmentRequestScreen} is a screen for managing replenishment requests
 * in the inventory management system. It allows administrators to view and approve
 * replenishment requests.
 *
 * <p>This class extends {@link SinglePaginationTableScreen} to provide a paginated view of requests</p>
 */
public class ReplenishmentRequestScreen extends SinglePaginationTableScreen<ReplenishmentRequest> {

    private final int APPROVE_REQUEST = 4;
    private InventoryManager inventoryManager;

    /**
     * Initializes the ReplenishmentRequestScreen with the title and loads existing replenishment requests.
     */
    public ReplenishmentRequestScreen() {
        super("Replenishment Request", null);
        this.inventoryManager = InventoryManagerHolder.getInventoryManager();
        setList(inventoryManager.getAllReplenishmentRequest());
        addOption(APPROVE_REQUEST, "Approve Request");
    }

    /**
     * Displays the details of a single replenishment request in a vertical format.
     *
     * @param item The replenishment request to display.
     */
    @Override
    public void displaySingleItem(ReplenishmentRequest item) {
        PrintTableUtils.printItemAsVerticalTable(consoleInterface, item);
    }

    /**
     * Handles user options for the replenishment request screen.
     * Specifically processes the approval of a replenishment request based on user input.
     *
     * @param optionId The ID of the selected option from the menu.
     */
    @Override
    protected void handleOption(int optionId) {
        if(optionId == APPROVE_REQUEST){
            print("Enter Request ID: ");
            String requestId = readString();
            ReplenishmentRequest request = inventoryManager.getReplenishmentRequestById(requestId);

            if(request != null){

                print("Do you want to approve? (Y/N): ");
                String choice = readString().trim().toUpperCase();

                if ("Y".equals(choice)) {
                    inventoryManager.approveReplenishmentRequest(request);
                    setCurrentTextConsoleColor(ConsoleColor.GREEN);
                    println("Request Approved!");
                }

                setList(inventoryManager.getAllReplenishmentRequest());
                waitForKeyPress();

            }else{
                setCurrentTextConsoleColor(ConsoleColor.RED);
                println("Unable to find request!");
                waitForKeyPress();
            }

        }
    }
}
