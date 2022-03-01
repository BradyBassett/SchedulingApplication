package com.C195.Controllers;

import com.C195.Models.User;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public abstract class BaseController {
    protected final static Locale locale = Locale.getDefault();
    protected final static ResourceBundle bundle = ResourceBundle.getBundle("com.C195.Resources.language", locale);
    protected static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    protected void showAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, e.toString());
        alert.showAndWait();
    }

    protected void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
    }

    protected boolean confirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message);
        Optional<ButtonType> confirmation = alert.showAndWait();
        return confirmation.isPresent() && confirmation.get() == ButtonType.OK;
    }

    protected Timestamp convertLocalToUTC(Timestamp timestamp) {
        ZoneId localZone = TimeZone.getDefault().toZoneId();
        ZoneId utcZone = ZoneId.of("UTC");
        LocalDateTime ldt = timestamp.toLocalDateTime();
        ZonedDateTime localTime = ZonedDateTime.of(ldt, localZone);
        ZonedDateTime utc = localTime.withZoneSameInstant(utcZone);

        return Timestamp.valueOf(utc.toLocalDateTime());
    }

    protected Timestamp convertLocalToEST(Timestamp timestamp) {
        ZoneId localZone = TimeZone.getDefault().toZoneId();
        ZoneId estZone = ZoneId.of("America/New_York");
        LocalDateTime ldt = timestamp.toLocalDateTime();
        ZonedDateTime local = ZonedDateTime.of(ldt, localZone);
        ZonedDateTime est = local.withZoneSameInstant(estZone);

        return Timestamp.valueOf(est.toLocalDateTime());
    }

    protected Timestamp convertUTCToLocal(Timestamp timestamp) {
        ZoneId utcZone = ZoneId.of("UTC");
        ZoneId localZone = TimeZone.getDefault().toZoneId();
        LocalDateTime ldt = timestamp.toLocalDateTime();
        ZonedDateTime utc = ZonedDateTime.of(ldt, utcZone);
        ZonedDateTime local = utc.withZoneSameInstant(localZone);

        return Timestamp.valueOf(local.toLocalDateTime());
    }
}
