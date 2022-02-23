package com.C195.Controllers;

import com.C195.Models.Country;
import com.C195.Models.Customer;
import com.C195.Models.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.C195.Database.JDBC.closeConnection;
import static com.C195.Database.JDBC.openConnection;
import static com.C195.Database.QueryCountries.queryCountries;
import static com.C195.Database.QueryCustomers.queryMaxId;
import static com.C195.Database.QueryDivisions.queryDivisions;

public class CustomerViewController extends ViewController implements Initializable {
    private Customer activeCustomer;
    ObservableList<Country> countries = FXCollections.observableArrayList();
    ObservableList<Division> divisions = FXCollections.observableArrayList();
    @FXML private Button cancelButton;
    @FXML private Button saveButton;
    @FXML private TextField customerIdField;
    @FXML private TextField customerNameField;
    @FXML private TextField customerAddressField;
    @FXML private TextField customerPostalCodeField;
    @FXML private TextField customerPhoneField;
    @FXML private ComboBox<Country> customerCountryBox;
    @FXML private ComboBox<Division> customerDivisionBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancelButton.setText(bundle.getString("button.cancel"));
        saveButton.setText(bundle.getString("button.save"));
        customerIdField.setPromptText(bundle.getString("field.id"));
        customerNameField.setPromptText(bundle.getString("field.name"));
        try {
            openConnection();
            customerIdField.setText(String.valueOf(queryMaxId() + 1));
        } catch (SQLException e) {
            showAlert(e);
        } finally {
            closeConnection();
        }
        customerAddressField.setPromptText(bundle.getString("field.address"));
        customerPostalCodeField.setPromptText(bundle.getString("field.postalCode"));
        customerPhoneField.setPromptText(bundle.getString("field.phone"));
        customerCountryBox.setPromptText(bundle.getString("field.country"));
        customerDivisionBox.setPromptText(bundle.getString("field.division"));
        initCountries();
    }

    private void initCountries() {
        try {
            openConnection();
            countries = FXCollections.observableArrayList(queryCountries());
        } catch (SQLException e) {
            showAlert(e);
            return;
        } finally {
            closeConnection();
        }

        customerCountryBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Country country) {
                return country.getCountry();
            }

            @Override
            public Country fromString(String s) {
                return null;
            }
        });

        customerCountryBox.setItems(countries);
    }

    private void initDivisions(int countryId) {
        try {
            openConnection();
            divisions = FXCollections.observableArrayList(queryDivisions(countryId));
        } catch (SQLException e) {
            showAlert(e);
            return;
        } finally {
            closeConnection();
        }

        customerDivisionBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Division division) {
                return division.getDivision();
            }

            @Override
            public Division fromString(String s) {
                return null;
            }
        });

        customerDivisionBox.setItems(divisions);
    }

    public void initCustomerData(Customer customer) {
        activeCustomer = customer;
        customerIdField.setText(String.valueOf(activeCustomer.getCustomerId()));
        customerNameField.setText(activeCustomer.getCustomerName());
        customerAddressField.setText(activeCustomer.getAddress());
        customerPostalCodeField.setText(activeCustomer.getPostalCode());
        customerPhoneField.setText(activeCustomer.getPhone());
        customerCountryBox.setValue(activeCustomer.getDivision().getCountry());
        customerDivisionBox.setValue(activeCustomer.getDivision());
        customerDivisionBox.setDisable(false);
    }

    @FXML private void handleCountrySelected() {
        initDivisions(customerCountryBox.getValue().getCountryId());
        customerDivisionBox.setDisable(false);
    }

    @FXML private void handleCancel(ActionEvent e) {
        showView(e, "../Views/mainView.fxml");
    }

    @FXML private void handleSave(ActionEvent e) {
        showView(e, "../Views/mainView.fxml");
    }
}
