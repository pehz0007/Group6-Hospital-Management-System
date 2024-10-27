package com.group6.hms.framework.screens.pagination;

import java.util.List;

public abstract class SinglePaginationTableScreen<T> extends PaginationTableScreen<T> {
    public SinglePaginationTableScreen(String header, List<T> items) {
        super(header, items);
    }

    public SinglePaginationTableScreen(String header, List<T> items, int pageSize) {
        super(header, items, pageSize);
    }

    @Override
    protected void printTable(List<T> sublist) {
        for (T item : sublist) {
            displaySingleItem(item);
        }
    }

    public abstract void displaySingleItem(T item);

}
