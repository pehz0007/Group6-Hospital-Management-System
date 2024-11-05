package com.group6.hms.app.managers.inventory;

public class InventoryManagerHolder {

    private static volatile InventoryManager instance;

    // Private constructor to prevent instantiation
    private InventoryManagerHolder() {
    }

    public static InventoryManager getInventoryManager() {
        if (instance == null) {
            synchronized (InventoryManagerHolder.class) {
                if (instance == null) {
                    instance = new InventoryManager();
                }
            }
        }
        return instance;
    }

}
