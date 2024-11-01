package com.group6.hms.app.screens.admin;

import com.group6.hms.app.managers.InventoryManager;
import com.group6.hms.app.models.ReplenishmentRequest;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.pagination.PrintTableUtils;
import com.group6.hms.framework.screens.pagination.SinglePaginationTableScreen;

public class ReplenishmentRequestScreen extends SinglePaginationTableScreen<ReplenishmentRequest> {

    private final int APPROVE_REQUEST = 4;
    private InventoryManager inventoryManager;

    public ReplenishmentRequestScreen() {
        super("Replenishment Request", null);
        this.inventoryManager = new InventoryManager();
        setList(inventoryManager.getAllReplenishmentRequest());
        addOption(APPROVE_REQUEST, "Approve Request");
    }

    @Override
    public void displaySingleItem(ReplenishmentRequest item) {
        PrintTableUtils.printItemAsVerticalTable(consoleInterface, item);
    }

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
