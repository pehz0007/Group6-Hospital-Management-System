package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.models.AppointmentStatus;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.pagination.StringRenderer;

public class AppointmentStatusRenderer extends StringRenderer {

    private AppointmentStatus appointmentStatus;

    @Override
    public void initRenderObject(Object value, int fieldWidth) {
        super.initRenderObject(value, fieldWidth);
        appointmentStatus = (AppointmentStatus) value;
        switch (appointmentStatus){
            case CONFIRMED -> setValueFieldColor(ConsoleColor.GREEN);
            case REQUESTED -> setValueFieldColor(ConsoleColor.YELLOW);
            case CANCELLED -> setValueFieldColor(ConsoleColor.RED);
        }
    }
}
