package com.group6.hms.framework.screens.calendar;

import com.group6.hms.framework.screens.pagination.StringRenderer;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeRenderer extends StringRenderer {

    @Override
    public void initRenderObject(Object rowValue, Object fieldValue, int fieldWidth) {
        if(fieldValue instanceof LocalTime time){

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
            String formattedString = time.format(formatter);

            super.initRenderObject(rowValue, formattedString, fieldWidth);
        }else super.initRenderObject(rowValue, fieldValue, fieldWidth);

    }
}
