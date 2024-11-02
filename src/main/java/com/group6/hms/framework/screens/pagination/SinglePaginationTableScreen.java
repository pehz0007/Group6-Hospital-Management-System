package com.group6.hms.framework.screens.pagination;

import java.util.List;

/**
 * SinglePaginationTableScreen is an abstract class that extends PaginationTableScreen to provide
 * the functionality for displaying items in a single vertical paginated table, where each item is displayed individually.
 *
 * @param <T> the type of the items to be displayed in the table.
 */
public abstract class SinglePaginationTableScreen<T> extends PaginationTableScreen<T> {
    /**
     * Constructs an instance of SinglePaginationTableScreen.
     *
     * @param header the title or header of the screen.
     * @param items the list of items to be displayed in the paginated table.
     */
    public SinglePaginationTableScreen(String header, List<T> items) {
        super(header, items);
    }

    /**
     * Constructs an instance of SinglePaginationTableScreen with the specified header, list of items, and page size.
     *
     * @param header the title or header of the screen.
     * @param items the list of items to be displayed.
     * @param pageSize the number of items to be displayed per page.
     */
    public SinglePaginationTableScreen(String header, List<T> items, int pageSize) {
        super(header, items, pageSize);
    }

    /**
     * Prints a given sublist of items by individually displaying each item.
     *
     * @param sublist the sublist of items to be printed. Each item in the sublist will be displayed individually.
     */
    @Override
    protected void printTable(List<T> sublist) {
        for (T item : sublist) {
            displaySingleItem(item);
        }
    }

    /**
     * Displays a single item of type T.
     *
     * @param item the item to be displayed.
     */
    public abstract void displaySingleItem(T item);

}
