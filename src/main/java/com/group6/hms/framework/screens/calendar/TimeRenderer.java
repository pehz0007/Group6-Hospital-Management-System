package com.group6.hms.framework.screens.calendar;

import com.group6.hms.framework.screens.pagination.StringRenderer;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * The {@code TimeRenderer} class allows the {@code PaginationTableScreen} to show the {@code LocalTime} in a pretty formatted string of
 * <pre>{@code 'hh:mm a'}</pre>
 */
public class TimeRenderer extends StringRenderer {

    /**
     * Call the {@code StringRenderer} {@link #initRenderObject(Object, Object, int)} once the {@code LocalTime} has been converted into a {@code String}
     * @param rowValue Represents the data for the entire row.
     * @param fieldValue Represents the specific field data for the cell being rendered.
     * @param fieldWidth Specifies the width for the field display.
     */
    @Override
    public void initRenderObject(Object rowValue, Object fieldValue, int fieldWidth) {
        if(fieldValue instanceof LocalTime time){

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
            String formattedString = time.format(formatter);

            super.initRenderObject(rowValue, formattedString, fieldWidth);
        }else super.initRenderObject(rowValue, fieldValue, fieldWidth);

    }
}
