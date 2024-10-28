package com.group6.hms.app.screens.doctor;

import com.group6.hms.app.auth.LoginManager;
import com.group6.hms.app.auth.LoginManagerHolder;
import com.group6.hms.app.models.Appointment;
import com.group6.hms.app.roles.Doctor;
import com.group6.hms.framework.screens.pagination.HeaderField;
import com.group6.hms.framework.screens.pagination.PaginationTableScreen;

import java.util.List;

public class AcceptOrDeclineScreen extends PaginationTableScreen<Appointment> {
    @HeaderField(width=1000)

    LoginManager loginManager = LoginManagerHolder.getLoginManager();
    Doctor doc = (Doctor) loginManager.getCurrentlyLoggedInUser();

    public AcceptOrDeclineScreen(List<Appointment> items) {
        super("Accept Pending Appointments", items);

    }
}
