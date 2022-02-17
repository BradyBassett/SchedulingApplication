package com.C195.Models;

import java.sql.Timestamp;

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

    public Appointment(int appointmentId, String title, String description, String location, String type, Timestamp start, Timestamp end, Customer customer, User user, Contact contact) {
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

    public int getAppointmentId() {
        return appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public Timestamp getStart() {
        return start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public Customer getCustomer() {
        return customer;
    }

    public User getUser() {
        return user;
    }

    public Contact getContact() {
        return contact;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
