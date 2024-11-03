package com.group6.hms.framework.screens.calendar;

import com.group6.hms.framework.screens.pagination.StringRenderer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The {@code DateRenderer} class allows the {@code PaginationTableScreen} to show the {@code LocalDate} in a pretty formatted string of
 * <pre>{@code 'dd MMMM uuuu'}</pre>
 */
public class DateRenderer extends StringRenderer {

    /**
     * Call the {@code StringRenderer} {@link #initRenderObject(Object, Object, int)} once the {@code LocalDate} has been converted into a {@code String}
     * @param rowValue Represents the data for the entire row.
     * @param fieldValue Represents the specific field data for the cell being rendered.
     * @param fieldWidth Specifies the width for the field display.
     */
    @Override
    public void initRenderObject(Object rowValue, Object fieldValue, int fieldWidth) {
        if(fieldValue instanceof LocalDate date){

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM uuuu");
            String formattedString = date.format(formatter);

            super.initRenderObject(rowValue, formattedString, fieldWidth);
        }else super.initRenderObject(rowValue, fieldValue, fieldWidth);

    }
}
