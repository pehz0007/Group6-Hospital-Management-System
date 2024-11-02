package com.group6.hms.framework.screens.calendar;

import com.group6.hms.framework.screens.pagination.StringRenderer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The {@code DateTimeRenderer} class allows the {@code PaginationTableScreen} to show the {@code LocalDateTime} in a pretty formatted string of
 * <pre>'dd MMMM uuuu, hh:mm a'</pre>
 */
public class DateTimeRenderer extends StringRenderer {

    /**
     * Call the {@code StringRenderer} {@link #initRenderObject(Object, Object, int)} once the {@code LocalDateTime} has been converted into a {@code String}
     * @param rowValue Represents the data for the entire row.
     * @param fieldValue Represents the specific field data for the cell being rendered.
     * @param fieldWidth Specifies the width for the field display.
     */
    @Override
    public void initRenderObject(Object rowValue, Object fieldValue, int fieldWidth) {
        if(fieldValue instanceof LocalDateTime dateTime){

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM uuuu, hh:mm a");
            String formattedString = dateTime.format(formatter);

            super.initRenderObject(rowValue, formattedString, fieldWidth);
        }else super.initRenderObject(rowValue, fieldValue, fieldWidth);

    }
}
