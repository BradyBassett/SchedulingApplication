package com.C195.Models;

public class Division {
    private int divisionID;
    private String division;
    private Country country;

    public Division(int divisionID, String division, Country country) {
        this.divisionID = divisionID;
        this.division = division;
        this.country = country;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public String getDivision() {
        return division;
    }

    public Country getCountry() {
        return country;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
