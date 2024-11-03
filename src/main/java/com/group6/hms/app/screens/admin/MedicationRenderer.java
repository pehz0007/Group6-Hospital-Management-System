package com.group6.hms.app.screens.admin;

import com.group6.hms.app.managers.inventory.models.Medication;
import com.group6.hms.framework.screens.pagination.StringRenderer;

public class MedicationRenderer extends StringRenderer {

    @Override
    public void initRenderObject(Object rowValue, Object fieldValue, int fieldWidth) {
        if(fieldValue instanceof Medication medication){
            super.initRenderObject(rowValue, medication.getName(), fieldWidth);
        }
        else super.initRenderObject(rowValue, "", fieldWidth);
    }
}
