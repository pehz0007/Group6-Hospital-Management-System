package com.group6.hms.app.screens.admin.importer;

import com.group6.hms.app.auth.User;
import com.group6.hms.app.roles.*;
import com.group6.hms.framework.screens.NumberUtils;

import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

public class StaffsCSVReader extends CSVReader {

    private static final char[] defaultPassword = "password".toCharArray();

    public StaffsCSVReader(Reader in, int sz) {
        super(in, sz);
    }

    public StaffsCSVReader(Reader in) {
        super(in);
    }

    public List<Staff> readAllStaffs() {
        String[] headers = this.readCSVLine();

        String[] values;
        List<Staff> staffs = new LinkedList<>();
        while (this.hasNext() && (values = this.readCSVLine()).length != 0) {
            String staffId = values[0];
            String staffName = values[1];
            String staffRole = values[2].toLowerCase();
            Gender staffGender = Gender.fromString(values[3]);
            int staffAge = NumberUtils.tryParseInt(values[4]);

            Staff staff = switch (staffRole){
                case "doctor" -> new Doctor(staffId, defaultPassword, staffName, staffGender, staffId, staffAge);
                case "pharmacist" -> new Pharmacist(staffId, defaultPassword, staffName, staffGender, staffId, staffAge);
                case "administrator" -> new Administrator(staffId, defaultPassword, staffName, staffGender, staffId, staffAge);
                default -> throw new InvalidStaffRoleException("Invalid staff role, " + staffRole);
            };
            staffs.add(staff);
        }
        return staffs;
    }

}
