package com.group6.hms.app.screens.admin;

import com.group6.hms.app.roles.Staff;

/**
 * The {@code StaffDataView} class is a representation of staff data
 * for display purposes within the administration interface.
 * It encapsulates the staff's basic information including ID,
 * name, and gender.
 */
public class StaffDataView {

    private String staffId;
    private String staffName;
    private String gender;

    /**
     * Constructs a {@code StaffDataView} instance from the specified
     * {@code Staff} object. This constructor initializes the
     * staff ID, name, and gender based on the provided staff object.
     *
     * @param staff the {@code Staff} object containing the staff's information
     */
    public StaffDataView(Staff staff) {
        this.staffId = staff.getUserId();
        this.staffName = staff.getName();
        this.gender = staff.getGender().toString();
    }

}
