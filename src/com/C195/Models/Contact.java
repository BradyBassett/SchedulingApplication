package com.C195.Models;

/**
 * Contact dataclass which is responsible for holding all contact information.
 * @author Brady Bassett
 */
public class Contact {
    private int contactId;
    private String contactName;
    private String email;

    /**
     * Contact constructor.
     * @param contactId int - The contact ID.
     * @param contactName String - The contact name.
     * @param email String - The contact email address.
     */
    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * A getter function to return the contact ID.
     * @return Returns the contact ID.
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * A getter function to return the contact name.
     * @return Returns the contact name.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * A getter function to return the contact email.
     * @return Returns the contact email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * A setter function to set the contact's ID.
     * @param contactId The new contact ID to be set.
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * A setter function to set the contact's name.
     * @param contactName The new contact name to be set.
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * A setter function to set the contact's email.
     * @param email The new contact email to be set.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
