package com.group6.hms.app.screens.admin.importer;

import com.group6.hms.app.auth.User;
import com.group6.hms.app.roles.*;
import com.group6.hms.framework.screens.NumberUtils;

import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

/**
 * The {@code StaffsCSVReader} class is responsible for reading staff
 * data from a CSV file and converting it into a list of {@link Staff}
 * objects.
 */
public class StaffsCSVReader extends CSVReader {

    private static final char[] defaultPassword = "password".toCharArray();

    /**
     * Constructs a {@code StaffsCSVReader} with the specified input
     * reader and buffer size.
     *
     * @param in the reader from which to read the CSV data
     * @param sz the buffer size for the reader
     */
    public StaffsCSVReader(Reader in, int sz) {
        super(in, sz);
    }

    /**
     * Constructs a {@code StaffsCSVReader} with the specified input
     * reader using the default buffer size.
     *
     * @param in the reader from which to read the CSV data
     */
    public StaffsCSVReader(Reader in) {
        super(in);
    }

    /**
     * Reads all staff entries from the CSV file and returns them as a
     * list of {@link Staff} objects. The staff roles are determined
     * from the CSV data, and appropriate staff objects are created
     * based on their roles.
     *
     * @return a list of {@code Staff} objects representing the
     *         staff members read from the CSV file
     * @throws InvalidStaffRoleException if an invalid staff role is
     *         encountered in the CSV data
     */
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
                case "doctor" -> new Doctor(staffId, defaultPassword, staffName, staffGender, staffAge);
                case "pharmacist" -> new Pharmacist(staffId, defaultPassword, staffName, staffGender, staffAge);
                case "administrator" -> new Administrator(staffId, defaultPassword, staffName, staffGender, staffAge);
                default -> throw new InvalidStaffRoleException("Invalid staff role, " + staffRole);
            };
            staffs.add(staff);
        }
        return staffs;
    }

}
