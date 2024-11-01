package com.group6.hms.app.screens.admin;

import com.group6.hms.app.roles.Staff;

public class StaffDataView {
    private String staffId;
    private String staffName;
    private String gender;
    public StaffDataView(Staff staff) {
        this.staffId = staff.getUserId();
        this.staffName = staff.getName();
        this.gender = staff.getGender().toString();
    }
}
