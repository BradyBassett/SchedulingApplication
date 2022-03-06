package com.C195.Models;

/**
 * Country dataclass which is responsible for holding all country information.
 * @author Brady Bassett
 */
public class Country {
    private int countryId;
    private String country;

    /**
     * Country constructor.
     * @param countryId int - The country ID.
     * @param country String - The country name.
     */
    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    /**
     * A getter function to return the country ID.
     * @return Returns the country ID.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * A getter function to return the country name.
     * @return Returns the country name.
     */
    public String getCountry() {
        return country;
    }

    /**
     * A setter function to set the country ID.
     * @param countryId The new country ID.
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * A setter function to set the country name.
     * @param country The new country name.
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
