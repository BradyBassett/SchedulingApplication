package com.C195.Models;

/**
 * First level division dataclass which is responsible for holding all division information.
 * @author Brady Bassett
 */
public class Division {
    private int divisionID;
    private String division;
    private Country country;

    /**
     * Division constructor
     * @param divisionID int - The division ID.
     * @param division String - The division name.
     * @param country Country - The country the division is a part of.
     */
    public Division(int divisionID, String division, Country country) {
        this.divisionID = divisionID;
        this.division = division;
        this.country = country;
    }

    /**
     * A getter function to return the division ID.
     * @return Returns the division ID.
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * A getter function to return the division name.
     * @return Returns the division name.
     */
    public String getDivision() {
        return division;
    }

    /**
     * A getter function to return the country the division is a part of.
     * @return Returns the country the division is a part of.
     */
    public Country getCountry() {
        return country;
    }

    /**
     * A setter function to set the division ID.
     * @param divisionID The new division ID.
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * A setter function to set the division name.
     * @param division The new division name.
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * A setter function to set the country the division is a part of.
     * @param country The new country the division is a part of.
     */
    public void setCountry(Country country) {
        this.country = country;
    }
}
