package com.C195.Models;

import java.sql.Timestamp;

/**
 * Appointment dataclass which is responsible for holding all appointment information.
 * @author Brady Bassett
 */
public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private Customer customer;
    private User user;
    private Contact contact;

    /**
     * Appointment constructor.
     * @param appointmentId int - The appointment ID.
     * @param title String - The appointment title.
     * @param description String - The appointment description.
     * @param location String - The appointment's location.
     * @param type String - What type of appointment the appointment is.
     * @param start Timestamp - When the appointment begins (yyyy-MM-dd hh:mm:ss).
     * @param end Timestamp - When the appointment ends (yyyy-MM-dd hh:mm:ss).
     * @param customer Customer - A reference to the customer that is associated with the appointment.
     * @param user User - A reference to the user that is associated with the appointment.
     * @param contact Contact - A reference to the contact that is associated with the appointment.
     */
    public Appointment(int appointmentId, String title, String description, String location, String type,
                       Timestamp start, Timestamp end, Customer customer, User user, Contact contact) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customer = customer;
        this.user = user;
        this.contact = contact;
    }

    /**
     * A getter function to return the appointment ID.
     * @return Returns the appointment ID.
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * A getter function to return the appointment title.
     * @return Returns the appointment title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * A getter function to return the appointment description.
     * @return Returns the appointment description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * A getter function to return the appointment's location.
     * @return Returns the appointment's location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * A getter function to return the appointment type.
     * @return Returns the appointment type.
     */
    public String getType() {
        return type;
    }

    /**
     * A getter function to return the appointment start time.
     * @return Returns the appointment start time.
     */
    public Timestamp getStart() {
        return start;
    }

    /**
     * A getter function to return the appointment end time.
     * @return Returns the appointment end time.
     */
    public Timestamp getEnd() {
        return end;
    }

    /**
     * A getter function to return the appointments associated customer.
     * @return Returns the appointments associated customer.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * A getter function to return the appointments associated user.
     * @return Returns the appointments associated user.
     */
    public User getUser() {
        return user;
    }

    /**
     * A getter function to return the appointments associated contact.
     * @return Returns the appointments associated contact.
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * A setter function to set the appointment's ID.
     * @param appointmentId The new appointment ID to be set.
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * A setter function to set the appointment's title.
     * @param title The new appointment title to be set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * A setter function to set the appointment's description.
     * @param description The new appointment description to be set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * A setter function to set the appointment's location.
     * @param location The new appointment location to be set.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * A setter function to set the appointment's type.
     * @param type The new appointment type to be set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * A setter function to set the appointment's start time.
     * @param start The new appointment start time to be set.
     */
    public void setStart(Timestamp start) {
        this.start = start;
    }

    /**
     * A setter function to set the appointment's end time.
     * @param end The new appointment end time to be set.
     */
    public void setEnd(Timestamp end) {
        this.end = end;
    }

    /**
     * A setter function to set a new associated customer for the appointment.
     * @param customer The new customer reference to be set.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * A setter function to set a new associated user for the appointment.
     * @param user The new user reference to be set.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * A setter function to set a new associated contact for the appointment.
     * @param contact The new contact reference to be set.
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
