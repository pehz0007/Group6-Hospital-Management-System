package com.group6.hms.app.screens.admin;

import com.group6.hms.app.models.MedicationStock;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.pagination.StringRenderer;

public class LowStockIndicatorRenderer extends StringRenderer {

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
