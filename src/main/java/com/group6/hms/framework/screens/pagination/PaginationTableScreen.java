package com.group6.hms.framework.screens.pagination;

import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.option.OptionScreen;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * {@code PaginationTableScreen} provides a reusable component to display and manage tables with pagination functionality in a console-based UI.
 *
 * @param <T> the type of items to be displayed in the table.
 */
public class PaginationTableScreen<T> extends OptionScreen {

    private static final int PREVIOUS_PAGE_ID = 1;
    private static final int NEXT_PAGE_ID = 2;
    private static final int FILTER_PAGE_ID = 3;
    private static final int DEFAULT_PAGE_SIZE = 5;

    private int pageSize = DEFAULT_PAGE_SIZE;
    private int currentPage = 1;
    private int maxPage = 0;

    //Invariant: Must be set after the constructor call
    protected List<T> list;
    protected List<T> filteredList;

    private Function<List<T>, List<T>> filterFunction;


    /**
     * Constructor to initialize the PaginationTableScreen.
     */
    public PaginationTableScreen(String header, List<T> items) {
        this(header, items, DEFAULT_PAGE_SIZE);
    }

    /**
     * Constructor to initialize the PaginationTableScreen.
     */
    protected PaginationTableScreen(String header, List<T> items, int pageSize) {
        super(header);
        this.pageSize = pageSize;
        if(items != null){
            setList(items);
        }
    }

    /**
     * Sets the allowBack flag to true, enabling the ability to navigate back to the previous screen,
     * and then calls the superclass's onStart method.
     */
    @Override
    public void onStart() {
        setAllowBack(true);
        super.onStart();
    }

    /**
     * Displays the content of the current page of the filtered list.
     * If the filtered list is empty, it prints an empty message.
     * Otherwise, it prints the table of items for the current page
     * and the pagination counter.
     */
    @Override
    public void onDisplay() {
        if(filteredList.isEmpty()){
            printEmpty();
        }else{
            printTable(getPage(filteredList, currentPage, pageSize));
            printPaginationCounter();
        }
        println("");
        super.onDisplay();
    }

    /**
     * Prints a message to the console indicating that there is nothing to be displayed.
     */
    private void printEmpty() {
        println("");
        setCurrentTextConsoleColor(ConsoleColor.PURPLE);
        println("Nothing to be displayed");
    }

    /**
     * Update the list being displayed by the pagination table
     * Also resets the pagination to the initial state.
     *
     * @param list The list of items to be set and displayed.
     */
    protected void setList(List<T> list) {
        this.list = list;
        this.filteredList = list;
        resetPageCount();
    }

    protected void printTable(List<T> sublist) {
        PrintTableUtils.printItemsAsTable(consoleInterface, sublist);
    }

    @Override
    protected void handleOption(int optionId) {
        switch (optionId) {
            case PREVIOUS_PAGE_ID -> previousPage();
            case NEXT_PAGE_ID -> nextPage();
            case FILTER_PAGE_ID -> {
                if(filterFunction != null){
                    filteredList = filterFunction.apply(list);
                    resetPageCount();
                }
            }
        }
    }

    /**
     * Prints the current page number and the maximum page number in the pagination system to the console.
     */
    private void printPaginationCounter(){
        setCurrentTextConsoleColor(ConsoleColor.YELLOW);
        println("Page " + currentPage + " of " + maxPage);
    }

    /**
     * Navigates to the previous page in the pagination system.
     * This method decreases the current page number by one, ensuring that the page number does not go below 1.
     * After updating the current page, it updates the available navigation options.
     */
    private void previousPage(){
        currentPage = Math.max(currentPage - 1, 1);
        updateOptions();
    }

    /**
     * Navigates to the next page in the pagination system.
     * This method increases the current page number by one, ensuring that the page number does not go above {@link #maxPage}.
     * After updating the current page, it updates the available navigation options.
     */
    private void nextPage(){
        currentPage = Math.min(currentPage + 1, maxPage);
        updateOptions();
    }

    private boolean nextPageOption = false;
    private boolean prevPageOption = false;

    /**
     * Updates the pagination navigation options based on the current page.
     */
    private void updateOptions(){
        if(currentPage == 1 && prevPageOption) {
            removeOption(PREVIOUS_PAGE_ID);
            prevPageOption = false;
        }
        if(currentPage == maxPage && nextPageOption) {
            removeOption(NEXT_PAGE_ID);
            nextPageOption = false;
        }
        if(currentPage > 1 && !prevPageOption) {
            addOption(PREVIOUS_PAGE_ID, "Previous Page");
            prevPageOption = true;
        }
        if(currentPage < maxPage && !nextPageOption) {
            addOption(NEXT_PAGE_ID, "Next Page");
            nextPageOption = true;
        }
    }

    /**
     * Calculate the page count for the max page based on the size of the filtered list and reset the page back to 1
     */
    private void resetPageCount() {
        this.currentPage = 1;
        this.maxPage = (int) Math.ceil((double)(filteredList.size()) / pageSize); // Ceil the page size
        updateOptions();
    }

    /**
     * Get the current page size
     * @return the page size of the table
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Get the current page number
     * @return the current page number of the table
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Get the maximum page number
     * @return the maximum page number of the table
     */
    public int getMaxPage() {
        return maxPage;
    }

    /**
     * returns a view (not a new list) of the sourceList for the
     * range based on page and pageSize
     * @param sourceList
     * @param page, page number should start from 1
     * @param pageSize
     * @return
     * custom error can be given instead of returning emptyList
     */
    public <T> List<T> getPage(List<T> sourceList, int page, int pageSize) {
        if(pageSize <= 0 || page <= 0) {
            throw new IllegalArgumentException("invalid page size: " + pageSize);
        }

        int fromIndex = (page - 1) * pageSize;
        if(sourceList == null || sourceList.size() <= fromIndex){
            return Collections.emptyList();
        }

        // toIndex exclusive
        return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
    }

    /**
     * Sets the filter function for the PaginationTableScreen.
     * The filter function determines how the items in the list are filtered.
     * Once the filter function is set, it automatically adds or removes the filter option
     * based on the presence of the filter function.
     *
     * @param filterFunction The function to apply to the list for filtering.
     * The function takes a list of items as input and returns a filtered list.
     */
    public void setFilterFunction(Function<List<T>, List<T>> filterFunction) {
        this.filterFunction = filterFunction;
        if(allowFiltering())addOption(FILTER_PAGE_ID,"Filter");
        else removeOption(FILTER_PAGE_ID);
    }

    public boolean allowFiltering(){
        return filterFunction != null;
    }

}
