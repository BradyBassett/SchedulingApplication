package com.C195.Models;

/**
 * User dataclass which is responsible for holding all user information excluding the password, which for security
 * reasons is not stored.
 * @author Brady Bassett
 */
public class User {
    private int userId;
    private String userName;

    /**
     * User constructor.
     * @param userId int - The user ID.
     * @param userName String - The username.
     */
    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    /**
     * A getter function to return the user ID.
     * @return Returns the user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * A getter function to return the username.
     * @return Returns the username.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * A setter function to set the user ID.
     * @param userId The new user ID.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * A setter function to set the username.
     * @param userName The new username.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
