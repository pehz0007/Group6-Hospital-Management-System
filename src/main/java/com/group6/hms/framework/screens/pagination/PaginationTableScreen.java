package com.group6.hms.framework.screens.pagination;

import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.option.OptionScreen;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

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

    @Override
    public void onStart() {
        setAllowBack(true);
        super.onStart();
    }

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

    private void printEmpty() {
        println("");
        setCurrentTextConsoleColor(ConsoleColor.PURPLE);
        println("Nothing to be displayed");
    }

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

    private void printPaginationCounter(){
        setCurrentTextConsoleColor(ConsoleColor.YELLOW);
        println("Page " + currentPage + " of " + maxPage);
    }

    private void previousPage(){
        currentPage = Math.max(currentPage - 1, 1);
        updateOptions();
    }

    private void nextPage(){
        currentPage = Math.min(currentPage + 1, maxPage);
        updateOptions();
    }

    private boolean nextPageOption = false;
    private boolean prevPageOption = false;

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

    private void resetPageCount() {
        this.currentPage = 1;
        this.maxPage = (int) Math.ceil((double)(filteredList.size()) / pageSize); // Ceil the page size
        updateOptions();
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

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

    public void setFilterFunction(Function<List<T>, List<T>> filterFunction) {
        this.filterFunction = filterFunction;
        if(allowFiltering())addOption(FILTER_PAGE_ID,"Filter");
        else removeOption(FILTER_PAGE_ID);
    }

    public boolean allowFiltering(){
        return filterFunction != null;
    }

}
