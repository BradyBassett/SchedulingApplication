package com.C195.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

public class ContactsScheduleReportViewController extends ReportController {
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLabel.setText(bundle.getString("label.contactsScheduleReport"));
        closeButton.setText(bundle.getString("button.close"));
    }
}