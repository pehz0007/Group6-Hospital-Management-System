package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.models.AppointmentStatus;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.pagination.StringRenderer;

/**
 * The {@code AppointmentStatusRenderer} is a renderer for displaying appointment status information.
 *
 * It extends {@code StringRenderer} to customize the output of appointment statuses
 * with appropriate color coding based on their state.
 */
public class AppointmentStatusRenderer extends StringRenderer {

    private AppointmentStatus appointmentStatus;

    /**
     * Initializes the rendering object with the appointment status.
     *
     * @param rowValue The row object being rendered, representing the context for this row.
     * @param fieldValue The value of the field to be rendered, expected to be an instance of {@code AppointmentStatus}.
     * @param fieldWidth The width allocated for the field in the output display.
     */
    @Override
    public void initRenderObject(Object rowValue, Object fieldValue, int fieldWidth) {
        super.initRenderObject(rowValue, fieldValue, fieldWidth);
        appointmentStatus = (AppointmentStatus) fieldValue;
        switch (appointmentStatus){
            case CONFIRMED, COMPLETED -> setValueFieldColor(ConsoleColor.GREEN);
            case REQUESTED -> setValueFieldColor(ConsoleColor.YELLOW);
            case CANCELLED -> setValueFieldColor(ConsoleColor.RED);
        }
    }
}
