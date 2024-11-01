package com.group6.hms.framework.screens.calendar;

import com.group6.hms.framework.screens.pagination.StringRenderer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeRenderer extends StringRenderer {

    @Override
    public void initRenderObject(Object rowValue, Object fieldValue, int fieldWidth) {
        if(fieldValue instanceof LocalDateTime dateTime){

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM uuuu, hh:mm a");
            String formattedString = dateTime.format(formatter);

            super.initRenderObject(rowValue, formattedString, fieldWidth);
        }else super.initRenderObject(rowValue, fieldValue, fieldWidth);

    }
}
