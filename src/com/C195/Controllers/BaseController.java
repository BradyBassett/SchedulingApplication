package com.C195.Controllers;

import com.C195.Models.User;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Abstract class that represents every controller. It stores the current user, shows alerts and converts timezones.
 * @author Brady Bassett
 */
public abstract class BaseController {
    /**
     * The language bundle representing both the EN and FR .properties files.
     */
    protected final static ResourceBundle bundle = ResourceBundle.getBundle("com.C195.Resources.language");
    /**
     * The current authenticated user
     */
    protected static User currentUser;

    /**
     * A getter function to return the current user.
     * @return Returns the current user.
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * A setter function to set the current user.
     * @param user The new current user to be set.
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * Shows an error alert that displays a given exception message.
     * @param e The exception provided.
     */
    protected void showAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, e.toString());
        alert.showAndWait();
    }

    /**
     * Shows an error alert that displays a given message.
     * @param message The message provided.
     */
    protected void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
    }

    /**
     * Shows a confirmation alert that displays a given message.
     * @param message The message to be displayed with the alert.
     * @return Returns the users decision in response to the confirmation alert.
     */
    protected boolean confirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message);
        Optional<ButtonType> confirmation = alert.showAndWait();
        return confirmation.isPresent() && confirmation.get() == ButtonType.OK;
    }

    /**
     * A function which converts a given timestamp from the users local timezone to UTC.
     * @param timestamp A timestamp in the users local timezone.
     * @return Returns the timestamp after being converted to UTC.
     */
    protected Timestamp convertLocalToUTC(Timestamp timestamp) {
        ZoneId localZone = TimeZone.getDefault().toZoneId();
        ZoneId utcZone = ZoneId.of("UTC");
        LocalDateTime ldt = timestamp.toLocalDateTime();
        ZonedDateTime localTime = ZonedDateTime.of(ldt, localZone);
        ZonedDateTime utc = localTime.withZoneSameInstant(utcZone);

        return Timestamp.valueOf(utc.toLocalDateTime());
    }

    /**
     * A function which converts a given timestamp from the users local timezone to EST.
     * @param timestamp A timestamp in the users local timezone.
     * @return Returns the timestamp after being converted to EST.
     */
    protected Timestamp convertLocalToEST(Timestamp timestamp) {
        ZoneId localZone = TimeZone.getDefault().toZoneId();
        ZoneId estZone = ZoneId.of("America/New_York");
        LocalDateTime ldt = timestamp.toLocalDateTime();
        ZonedDateTime local = ZonedDateTime.of(ldt, localZone);
        ZonedDateTime est = local.withZoneSameInstant(estZone);

        return Timestamp.valueOf(est.toLocalDateTime());
    }

    /**
     * A function which converts a given timestamp from UTC to the users local timezone.
     * If your database server converts all timestamps from your local time to utc then remove this function
     * @param timestamp A timestamp in UTC.
     * @return Returns the timestamp after being converted to the users local timezone.
     */
    protected Timestamp convertUTCToLocal(Timestamp timestamp) {
        ZoneId utcZone = ZoneId.of("UTC");
        ZoneId localZone = TimeZone.getDefault().toZoneId();
        LocalDateTime ldt = timestamp.toLocalDateTime();
        ZonedDateTime utc = ZonedDateTime.of(ldt, utcZone);
        ZonedDateTime local = utc.withZoneSameInstant(localZone);

        return Timestamp.valueOf(local.toLocalDateTime());
    }
}
