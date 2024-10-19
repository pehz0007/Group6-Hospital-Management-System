package com.group6.hms.app.screens.admin;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.auth.LoginManagerHolder;
import com.group6.hms.app.auth.User;
import com.group6.hms.framework.screens.ConsoleColor;
import com.group6.hms.framework.screens.pagination.PaginationTableScreen;

import java.util.Collection;
import java.util.List;

public class ViewAndManageUsersScreen extends PaginationTableScreen<UserView> {

    private final int CREATE_USER = 4;
    private LoginManager loginManager;

    public ViewAndManageUsersScreen() {
        super("Users", null);
        addOption(CREATE_USER, "Create a new user");
    }

    @Override
    public void onStart() {
        super.onStart();
        loginManager = LoginManagerHolder.getLoginManager();
        updateUserViewsTable();
    }

    private void updateUserViewsTable() {
        Collection<User> users = loginManager.getAllUsers();
        List<UserView> userViews = users.stream().map(UserView::new).toList();
        setItems(userViews);
    }

    @Override
    protected void handleOption(int optionId) {
        super.handleOption(optionId);
        if (optionId == CREATE_USER) {
            UserUtils.askForUsernameAndPassword(consoleInterface, loginManager);
            updateUserViewsTable();
            setCurrentTextConsoleColor(ConsoleColor.GREEN);
            println("User has been created!");
            waitForKeyPress();
        }
    }
}
