package com.group6.hms.app.screens;

import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.OptionScreen;

import java.util.Collections;
import java.util.List;

public class PaginationTableScreen<T> extends OptionScreen {

    private static final int PREVIOUS_PAGE = 1;
    private static final int NEXT_PAGE = 2;
    private static final int DEFAULT_PAGE_SIZE = 2;

    private int pageSize = 2;
    private int currentPage = 1;
    private int maxPage = 0;

    private List<T> items;

    /**
     * Constructor to initialize the PaginationTableScreen.
     */
    protected PaginationTableScreen(String header, List<T> items) {
        this(header, items, DEFAULT_PAGE_SIZE);
    }

    /**
     * Constructor to initialize the PaginationTableScreen.
     */
    protected PaginationTableScreen(String header, List<T> items, int pageSize) {
        super(header);
        this.items = items;
        this.pageSize = pageSize;
        this.maxPage = (int) (double) (items.size() / pageSize); // Ceil the page size

        updateOptions();
    }

    @Override
    public void onStart() {
        PrintTableUtils.printItemsAsTable(this, getPage(items, currentPage, pageSize));
        printPaginationCounter();
        setAllowBack(true);

        super.onStart();
    }

    @Override
    protected void handleOption(int optionId) {
        switch (optionId) {
            case PREVIOUS_PAGE -> previousPage();
            case NEXT_PAGE -> nextPage();
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
            removeOption(PREVIOUS_PAGE);
            prevPageOption = false;
        }
        if(currentPage == maxPage && nextPageOption) {
            removeOption(NEXT_PAGE);
            nextPageOption = false;
        }
        if(currentPage > 1 && !prevPageOption) {
            addOption(PREVIOUS_PAGE, "Previous Page");
            prevPageOption = true;
        }
        if(currentPage < maxPage && !nextPageOption) {
            addOption(NEXT_PAGE, "Next Page");
            nextPageOption = true;
        }
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

}
