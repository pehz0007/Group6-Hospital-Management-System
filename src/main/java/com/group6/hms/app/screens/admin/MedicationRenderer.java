package com.group6.hms.app.screens.admin;

import com.group6.hms.app.models.Medication;
import com.group6.hms.framework.screens.pagination.StringRenderer;

/**
 * The {@code MedicationRenderer} is a renderer for displaying medication information.
 * It extends {@link StringRenderer} to customize the output of medication details,
 * particularly focusing on the medication name.
 */
public class MedicationRenderer extends StringRenderer {

    /**
     * Initializes the rendering object for displaying medication names.
     *
     * @param rowValue The row object being rendered, which represents the context for this row.
     * @param fieldValue The value of the field to be rendered, expected to be an instance of {@code Medication}.
     * @param fieldWidth The width allocated for the field in the output display.
     */
    @Override
    public void initRenderObject(Object rowValue, Object fieldValue, int fieldWidth) {
        if(fieldValue instanceof Medication medication){
            super.initRenderObject(rowValue, medication.getName(), fieldWidth);
        }
        else super.initRenderObject(rowValue, "", fieldWidth);
    }
}
