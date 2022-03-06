package com.C195.Models;

/**
 * Customer dataclass which is responsible for holding all customer information.
 * @author Brady Bassett
 */
public class Customer {
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private Division division;

    /**
     * Customer constructor.
     * @param customerId int - The customer ID.
     * @param customerName String - The customer name.
     * @param address String - The customer address.
     * @param postalCode String - The customer postal code.
     * @param phone String - The customer phone number.
     * @param division Division - The customers first level division.
     */
    public Customer(int customerId, String customerName, String address, String postalCode, String phone,
                    Division division) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.division = division;
    }

    /**
     * A getter function to return the customer ID.
     * @return Returns the customer ID.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * A getter function to return the customer name.
     * @return Returns the customer name.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * A getter function to return the customer address.
     * @return Returns the customer address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * A getter function to return the customer postal code.
     * @return Returns the customer postal code.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * A getter function to return the customer phone number.
     * @return Returns the customer phone number.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * A getter function to return the customer division.
     * @return Returns the customer division.
     */
    public Division getDivision() {
        return division;
    }

    /**
     * A setter function to set the customer ID.
     * @param customerId The new customer ID.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * A setter function to set the customer name.
     * @param customerName The new customer name.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * A setter function to set the customer address.
     * @param address The new customer address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * A setter function to set the customer postal code.
     * @param postalCode The new customer postal code.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * A setter function to set the customer phone number.
     * @param phone The new customer phone number.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * A setter function to set the customer division.
     * @param division The new customer division.
     */
    public void setDivision(Division division) {
        this.division = division;
    }
}
