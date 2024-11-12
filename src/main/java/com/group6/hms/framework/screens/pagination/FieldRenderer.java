package com.group6.hms.framework.screens.pagination;

import com.group6.hms.framework.screens.ConsoleInterface;

/**
 * The {@code FieldRenderer} class allow the {@code PaginationTableScreen} to display custom field with different type
 */
public interface FieldRenderer<T> {

    /**
     * Initialise the Field Renderer object and setting up values based on a row's data.
     * @param rowValue Represents the data for the entire row.
     * @param fieldValue Represents the specific field data for the cell being rendered.
     * @param fieldWidth Specifies the width for the field display.
     */
    void initRenderObject(Object rowValue, Object fieldValue, int fieldWidth);

    /**
     * Handles the actual rendering of the field.
     * @param consoleInterface The instance of the {@code ConsoleInterface} used for printing to the console
     * @param line Which line is currently being rendered
     * @param fieldWidth Specifies the width for the field display.
     */
    void render(ConsoleInterface consoleInterface, int line, int fieldWidth);

    /**
     * Returns the number of lines this field will take up when rendered
     * @return The number of lines this field
     */
    int getLines();
}