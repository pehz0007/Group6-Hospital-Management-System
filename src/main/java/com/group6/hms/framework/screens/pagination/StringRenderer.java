package com.group6.hms.framework.screens.pagination;

import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.ConsoleInterface;

import static com.group6.hms.framework.screens.pagination.TextUtils.wrapTextWithoutBreakingAtSpaces;

/**
 * The {@code StringRenderer} class implements the {@code FieldRenderer} interface to provide functionality
 * for rendering string values in a console interface. It supports wrapping text without breaking words
 * at spaces to fit within a specified field width.
 */
public class StringRenderer implements FieldRenderer {

    private ConsoleColor valueFieldColor = ConsoleColor.GREEN;

    private String valueToPrint;
    private String[] valuesToPrint;

    /**
     * Initializes the object required for rendering text within a specified field width.
     *
     * @param rowValue Represents the data for the entire row.
     * @param fieldValue Represents the specific field data for the cell being rendered.
     * @param fieldWidth Specifies the width for the field display.
     */
    @Override
    public void initRenderObject(Object rowValue, Object fieldValue, int fieldWidth) {
        valueToPrint = fieldValue != null ? fieldValue.toString() : "null";
        valuesToPrint = wrapTextWithoutBreakingAtSpaces(valueToPrint, fieldWidth);
    }

    /**
     * Renders the specified line of text on the console within the given field width.
     *
     * @param consoleInterface The instance of the ConsoleInterface used for printing to the console.
     * @param line The specific line of text to render.
     * @param fieldWidth Specifies the width for the field display.
     */
    @Override
    public void render(ConsoleInterface consoleInterface, int line, int fieldWidth) {
        String printValue = valuesToPrint[line];
        consoleInterface.setCurrentTextConsoleColor(valueFieldColor);
        consoleInterface.print(String.format("%-" + fieldWidth + "s", printValue));
    }


    /**
     * Returns the number of lines the text to be printed will occupy.
     *
     * @return The number of lines this field will take up when rendered.
     */
    @Override
    public int getLines() {
        return valuesToPrint.length;
    }

    /**
     * Sets the color to be used for rendering the value field in the console.
     *
     * @param valueFieldColor The {@link ConsoleColor} to set the value field to.
     */
    protected void setValueFieldColor(ConsoleColor valueFieldColor) {
        this.valueFieldColor = valueFieldColor;
    }
}