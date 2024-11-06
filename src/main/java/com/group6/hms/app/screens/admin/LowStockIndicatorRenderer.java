package com.group6.hms.app.screens.admin;

import com.group6.hms.app.managers.inventory.models.MedicationStock;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.pagination.StringRenderer;

/**
 * The {@code LowStockIndicatorRenderer} is a renderer for indicating the stock status of medications.
 *
 * It extends {@link StringRenderer} to customize the display of medication stock levels
 * based on their current stock in relation to the low stock level limit.
 */
public class LowStockIndicatorRenderer extends StringRenderer {

    /**
     * Initializes the rendering object for displaying medication stock levels.
     *
     * @param rowValue The row object being rendered, which is expected to be a {@code MedicationStock} instance.
     * @param fieldValue The value of the field to be rendered.
     * @param fieldWidth The width allocated for the field in the output display.
     */
    @Override
    public void initRenderObject(Object rowValue, Object fieldValue, int fieldWidth) {
        super.initRenderObject(rowValue, fieldValue, fieldWidth);
        if(rowValue instanceof MedicationStock medicationStock){
            if(medicationStock.getCurrentStock() <= medicationStock.getLowStockLevelLimit()){
                setValueFieldColor(ConsoleColor.RED);
            }else{
                setValueFieldColor(ConsoleColor.GREEN);
            }
        }
    }
}