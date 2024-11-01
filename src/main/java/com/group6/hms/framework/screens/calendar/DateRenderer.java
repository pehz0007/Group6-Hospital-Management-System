package com.group6.hms.framework.screens.calendar;

import com.group6.hms.framework.screens.pagination.StringRenderer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateRenderer extends StringRenderer {

    @Override
    public void initRenderObject(Object rowValue, Object fieldValue, int fieldWidth) {
        if(fieldValue instanceof LocalDate date){

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM uuuu");
            String formattedString = date.format(formatter);

            super.initRenderObject(rowValue, formattedString, fieldWidth);
        }else super.initRenderObject(rowValue, fieldValue, fieldWidth);

    }
}
