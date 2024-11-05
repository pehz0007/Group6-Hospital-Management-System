package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.managers.appointment.models.AppointmentStatus;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.pagination.StringRenderer;

public class AppointmentStatusRenderer extends StringRenderer {

    private AppointmentStatus appointmentStatus;

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
