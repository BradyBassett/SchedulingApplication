package com.C195.Controllers;

import com.C195.Database.QueryCustomers;
import com.C195.Models.Country;
import com.C195.Models.Customer;
import com.C195.Models.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.C195.Database.JDBC.closeConnection;
import static com.C195.Database.JDBC.openConnection;
import static com.C195.Database.QueryCountries.queryCountries;
import static com.C195.Database.QueryCustomers.*;
import static com.C195.Database.QueryDivisions.queryDivisions;

public class CustomerViewController extends FormController implements Initializable {
    public boolean newCustomer = true;
    @FXML private TextField customerIdField;
    @FXML private TextField customerNameField;
    @FXML private TextField customerAddressField;
    @FXML private TextField customerPostalCodeField;
    @FXML private TextField customerPhoneField;
    @FXML private ComboBox<Country> customerCountryBox;
    @FXML private ComboBox<Division> customerDivisionBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initButtons();
        try {
            openConnection();
            customerIdField.setText(String.valueOf(QueryCustomers.queryMaxId() + 1));
        } catch (SQLException e) {
            showAlert(e);
        } finally {
            closeConnection();
        }
        customerNameField.setPromptText(bundle.getString("field.name"));
        customerAddressField.setPromptText(bundle.getString("field.address"));
        customerPostalCodeField.setPromptText(bundle.getString("field.postalCode"));
        customerPhoneField.setPromptText(bundle.getString("field.phone"));
        customerCountryBox.setPromptText(bundle.getString("field.country"));
        customerDivisionBox.setPromptText(bundle.getString("field.division"));
        initCountries();
    }

    private void initCountries() {
        ObservableList<Country> countries;
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
        ObservableList<Division> divisions;
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
        customerIdField.setText(String.valueOf(customer.getCustomerId()));
        customerNameField.setText(customer.getCustomerName());
        customerAddressField.setText(customer.getAddress());
        customerPostalCodeField.setText(customer.getPostalCode());
        customerPhoneField.setText(customer.getPhone());
        customerCountryBox.setValue(customer.getDivision().getCountry());
        customerDivisionBox.setValue(customer.getDivision());
        customerDivisionBox.setDisable(false);
    }

    @FXML private void handleCountrySelected() {
        initDivisions(customerCountryBox.getValue().getCountryId());
        customerDivisionBox.setDisable(false);
    }

    @FXML private void handleSave(ActionEvent event) {
        boolean success = false;
        try {
            validateItems();
            openConnection();
            Customer customer = new Customer(Integer.parseInt(customerIdField.getText()), customerNameField.getText(),
                    customerAddressField.getText(), customerPostalCodeField.getText(), customerPhoneField.getText(),
                    customerDivisionBox.getValue());
            if (newCustomer){
                createNewCustomer(customer);
            } else {
                modifyCustomer(customer);
            }
            success = true;
        } catch (NullPointerException | IllegalArgumentException | SQLException exception) {
            showAlert(exception);
        } finally {
            closeConnection();
        }
        if (success)
            showView(event, "../Views/mainView.fxml");
    }

    private void validateItems() {
        validateFieldNotEmpty(customerNameField.getText(), bundle.getString("error.nullNameField"));
        validateFieldNotEmpty(customerAddressField.getText(), bundle.getString("error.nullAddressField"));
        validateField("\\d+\\s[\\w\\s]+", customerAddressField.getText(), bundle.getString("error.invalidAddress"));
        validateFieldNotEmpty(customerPostalCodeField.getText(), bundle.getString("error.nullPostalCodeField"));
        validateField("[a-zA-Z\\d]{5}", customerPostalCodeField.getText(), bundle.getString("error.invalidPostal"));
        validateFieldNotEmpty(customerPhoneField.getText(), bundle.getString("error.nullPhoneField"));
        validateField("^(\\+?\\d{1,3}-?( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$",
                customerPhoneField.getText(), bundle.getString("error.invalidPhone"));

        if (customerCountryBox.getValue() == null)
            throw new NullPointerException(bundle.getString("error.nullCountryBox"));
        if (customerDivisionBox.getValue() == null)
            throw new NullPointerException(bundle.getString("error.nullDivisionBox"));
    }
}
