package com.C195.Models;

import java.time.Month;

public class CustomerAppointment {
    private int customerAppointments;
    private String type;
    private Month month;

    public CustomerAppointment(int customerAppointments, String type, Month month) {
        this.customerAppointments = customerAppointments;
        this.type = type;
        this.month = month;
    }

    public int getCustomerAppointments() {
        return customerAppointments;
    }

    public void setCustomerAppointments(int customerAppointments) {
        this.customerAppointments = customerAppointments;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }
}

