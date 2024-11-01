package com.group6.hms.framework.screens.pagination;

import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.ConsoleInterface;

import static com.group6.hms.framework.screens.pagination.TextUtils.wrapTextWithoutBreakingAtSpaces;

public class StringRenderer implements FieldRenderer {

    private ConsoleColor valueFieldColor = ConsoleColor.GREEN;

    private String valueToPrint;
    private String[] valuesToPrint;

    @Override
    public void initRenderObject(Object rowValue, Object fieldValue, int fieldWidth) {
        valueToPrint = fieldValue != null ? fieldValue.toString() : "null";
        valuesToPrint = wrapTextWithoutBreakingAtSpaces(valueToPrint, fieldWidth);
    }

    @Override
    public void render(ConsoleInterface consoleInterface, int line, int fieldWidth) {
        String printValue = valuesToPrint[line];
        consoleInterface.setCurrentTextConsoleColor(valueFieldColor);
        consoleInterface.print(String.format("%-" + fieldWidth + "s", printValue));
    }

    @Override
    public int getLines() {
        return valuesToPrint.length;
    }

    protected void setValueFieldColor(ConsoleColor valueFieldColor) {
        this.valueFieldColor = valueFieldColor;
    }
}