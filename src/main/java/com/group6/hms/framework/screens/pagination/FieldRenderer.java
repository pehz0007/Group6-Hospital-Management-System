package com.group6.hms.framework.screens.pagination;

import com.group6.hms.framework.screens.ConsoleInterface;

public interface FieldRenderer {

    void initRenderObject(Object value, int fieldWidth);
    void render(ConsoleInterface consoleInterface, int line, int fieldWidth);
    int getLines();
}